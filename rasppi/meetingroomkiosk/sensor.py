# coding=utf-8
import logging
# see http://stackoverflow.com/questions/2234982/why-both-import-logging-and-import-logging-config-are-needed
import logging.config
import sys
import time

from meetingroomkiosk.constants import Constants
from meetingroomkiosk.heartbeat import Heartbeat
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
        else:
            self._sensor_service = SensorService()

        if MOCK_DATA:
            self._registration_service = RegistrationServiceMock()
        else:
            self._registration_service = RegistrationService()

        # TODO if MOCK_DATA
        self._heartbeat = Heartbeat()

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
                    log.info("Object found between threshold1 and threshold2 : {}".format(distance))
                else:
                    # we might have event type 2. but need to check if object is too close
                    if distance <= Constants.TOO_CLOSE_DISTANCE_THRESHOLD:
                        # ignore
                        log.info("Object is too close : {}".format(distance))
                    else:
                        # we have event type 2
                        log.info("Object is withing threshold2 and it is not too close : {}".format(distance))
                        event_type = Constants.EVENT_TYPE_OBJECT_WITHIN_THRESHOLD2
            else:
                # ignore the object since it is too far away
                log.info("Object is too far away : {}".format(distance))
                pass

            # noinspection PyBroadException
            try:
                # TODO: do not send it every time!
                # TODO: do it in background
                if event_type:
                    log.info("Gonna broadcast event : " + event_type)
                    self._sensor_service.broadcastEvent(event_type)
            except:
                log.exception("Error broadcasting event : " + event_type)
                # do nothing. continue with the next measurement

            # sleep some time before measuring again
            if event_type:
                # if we do broadcast, then sleep less since some time will be gone during the REST call
                time.sleep(0.2)
            else:
                # sleep more
                time.sleep(0.75)

    def _on_exit(self):
        # try to clean up anyway. it is safe to do that over and over again
        self._hardware.clean_up()
        # stop heartbeat thread so that we don't send heartbeats anymore
        self._heartbeat.stop_heartbeat_thread()
        # send heartbeat die
        self._heartbeat.sendSync(Constants.HEARTBEAT_DIE, "Exiting program")

    def start_program(self):
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