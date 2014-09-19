# coding=utf-8
import logging
import time
from server_info import SensorInfo

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

        # add sleep time to make it more realistic
        time.sleep(0.5)

        return SensorInfo({'token': 'TOKEN', 'threshold1': 300, 'threshold2': 150, 'secondsToIgnoreSameEvents': 5})
