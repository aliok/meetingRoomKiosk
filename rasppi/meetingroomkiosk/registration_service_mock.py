# coding=utf-8
import logging
from meetingroomkiosk.server_info import SensorInfo

__author__ = 'ali ok'

log = logging.getLogger(__name__)


class RegistrationServiceMock:
    # TODO: document me
    """
    """


    def __init__(self):
        pass

    def get_sensor_info_sync(self):
        log.info("Mock get_sensor_info_sync")
        return SensorInfo({'token': 'TOKEN', 'threshold1': 300, 'threshold2': 150})

    def set_token(self, token):
        log.info("Mock token set : " + token)
