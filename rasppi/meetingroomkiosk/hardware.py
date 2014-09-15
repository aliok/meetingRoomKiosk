# coding=utf-8
# Original source and RaspPi setup :
# https://www.modmypi.com/blog/hc-sr04-ultrasonic-range-sensor-on-the-raspberry-pi
#
# Also used following sources :
# http://www.raspberrypi-spy.co.uk/2012/12/ultrasonic-distance-measurement-using-python-part-1/
# http://www.raspberrypi-spy.co.uk/2013/01/ultrasonic-distance-measurement-using-python-part-2/
__author__ = 'ali ok'

import time

import RPi.GPIO as GPIO


class Hardware:
    """
    Provides service to measure distance of object.
    GPIO needs to be initialized first! Call initialize_gpio for that.
    """
    # name our input and output pins
    TRIG_PORT = 23  # output pin which triggers the sensor                    : GPIO 23 [Pin 16]
    ECHO_PORT = 24  # input pin which reads the return signal from the sensor : GPIO 24 [Pin 18]

    # make 3 measurements to calculate average
    MEASUREMENT_COUNT = 3

    def __init__(self):
        self._initialized = False

    def initialize_gpio(self):
        """
        Initializes the GPIO. Please note, this takes at least 0.5 second since ultrasonic module needs to be settled.
        """
        # noinspection PyBroadException
        try:
            # Use BCM GPIO references
            # instead of physical pin numbers
            GPIO.setmode(GPIO.BCM)

            # set GPIO ports
            GPIO.setup(self.TRIG_PORT, GPIO.OUT)
            GPIO.setup(self.ECHO_PORT, GPIO.IN)

            # ensure that the Trigger pin is set low, and give the sensor a some time to settle
            GPIO.output(self.TRIG_PORT, False)
            time.sleep(0.5)
            self._initialized = True
        except:
            # catch all.
            # see http://stackoverflow.com/questions/730764/try-except-in-python-how-to-properly-ignore-exceptions
            self.clean_up()
            raise  # re-raise it!

    def measure(self):
        """
        Measures the distance of object and returns it in float centimeters. Please note this measurement takes longer
        if the object is far away.
        3 measurements are made and the average is returned.
        It takes (distanceInCentimeters / 17150) * 3 seconds.
        For example, for an object which is 50 cm away, it takes at least 0.009 seconds (9 milliseconds). If there is
        no object, it takes at least 0.75 milliseconds to understand there is nothing.
        """
        if not self._initialized:
            raise Exception("GPIO not initialized!")

        try:
            total = 0
            for i in range(self.MEASUREMENT_COUNT):
                total = total + self._measure_once()

            return total / self.MEASUREMENT_COUNT
        except:
            # catch all.
            # see http://stackoverflow.com/questions/730764/try-except-in-python-how-to-properly-ignore-exceptions
            self.clean_up()
            raise  # re-raise it!

    def clean_up(self):
        """
        Cleans up GPIO and marks it as uninitialized. You must call this if program needs to shutdown for any reason.
        """
        # Reset GPIO settings
        GPIO.cleanup()
        self._initialized = False

    def _measure_once(self):
        """
        Measures the distance of object and returns it in float centimeters. Please note this measurement takes longer
        if the object is far away.
        It takes distanceInCentimeters / 17150 seconds.
        For example, for an object which is 50 cm away, it takes at least 0.003 seconds (3 milliseconds). If there is
        no object, it takes at least 0.25 milliseconds to understand there is nothing.
        """
        # global pulse_start, pulse_end, pulse_duration, distance
        # HC-SR04 sensor requires a short 10uS pulse to trigger the module, which will cause the ultrasonic sensor to
        # start the ranging program (8 ultrasound bursts at 40 kHz) in order to obtain an echo response. So, to create
        # our trigger pulse, we set out trigger pin high for 10uS then set it low again.
        GPIO.output(self.TRIG_PORT, True)
        time.sleep(0.00001)
        GPIO.output(self.TRIG_PORT, False)

        # Now that we’ve sent our pulse signal we need to listen to our input pin, which is connected to ECHO. The
        # ultrasonic sensor sets ECHO to high for the amount of time it takes for the pulse to go and come back,
        # so our code therefore needs to measure the amount of time that the ECHO pin stays high. We use the “while”
        # string to ensure that each signal timestamp is recorded in the correct order.
        # The time.time() function will record the latest timestamp for a given condition. For example, if a pin
        # goes from low to high, and we’re recording the low condition using the time.time() function, the recorded
        # timestamp will be the latest time at which that pin was low.
        # Our first step must therefore be to record the last low timestamp for ECHO (pulse_start) e.g. just before the
        # return signal is received and the pin goes high.
        pulse_start = time.time()
        while GPIO.input(self.ECHO_PORT) == 0:
            pulse_start = time.time()

        # Once a signal is received, the value changes from low (0) to high (1), and the signal will remain high for the
        # duration of the echo pulse. We therefore also need the last high timestamp for ECHO (pulse_end).
        pulse_end = time.time()
        while GPIO.input(self.ECHO_PORT) == 1:
            pulse_end = time.time()

        # We can now calculate the difference between the two recorded timestamps, and hence the duration of pulse.
        pulse_duration = pulse_end - pulse_start

        # speed of sound at sea level is the baseline as the 343m/s.
        # that is 34300 cm
        # we need to divide that with 2 since time calculated is actually time it takes for the ultrasonic pulse to
        # travel the distance to the object and back again
        # thus, just multiply with 17150
        distance = pulse_duration * 17150
        distance = round(distance, 2)
        return distance
