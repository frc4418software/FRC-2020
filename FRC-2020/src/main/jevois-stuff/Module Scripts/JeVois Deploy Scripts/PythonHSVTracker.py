import libjevois as jevois
import cv2
import numpy as np

# @videomapping YUYV 340 252 60.0 YUYV 340 252 60.0 JeVois FRC2020

def Pipeline:
    # Start measuring image processing time (NOTE: does not account for input conversion time):
    self.timer.start()


    #================== ACTUAL PIPELINE ===========================================================
    #==============================================================================================
    #==============================================================================================

    # STEP ONE: Get BGR image input
    frame = inframe.getCvBGR()

    # STEP TWO: Convert BGR input image to first HSV output
    frameHSV = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    # STEP THREE: Create HSV threshold mask image from first HSV output
    mask = cv2.inRange(frameHSV, colorLow, colorHigh)

    # Find contours from mask image
    contours, hierarchy = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    # Draw contours after finding contours from mask image
    frame_with_contours = cv2.drawContours(frame, contours, -1, (0,255,0), 3)

    # Create list of all contour areas from all found contours
    contour_areas = [(cv2.contourArea(contour), contour) for contour in contours]

    # If the list of contour areas is NOT empty (also means that there is at least one contour)
    if (len(contour_areas) != 0):

        # Find the biggest contour by looking for the biggest contour, using the list of contours areas
        biggest_contour = max(contour_areas, key=lambda x: x[0])[1]

        # Calculates the bounding corner points that define a generalized rectangle containing the biggest contour
        x,y,w,h = cv2.boundingRect(biggest_contour)

        # Get the area of the biggest contour
        area = cv2.contourArea(biggest_contour)

        # Draw a generalized rectangle around this biggest contour using the calculated bounding cornor points
        frame_rect = cv2.rectangle(frame,(x,y),(x+w,y+h),(0,255,0),2)

        # If largest contour found is within area threshold
        if ((area > area_min) and (area < area_max)):

            # Calculate the center of the rectangle containing the biggest contour
            xcenter = int(x + (w/2))
            ycenter = int(y + (h/2))

            # Cast the calculated center coords of the rectangle into a string
            str_xcenter = str(xcenter)
            str_ycenter = str(ycenter)

            # Use hard-wired serial port to send the rectangle center coords as strings, with the terminating char 's'
            jevois.sendSerial(str_xcenter+','+str_ycenter)

            # NOTE:  to see if getting center of rectangle is correct
            #frame_center = cv2.circle(frame_rect, (xcenter, ycenter), 2, (255, 0, 0), -1)

            # Use post-processed image (that has rect containing the biggest contour) as the output image
            outimg = frame_rect

        # If largest contour found is NOT within area threshold
        else:

            # Send filler message to jevois using hard-wired serial port saying that there is NOT a single contour found
            jevois.sendSerial(nopeString)

            # Use post-processed image (that does NOT have contour within area threshold) as the output image
            outimg = frame_rect

    # If the list of contour areas IS empty (also means that there is NOT least one contour)
    else:

        # Send filler message to jevois using hard-wired serial port saying that there is NOT a single contour found
        jevois.sendSerial(nopeString)

        # Use post-processed image (that does NOT have any contours) as the output image
        outimg = frame_with_contours


    #================== END OF PIPELINE ===========================================================
    #==============================================================================================
    #==============================================================================================


    # Write frames/s info from our timer into the edge map (NOTE: does not account for output conversion time):
    fps = self.timer.stop()
    #outheight = outimg.shape[0]
    #outwidth = outimg.shape[1]
    #cv2.putText(outimg, fps, (3, outheight - 6), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255))

    # Convert our OpenCv output image to video output format and send to host over USB:
    outframe.sendCv(outimg)


class PythonHSVTracker:
    # Initializer method when creating FRC2020 object
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("sandbox", 100, jevois.LOG_INFO)

        global lowHue, highHue, lowSat, highSat, lowVal, highVal, area_min, area_max, colorLow, colorHigh, nopeString

        # String to say there's nothing
        nopeString = "n"

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

        # Threshold for largest contour found
        area_min = 170
        area_max =

    def processNoUSB(self, inframe):
        Pipeline()

    ## Process function with USB output
    def process(self, inframe, outframe):
        Pipeline()
