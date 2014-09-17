# coding=utf-8
import logging

log = logging.getLogger(__name__)


class HeartbeatMock:
    # TODO doc me
    """
    """

    def start_heartbeat_thread(self):
        # TODO: start the background thread
        # TODO: check token first
        pass

    def sendSync(self, type, message=None, exc_info=None):
        # TODO
        # TODO: check token first
        # TODO: add sleep time to make it more realistic
        pass

    def stop_heartbeat_thread(self):
        # TODO
        # TODO: check token first
        pass

    def set_token(self, token):
        # TODO
        pass