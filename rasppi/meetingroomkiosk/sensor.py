# coding=utf-8
import logging

from meetingroomkiosk.constants import Constants
from meetingroomkiosk.hardware import Hardware
from meetingroomkiosk.hardware_mock import HardwareMock
from meetingroomkiosk.heartbeat import Heartbeat
from meetingroomkiosk.sensor_service import SensorService

__author__ = 'ali ok'

MOCK_DATA = True
MOCK_HARDWARE = True

logging.basicConfig(level=logging.INFO)
log = logging.getLogger(__name__)  # TODO try to have a rolling log so that the log size doesn't exceed some days


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
            self._hardware = HardwareMock()
        else:
            self._hardware = Hardware()

        # TODO if MOCK_DATA
        self._sensor_service = SensorService()

        # TODO if MOCK_DATA
        self._heartbeat = Heartbeat()

        self._threshold1 = None  # TODO
        self._threshold2 = None  # TODO

    def _start(self):
        log.log("Starting program...")
        # TODO
        # first of all, check all the required settings and then start measuring
        # during that, if server settings are all good, send a heartbeat about starting the sensor program

        self._heartbeat.sendSync(Constants.HEARTBEAT_STARTING)

        # initalize hardware.
        try:
            log.info("Initializing hardware GPIO...")
            self._hardware.initialize_gpio()
            self._heartbeat.sendSync(Constants.HEARTBEAT_GPIO_INITIALIZED)
        except:
            # do not care about clean up, since hardware does it itself
            log.critical("Unable to initialize hardware GPIO. Exiting program!")  # TODO: log exception as well
            # send heartbeat die with message
            self._heartbeat.sendSync(Constants.HEARTBEAT_DIE, "Unable to initialize GPIO.")  # TODO: with exception
            return

        # start heartbeat here
        self._heartbeat.start_heartbeat_thread()

        # start measuring
        while True:
            event_type = None
            try:
                distance = self._hardware.measure()
            except:
                # TODO: measure method cleans up if there is an error
                # do not care about clean up, since hardware itself does it
                # TODO: update heartbeat status so that server knows there is something wrong with the measurement
                # since long time if that is the case
                log.error("Error during measurement!")

            if distance < self._threshold1:
                if distance >= self._threshold2:
                    # we have event type 1
                    event_type = Constants.EVENT_TYPE_OBJECT_WITHIN_THRESHOLD1
                    log.info("Object found between threshold1 and threshold2 : " + distance)
                    # TODO
                    pass
                else:
                    # we might have event type 2. but need to check if it is too close
                    if distance <= Constants.TOO_CLOSE_DISTANCE_THRESHOLD:
                        # ignore
                        log.info("Object is too close : " + distance)
                        # TODO
                        pass
                    else:
                        # we have event type 2
                        log.info("Object is withing threshold2 and it is not too close : " + distance)
                        event_type = Constants.EVENT_TYPE_OBJECT_WITHIN_THRESHOLD2
                        # TODO
                        pass
            else:
                # ignore the object since it is too far away
                log.info("Object is too far away : " + distance)
                pass

            try:
                # TODO: do not send it every time!
                # TODO: do it in background
                if event_type:
                    log.info("Gonna broadcast event : " + event_type)
                    self._sensor_service.broadcastEvent(event_type)
            except:
                log.error("Error broadcasting event : " + event_type)  # TODO log exception as well
                pass  # TODO

                # TODO : sleep some time before measuring again

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
            log.critical("Program didn't handle the exception. Probably it wanted termination.")  # log error exception
            self._on_exit()