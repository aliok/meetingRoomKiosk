# coding=utf-8
__author__ = 'ali ok'

import time


class HardwareMock:
    """
    Mock for hardware module.
    @see hardware.py
    """

    def __init__(self):
        self._initialized = False

    def initialize_gpio(self):
        time.sleep(0.5)  # add some wait-time to make it more realistic
        self._initialized = True

    def measure(self):
        if not self._initialized:
            raise Exception("GPIO not initialized!")
        # TODO convert it to have series of events
        time.sleep(0.3)
        return 45.0
