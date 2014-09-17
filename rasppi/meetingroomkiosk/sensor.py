# coding=utf-8
import logging
# see http://stackoverflow.com/questions/2234982/why-both-import-logging-and-import-logging-config-are-needed
import logging.config
import sys
import time

from meetingroomkiosk.constants import Constants
from meetingroomkiosk.heartbeat import Heartbeat
from meetingroomkiosk.heartbeat_mock import HeartbeatMock
from meetingroomkiosk.registration_service import RegistrationService
from meetingroomkiosk.registration_service_mock import RegistrationServiceMock
from meetingroomkiosk.sensor_service import SensorService
from meetingroomkiosk.sensor_service_mock import SensorServiceMock


__author__ = 'ali ok'

MOCK_DATA = True
MOCK_HARDWARE = True

# have rolling log files
# see http://victorlin.me/posts/2012/08/26/good-logging-practice-in-python
logging.config.dictConfig({
    'version': 1,
    'disable_existing_loggers': False,  # this fixes the problem

    'formatters': {
        'standard': {
            'format': '%(asctime)s [%(levelname)s] %(name)s: %(message)s'
        },
    },
    'handlers': {
        'console': {
            'level': 'DEBUG',
            'class': 'logging.StreamHandler',
            'formatter': "standard",
        },
        'file': {
            'level': 'INFO',
            'class': 'logging.handlers.RotatingFileHandler',
            'formatter': "standard",
            'filename': "sensor.log",
            'maxBytes': 10485760,  # 10 MB
            'backupCount': 100,  # last 100 files
            'encoding': "utf8"
        },
    },
    'loggers': {
        '': {
            'handlers': ['console', 'file'],
            'level': 'INFO',
            'propagate': True
        }
    }
})

log = logging.getLogger(__name__)


