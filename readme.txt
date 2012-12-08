THis is the software that goes with POP!

Things to remember:

1. This code simply takes data in the form of OSC messages from CCV (v1.2). The data s parsed for blob ids and position information. CCV can be downloaded from:http://ccv.nuigroup.com

2. After downloading CCV you will need to calibrate the software to work with your camera to give accurate position information. The most important thing here is to make sure the dimensions of your camera resolution match with the config file of CCV. If this is not accurate the position information will be inaccurate.

3. I tried calibrating it using the calibration mode in the GUI but as this application is not a touch screen interface and CCV is designed to work for this I couldn't make it work.
I went through a rather tedious trial and error process and manually configured the config.xml file to add calibration points.