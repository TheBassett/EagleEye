ERAU EagleEye Live Locator App 

Version: 1.0 
Date: 28 Oct 2015
Group: Mystery Inc
Members: Jessica Updegrove, Alex Bassett, Dean Laga, Jonathan Rach, Joel Vande Polder
Class: ERAU SE 300
Instructor: Dr. Jafer

GENERAL USAGE NOTES
---------------------

- The current application processes an image from file using the OpenCV library for Java. Through processing, objects are detected and lines drawn around contours. 

- This is accomplished through a JavaFX GUI found in DeviceTestGUI. 

- The application should be run through Eclipse

- OpenCV needs to be added to the file path as a new library.

- As of now there is no logic that determines what is in the picture.

- The code is being used to develop the back end of an eventual Android app to be used to identify buildings on Embry-Riddle's Daytona Beach Campus

- The hue, saturation, and values can be set manually by adjusting the values in the minValues and maxValues scalars found on lines 76 and 77.  The order is as follows for both scalars (hue, saturation, values)


BEFORE YOU RUN
----------------
In the current Setup
	- Change the path parameter in System.load in PicProcessing class on line 62 of PicProcessing so that is points to the correct library
	- The file path for the result Image location needs to be changed so that it points to the correct image. This is found on line 100 in DeviceTestGUI

ACKNOWLEDGMENTS
----------------
The image processing was accomplished with help from the Face Recognition and Object Detection examples on readthedoc.org and can be found at: opencv-java-tutorials.redthedoc.org/en/latest/#