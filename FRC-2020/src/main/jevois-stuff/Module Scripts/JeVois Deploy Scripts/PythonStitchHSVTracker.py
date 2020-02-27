import libjevois as jevois
import cv2
import numpy as np

# @videomapping YUYV 340 252 60.0 YUYV 340 252 60.0 JeVois FRC2020

class PythonStitchHSVTracker:

    # Initializer method when creating FRC2020 object
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("sandbox", 100, jevois.LOG_INFO)

        global lowHue_1, highHue_1, lowSat_1, highSat_1, lowVal_1, highVal_1,
        lowHue_2, highHue_2, lowSat_2, highSat_2, lowVal_2, highVal_2, area_min, area_max

        # Threhold for first hue in HSV
        lowHue_1 = 10
        highHue_1 = 72

        # Threshold for first saturation in HSV
        lowSat_1 = 0
        highSat_1 = 193

        # Threshold for first value in HSV
        lowVal_1 = 212
        highVal_1 = 255



        # Threshold for second hue in HSV
        lowHue_2 = 32
        highHue_2 = 38

        # Threshold for second saturation in HSV
        lowSat_2 = 117
        highSat_2 = 242

        # Threshold for second value in HSV
        lowVal_2 = 128
        highVal_2 = 219


        # Threshold for largest contour found
        area_min = 170
        area_max = 6000

    ## Process function with USB output
    def process(self, inframe, outframe):

        # Start measuring image processing time (NOTE: does not account for input conversion time):
        self.timer.start()


        #================== ACTUAL PIPELINE ===========================================================
        #==============================================================================================
        #==============================================================================================


        # Get BGR image input
        frame = inframe.getCvBGR()

        # Convert BGR input image to first HSV output
        frameHSV = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

        # Create numpy arrays for two HSV thresholds
        thresh1_colorLow = np.array([lowHue_1,lowSat_1,lowVal_1])
        thresh1_colorHigh = np.array([highHue_1,highSat_1,highVal_1])

        # Create numpy array for second HSV threshold
        thresh2_colorLow = np.array([lowHue_2, lowSat_2, lowVal_2])
        thresh2_colorHigh = np.array([highHue_2,highSat_2,highVal_2])

        # Create HSV threshold mask images from first HSV output
        mask_1 = cv2.inRange(frameHSV, thresh1_colorLow, thresh1_colorHigh)

        mask_2 = cv2.inRange(frameHSV, thresh2_colorLow, thresh2_colorHigh)

        # Create a stitched together image that combines both masks into one image
        total_mask = cv2.bitwise_or(mask_1, mask_2)

        # Find contours from the total mask image
        contours, hierarchy = cv2.findContours(total_mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

        # Draw contours after finding contours from total mask image
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
                jevois.sendSerial('x' + str_xcenter)
                jevois.sendSerial('y' + str_ycenter)

                # NOTE:  to see if getting center of rectangle is correct
                #frame_center = cv2.circle(frame_rect, (xcenter, ycenter), 2, (255, 0, 0), -1)

                # Use post-processed image (that has rect containing the biggest contour) as the output image
                outimg = frame_rect

            # If largest contour found is NOT within area threshold
            else:

                # Send filler message to jevois using hard-wired serial port saying that there is NOT a single contour found
                jevois.sendSerial('nahs')

                # Use post-processed image (that does NOT have contour within area threshold) as the output image
                outimg = frame_rect

        # If the list of contour areas IS empty (also means that there is NOT least one contour)
        else:
            # Use post-processed image (that does NOT have any contours) as the output image
            outimg = frame_with_contours


        #================== ACTUAL PIPELINE ===========================================================
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
