# coding=utf-8
import logging

import requests

from constants import Constants
from credentials import Credentials

__author__ = 'ali ok'

log = logging.getLogger(__name__)


class SensorService:
    """
    Provides services for interacting the server side.
    """

    def __init__(self):
        self._token = None

    def broadcast_event(self, event_type):
        """
        Sends a synchronous request to server about passed event.
        """
        # check token existence first
        if not self._token:
            raise Exception("No token set!")

        # very short timeout!
        # do it in synchronously

        payload = {'token': self._token, 'eventType': event_type}
        response = requests.get(Credentials.SERVER_END_POINT + "/notifySensorEvent", params=payload,
                                timeout=Constants.REQUEST_BROADCAST_TIMEOUT * 10)
        response.raise_for_status()

        # no result is returned since server notifies display asynchronously
        log.info("Message broadcasted successfully! " + str(payload))

    def set_token(self, token):
        self._token = token