class Sensor:
    """
    Defines sensor logic.
    It reads the distance of object continuously and calls services on the server on different events to notify server.
    Events are :
    * OBJECT_WITHIN_THRESHOLD1 : Notify server about an object which just showed up in the not-very-close distance.
    * OBJECT_WITHIN_THRESHOLD2 : Notify server about an object which just showed up in the close distance.

    It is smart enough to not to send same event over and over again. Also, if there is a very close event for a very
    long time, it doesn't broadcast the event to the server.
    """

    def __init__(self):
        if MOCK_HARDWARE:
            from meetingroomkiosk.hardware_mock import HardwareMock

            self._hardware = HardwareMock()
        else:
            from meetingroomkiosk.hardware import Hardware

            self._hardware = Hardware()

        if MOCK_DATA:
            self._sensor_service = SensorServiceMock()
            self._registration_service = RegistrationServiceMock()
            self._heartbeat = HeartbeatMock()
        else:
            self._sensor_service = SensorService()
            self._registration_service = RegistrationService()
            self._heartbeat = Heartbeat()

    @staticmethod
    def _current_time_in_millis():
        # see http://stackoverflow.com/questions/5998245/get-current-time-in-milliseconds-in-python
        # about getting the current time in millis
        return int(round(time.time() * 1000))

    def _start(self):
        log.info("Starting program...")

        # first of all, check all the required settings
        # noinspection PyBroadException
        try:
            self._sensor_info = self._registration_service.get_sensor_info_sync()
        except:
            log.exception("Unable to register! Exiting program")
            return

        if not self._sensor_info:
            log.critical("Unable to get sensor info. Exiting program!")

        # give the token to services
        self._sensor_service.set_token(self._sensor_info.token)
        self._registration_service.set_token(self._sensor_info.token)
        self._heartbeat.set_token(self._sensor_info.token)

        # since server settings are all good, send a heartbeat about starting the sensor program
        self._heartbeat.sendSync(Constants.HEARTBEAT_STARTING)

        # initialize hardware.
        # noinspection PyBroadException
        try:
            log.info("Initializing hardware GPIO...")
            self._hardware.initialize_gpio()
        except:
            # do not care about clean up, since hardware does it itself
            log.exception("Unable to initialize hardware GPIO. Exiting program!")
            # send heartbeat die with message
            self._heartbeat.sendSync(Constants.HEARTBEAT_DIE, "Unable to initialize GPIO.", sys.exc_info())
            return

        # send the sync heartbeat afterwards to not to mix GPIO initialization exceptions with heartbeat exceptions
        self._heartbeat.sendSync(Constants.HEARTBEAT_GPIO_INITIALIZED)

        # start heartbeat here
        self._heartbeat.start_heartbeat_thread()

        previous_broadcasted_event_type = None
        previous_broadcasted_event_time = 0

        # start measuring
        while True:
            event_type = None
            try:
                distance = self._hardware.measure()
            except:
                # do not care about clean up, since hardware itself does it
                # update heartbeat status so that server knows there is something wrong with the measurement
                self._heartbeat.sendSync(Constants.HEARTBEAT_DIE, "Error during measure.", sys.exc_info())
                # since long time if that is the case
                log.exception("Error during measurement!")
                # re-raise and eventually exit the program
                raise

            if distance < self._sensor_info.threshold1:
                if distance >= self._sensor_info.threshold2:
                    # we have event type 1
                    event_type = Constants.EVENT_TYPE_OBJECT_WITHIN_THRESHOLD1
                    log.info("Object found between threshold1 and threshold2 : {} cm".format(distance))
                else:
                    # we might have event type 2. but need to check if object is too close
                    if distance <= Constants.TOO_CLOSE_DISTANCE_THRESHOLD:
                        # ignore
                        log.info("Object is too close : {}".format(distance))
                    else:
                        # we have event type 2
                        log.info("Object is withing threshold2 and it is not too close : {} cm".format(distance))
                        event_type = Constants.EVENT_TYPE_OBJECT_WITHIN_THRESHOLD2
            else:
                # ignore the object since it is too far away
                log.info("Object is too far away : {} cm".format(distance))
                pass

            # do not broadcast the event every time!
            # broadcast the event if it is new.
            # new means:
            # last broadcasted event is from a different type
            # OR
            # it has been N seconds since last broadcasted event
            send_broadcast = False

            if not event_type:
                send_broadcast = False
            else:
                if previous_broadcasted_event_type != event_type:
                    send_broadcast = True
                else:
                    elapsed_time_since_last_broadcast = Sensor._current_time_in_millis() - previous_broadcasted_event_time
                    if elapsed_time_since_last_broadcast > self._sensor_info.seconds_to_ignore_same_events * 1000:
                        send_broadcast = True
                    else:
                        log.info(
                            "Not broadcasting the event since same type is broadcasted very recently : " + event_type)

            # noinspection PyBroadException
            try:
                if send_broadcast:
                    log.info("Gonna broadcast event : " + event_type)
                    self._sensor_service.broadcastEvent(event_type)
            except:
                log.exception("Error broadcasting event : " + event_type)
                # do nothing. continue with the next measurement

            # sleep some time before measuring again
            if send_broadcast:
                previous_broadcasted_event_type = event_type
                previous_broadcasted_event_time = Sensor._current_time_in_millis()
                # if we do broadcast, then sleep less since some time will be gone during the REST call
                time.sleep(Constants.SLEEP_TIME_BEFORE_NEXT_MEASUREMENT_AFTER_BROADCAST)
            else:
                # sleep more
                time.sleep(Constants.SLEEP_TIME_BEFORE_NEXT_MEASUREMENT_NO_BROADCAST)

    def _on_exit(self):
        # try to clean up anyway. it is safe to do that over and over again
        self._hardware.clean_up()
        # stop heartbeat thread so that we don't send heartbeats anymore
        self._heartbeat.stop_heartbeat_thread()
        # send heartbeat die
        self._heartbeat.sendSync(Constants.HEARTBEAT_DIE, "Exiting program")

    def start_program(self):
        # noinspection PyBroadException
        try:
            self._start()
        except:
            # catch all unhandled exceptions
            # that means, program wanted to terminate
            log.exception("Program didn't handle the exception. Probably it wanted termination.")
            self._on_exit()


def main():
    sensor = Sensor()
    sensor.start_program()


if __name__ == "__main__":
    main()