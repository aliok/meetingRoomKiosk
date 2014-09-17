# coding=utf-8

__author__ = 'ali ok'


class SensorInfo:
    # TODO: document me
    """
    """

    def __init__(self, dictionary):
        # TODO
        self.threshold1 = dictionary['threshold1']
        self.threshold2 = dictionary['threshold2']

        self.seconds_to_ignore_same_events = dictionary['secondsToIgnoreSameEvents']

        self.token = dictionary['token']


