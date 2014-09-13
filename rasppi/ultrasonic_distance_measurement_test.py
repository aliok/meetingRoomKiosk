# coding=utf-8
# Original source and RaspPi setup :
# https://www.modmypi.com/blog/hc-sr04-ultrasonic-range-sensor-on-the-raspberry-pi
#
# Also used following sources :
# http://www.raspberrypi-spy.co.uk/2012/12/ultrasonic-distance-measurement-using-python-part-1/
# http://www.raspberrypi-spy.co.uk/2013/01/ultrasonic-distance-measurement-using-python-part-2/

import time

import RPi.GPIO as GPIO

# name our input and output pins
TRIG = 23  # output pin which triggers the sensor                    : GPIO 23 [Pin 16]
ECHO = 24  # input pin which reads the return signal from the sensor : GPIO 24 [Pin 18]


def initializeGPIO():
    # Use BCM GPIO references
    # instead of physical pin numbers
    GPIO.setmode(GPIO.BCM)

    # set GPIO ports
    GPIO.setup(TRIG, GPIO.OUT)
    GPIO.setup(ECHO, GPIO.IN)

    # ensure that the Trigger pin is set low, and give the sensor a some time to settle
    GPIO.output(TRIG, False)
    time.sleep(0.5)


def measure():
    # global pulse_start, pulse_end, pulse_duration, distance
    # HC-SR04 sensor requires a short 10uS pulse to trigger the module, which will cause the sensor to start the ranging
    # program (8 ultrasound bursts at 40 kHz) in order to obtain an echo response. So, to create our trigger pulse,
    # we set out trigger pin high for 10uS then set it low again.
    GPIO.output(TRIG, True)
    time.sleep(0.00001)
    GPIO.output(TRIG, False)

    # Now that we’ve sent our pulse signal we need to listen to our input pin, which is connected to ECHO. The sensor
    # sets ECHO to high for the amount of time it takes for the pulse to go and come back, so our code therefore needs
    # to measure the amount of time that the ECHO pin stays high. We use the “while” string to ensure that each signal
    # timestamp is recorded in the correct order.
    # The time.time() function will record the latest timestamp for a given condition. For example, if a pin goes from
    # low to high, and we’re recording the low condition using the time.time() function, the recorded timestamp will be
    # the latest time at which that pin was low.
    # Our first step must therefore be to record the last low timestamp for ECHO (pulse_start) e.g. just before the
    # return signal is received and the pin goes high.
    pulse_start = time.time()
    while GPIO.input(ECHO) == 0:
        pulse_start = time.time()

    # Once a signal is received, the value changes from low (0) to high (1), and the signal will remain high for the
    # duration of the echo pulse. We therefore also need the last high timestamp for ECHO (pulse_end).
    pulse_end = time.time()
    while GPIO.input(ECHO) == 1:
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
    print "Distance:", distance, "cm"


# Finally, we clean our GPIO pins to ensure that all inputs/outputs are reset
GPIO.cleanup()


# Wrap main content in a try block so we can
# catch the user pressing CTRL-C and run the
# GPIO cleanup function. This will also prevent
# the user seeing lots of unnecessary error
# messages.
try:
    initializeGPIO()
    while True:
        measure()
        time.sleep(1)

except KeyboardInterrupt:  # User pressed CTRL-C
    # Reset GPIO settings
    GPIO.cleanup()