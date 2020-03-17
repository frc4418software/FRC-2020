import libjevois as jevois
import cv2
import numpy as np

# @videomapping YUYV 340 240 60.0 YUYV 340 240 60.0 JeVois PythonCascadeTracker

def Pipeline(has_out_frame):
    # Start measuring image processing time (NOTE: does not account for input conversion time):
    self.timer.start()


    #================== ACTUAL PIPELINE ===========================================================
    #==============================================================================================
    #==============================================================================================


    bgr_frame = inframe.getCvBGR()

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
        
        # Cast the calculated area of the drawn rectangle into a string
        str_rect_size = str(w * h)

        # Use hard-wired serial port to send the rectangle center coords and area as strings
        jevois.sendSerial(str_xcenter + str_delim + str_ycenter + str_delim + str_rect_size)

        outimg = rect_frame

    # If list of returned rectangles in image IS empty
    elif len(power_cells) == 0:
        # Send serial message indicator to roboRio that there are no balls found
        jevois.sendSerial(nopeString)

        # Output BGR image without rectangles (without identified balls because couldn't find any)
        outimg = bgr_frame

    #================== END OF PIPELINE ===========================================================
    #==============================================================================================
    #==============================================================================================


    # Write frames/s info from our timer into the edge map (NOTE: does not account for output conversion time):
    fps = self.timer.stop()

    if has_out_frame == True:
        # Convert our OpenCv output image to video output format and send to host over USB:
        outframe.sendCv(outimg)



class PythonCascadeTracker:
    # Initializer method when creating PythonCascadeTracker object
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("cascade-classifier", 100, jevois.LOG_INFO)

        global power_cell_cascade, scale_factor, min_neighbors, str_delim, nopeString

        # String to say there's nothing
        nopeString = "n"

        # String to seperate data to be parsed
        str_delim = ','

        weights_path = "/jevois/data/cascade.xml"

        power_cell_cascade = cv2.CascadeClassifier(weights_path)

        if not power_cell_cascade.load(cv2.samples.findFile(weights_path)):
            jevois.sendSerial("Error loading cascade")

        scale_factor = 1.01

        min_neighbors = 3

    # Process function without USB output
    def processNoUSB(self, inframe):
        Pipeline(False)

    # Process function with USB output
    def process(self, inframe, outframe):
        Pipeline(True)
