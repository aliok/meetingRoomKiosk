# coding=utf-8
import logging
import time

__author__ = 'ali ok'

log = logging.getLogger(__name__)


class SensorServiceMock:
    """
    Basically just logs the broadcast requests asked. It blocks execution for some time to
    have a more realistic mock.
    """

    def __init__(self):
        self._token = None

    def broadcast_event(self, event_type):
        if not self._token:
            raise Exception("No token set!")

        log.info("Mock broadcast : " + event_type)
        # add sleep time to make it more realistic
        time.sleep(0.5)

    def set_token(self, token):
        log.info("Mock token set : " + token)
        self._token = token