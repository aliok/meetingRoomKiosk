# coding=utf-8

__author__ = 'ali ok'


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
        # TODO : fill

        # check token existence first
        if not self._token:
            raise Exception("No token set!")

        # TODO: very short timeout!
        # TODO: make short timeout application wide
        # TODO: do it in synchronously
        pass

    def set_token(self, token):
        self._token = token

