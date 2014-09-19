# coding=utf-8
import logging

import requests

from constants import Constants
from credentials import Credentials
from server_info import SensorInfo


__author__ = 'ali ok'

log = logging.getLogger(__name__)


class RegistrationService:
    """
    Provides services for getting sensor configuration to be used in the program.
    """

    def __init__(self):
        pass

    def get_sensor_info_sync(self):
        """
        Sends a synchronous request to server about passed event.
        """

        # read the credentials and some other information from a file : credentials.py
        # very short timeout!
        # do it in synchronously

        payload = {
            'sensorKey': Credentials.SENSOR_KEY,
            'password': Credentials.PASSWORD,
            'roomKey': Credentials.ROOM_KEY}

        response = requests.get(Credentials.SERVER_END_POINT + "/registerSensor", params=payload,
                                timeout=Constants.REQUEST_REGISTRATION_TIMEOUT)
        response.raise_for_status()
        json = response.json()
        log.info("Registered successfully : " + str(json))

        return SensorInfo(json)