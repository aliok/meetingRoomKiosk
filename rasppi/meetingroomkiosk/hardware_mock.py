# coding=utf-8
__author__ = 'ali ok'

import time


class HardwareMock:
    """
    Mock for hardware module. Sends a series of measurements
    @see hardware.py
    """

    def __init__(self):
        self._initialized = False
        self._currentEventSeriesIndex = -1

    def initialize_gpio(self):
        time.sleep(0.75)  # add some wait-time to make it more realistic
        self._initialized = True
        self._currentEventSeriesIndex = 0

    def clean_up(self):
        self._initialized = False
        self._currentEventSeriesIndex = 0

    def measure(self):
        if not self._initialized:
            raise Exception("GPIO not initialized!")

        series = HardwareMock._event_series_7()

        if self._currentEventSeriesIndex >= len(series):
            time.sleep(0.3)
            raise Exception("End of series")
        else:
            return_value = series[self._currentEventSeriesIndex]
            self._currentEventSeriesIndex += 1
            return return_value

    @staticmethod
    def _event_series_1():
        """
        No object in sight at all
        """
        return [5000, 5000, 5000, 5000, 5000, 5000]

    @staticmethod
    def _event_series_2():
        """
        Object gets between threshold1 and threshold 2 then goes away
        """
        return [5000, 500, 299, 200, 299, 500, 5000]

    @staticmethod
    def _event_series_3():
        """
        Object gets into threshold 2 then goes away
        """
        return [5000, 500, 299, 200, 149, 299, 5000]

    @staticmethod
    def _event_series_4():
        """
        Object gets into threshold 2 then goes away instantly
        """
        return [5000, 500, 299, 200, 149, 5000, 5000]

    @staticmethod
    def _event_series_5():
        """
        Object gets between threshold1 and threshold2 then goes away instantly
        """
        return [5000, 500, 299, 5000, 5000]

    @staticmethod
    def _event_series_6():
        """
        Object gets into threshold2 and stays there for a while
        """
        return [5000, 500, 299, 149, 100, 100, 100, 299, 5000]

    @staticmethod
    def _event_series_7():
        """
        Object gets into threshold2 and then becomes too close
        """
        return [5000, 500, 299, 149, 100, 5, 5, 5, 5, 5, 5000]