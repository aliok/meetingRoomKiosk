# coding=utf-8
import logging

log = logging.getLogger(__name__)


class Heartbeat:
    """
    # Sends heartbeat regularly to notify server that the sensor is alive.
    # It is done in a separate thread so that nothing is blocked.
    """

    # TODO: start the thread with low prioirty

    def start_heartbeat_thread(self):
        # TODO: start the background thread
        # TODO: check token first
        # TODO: use timeout Constants.REQUEST_HEARTBEAT_TIMEOUT
        pass

    def sendSync(self, type, message=None, exc_info=None):
        # TODO
        # TODO: check token first
        # TODO: do it in synchronously : use timeout Constants.REQUEST_HEARTBEAT_TIMEOUT
        pass

    def stop_heartbeat_thread(self):
        # TODO
        # TODO: check token first
        # TODO: send Constants.HEARTBEAT_OPERATIONAL regularly
        # TODO: use timeout Constants.REQUEST_HEARTBEAT_TIMEOUT
        pass

    def set_token(self, token):
        # TODO
        pass