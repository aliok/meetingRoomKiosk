# coding=utf-8
import logging

logging.basicConfig(level=logging.INFO)
log = logging.getLogger(__name__)  # TODO try to have a rolling log so that the log size doesn't exceed some days


class Heartbeat:
    """
    # Sends heartbeat regularly to notify server that the sensor is alive.
    # It is done in a separate thread so that nothing is blocked.
    """

    # TODO: start the thread with low prioirty

    def start_heartbeat_thread(self):
        # TODO: start the background thread
        pass

    def sendSync(self, type, message=None, exc_info=None):
        # TODO
        pass

    def stop_heartbeat_thread(self):
        # TODO
        pass