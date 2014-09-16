# coding=utf-8
import logging

__author__ = 'ali ok'

log = logging.getLogger(__name__)


class SensorServiceMock:
    # TODO: document me
    """
    """


    def __init__(self):
        pass

    def broadcastEvent(self, event_type):
        log.info("Mock broadcast : " + event_type)

    def set_token(self, token):
        log.info("Mock token set : " + token)