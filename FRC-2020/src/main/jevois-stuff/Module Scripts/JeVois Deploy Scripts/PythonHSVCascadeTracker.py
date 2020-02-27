import libjevois as jevois
import cv2
import numpy as np

# @videomapping YUYV 340 240 60.0 YUYV 340 240 60.0 JeVois PythonCascadeTracker

class PythonHSVCascadeTracker:

    # Initializer method when creating PythonCascadeTracker object
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("cascade-classifier", 100, jevois.LOG_INFO)

        global power_cell_cascade, scale_factor, min_neighbors, lowHue, highHue, lowSat, highSat, lowVal, highVal,
        colorLow, colorHigh

        weights_path = "/jevois/data/cascade.xml"

        power_cell_cascade = cv2.CascadeClassifier(weights_path)

        if not power_cell_cascade.load(cv2.samples.findFile(weights_path)):
            jevois.sendSerial("Error loading cascade")

        # Config variables
        scale_factor = 1.01
        min_neighbors = 3

        # Threhold for hue in HSV
        lowHue = 23
        highHue = 57

        # Threshold for saturation in HSV
        lowSat = 89
        highSat = 169

        # Threshold for value in HSV
        lowVal = 34
        highVal = 188

        # Create numpy array for HSV threshold
        colorLow = np.array([lowHue,lowSat,lowVal])
        colorHigh = np.array([highHue,highSat,highVal])

    # Process function without USB output
    def processNoUSB(self, inframe):
        # idk what to put here
        jevois.LFATAL("process no usb not implemented")

    # Process function with USB output
    def process(self, inframe, outframe):
        # Start measuring image processing time (NOTE: does not account for input conversion time):
        self.timer.start()


        #================== ACTUAL PIPELINE ===========================================================
        #==============================================================================================
        #==============================================================================================

        # Get input BGR image from JeVois
        bgr_frame = inframe.getCvBGR()
        
        # Convert image to HSV and filter out desired color
        hsv_frame = cv2.cvtColor(bgr_frame, cv2.COLOR_BGR2HSV)
        mask = cv2.inRange(hsv_frame, colorLow, colorHigh)

        #TODO figure out how to apply HSV mask to BGR input image, so cascade doesn't have to do calculations on noise
        
        gray_frame = cv2.cvtColor(bgr_frame, cv2.COLOR_BGR2GRAY)

        gray_frame = cv2.equalizeHist(gray_frame)

        power_cells = power_cell_cascade.detectMultiScale(gray_frame, scale_factor, min_neighbors)

        # If list of returned rectangles in image is not empty
        if len(power_cells) != 0:

            for (x, y, w, h) in power_cells:

                center = (x + (w//2), y + (h//2))

                rect_frame = cv2.rectangle(bgr_frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

            # Cast the calculated center coords of the rectangle into a string
            str_xcenter = str(x + (w/2))
            str_ycenter = str(y + (h/2))

            # Use hard-wired serial port to send the rectangle center coords as strings, with the terminating char 's'
            jevois.sendSerial('x' + str_xcenter)
            jevois.sendSerial('y' + str_ycenter)

            outimg = rect_frame

        # If list of returned rectangles in image IS empty
        elif len(power_cells) == 0:
            # Send serial message indicator to roboRio that there are no balls found
            jevois.sendSerial('nah')

            # Output BGR image without rectangles (without identified balls because couldn't find any)
            outimg = bgr_frame
        

        #================== END OF PIPELINE ===========================================================
        #==============================================================================================
        #==============================================================================================






        # NOTE: Write a title:
        #cv2.putText(outimg, "JeVois Python Sandbox", (3, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255))

        # Write frames/s info from our timer into the edge map (NOTE: does not account for output conversion time):
        fps = self.timer.stop()
        
        #outheight = outimg.shape[0]
        #outwidth = outimg.shape[1]
        #cv2.putText(outimg, fps, (3, outheight - 6), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255))

        # Convert our OpenCv output image to video output format and send to host over USB:
        outframe.sendCv(outimg)
