import libjevois as jevois
import cv2
import numpy as np

# @videomapping YUYV 340 252 60.0 YUYV 340 252 60.0 JeVois FRC2020

def Pipeline(inimg, has_out_frame=False):
    # Start measuring image processing time (NOTE: does not account for input conversion time):
    #self.timer.start()

    #================== ACTUAL PIPELINE ===========================================================
    #==============================================================================================
    #==============================================================================================

    # Convert BGR JeVois input image to HSL
    frame_HLS = cv2.cvtColor(inimg, cv2.COLOR_BGR2HLS)
    # Make HSL mask using defined range
    HLS_mask = cv2.inRange(frame_HLS, HLS_low_thresh, HLS_high_thresh)

    # Erode the HSL mask to zero-out small noise
    HLS_mask_eroded = cv2.erode(HLS_mask, kernel=None, iterations=HLS_erode_its, borderType=cv2.BORDER_CONSTANT)
    # Dilate the HSL mask to focus on non-zero pixels (not noise)
    HSL_mask_dilated = cv2.dilate(HLS_mask_eroded, kernel=None, iterations=HLS_dilate_its, borderType=cv2.BORDER_CONSTANT)


    # Convert BGR JeVois input image to HSV
    frame_HSV = cv2.cvtColor(inimg, cv2.COLOR_BGR2HSV)
    # Make HSV mask using defined range
    HSV_mask = cv2.inRange(frame_HSV, HSV_low_thresh, HSV_high_thresh)

    # Erode the HSV mask to zero-out small noise
    HSV_mask_eroded = cv2.erode(HSV_mask, kernel=None, iterations=HSV_erode_its, borderType=cv2.BORDER_CONSTANT)
    # Dilate the HSV mask to focus on non-zero pixels (not noise)
    HSV_mask_dilated = cv2.dilate(HSV_mask_eroded, kernel=None, iterations=HSV_dilate_its, borderType=cv2.BORDER_CONSTANT)


    # 'Stitch' together the pixel blobs found from HSL or HSV
    bitwise_and_frame = cv2.bitwise_and(HSL_mask_dilated, HSV_mask_dilated)
    # Erode the stitch image to zero-out more small noise
    bitwise_and_eroded = cv2.erode(bitwise_and_frame, kernel=None, iterations=bitwise_and_erode_its, borderType=cv2.BORDER_CONSTANT)
    # Scale up the non-zero pixel blobs to be more noticeable as blobs
    bitwise_and_dilated = cv2.dilate(bitwise_and_eroded, kernel=None, iterations=bitwise_and_dilate_its, borderType=cv2.BORDER_CONSTANT)


    # Store a list of all found contours
    contours, hierarchy = cv2.findContours(bitwise_and_dilated, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    # New frame that is copy of JeVois input image, but with the contours drawn on top of it
    contours_frame = cv2.drawContours(inimg, contours, -1, (0,255,0), 3)
    # Get the size of each contour in the list of contours
    contour_areas = [(cv2.contourArea(contour), contour) for contour in contours]

    # If there was at least one contour found
    if (len(contour_areas) != 0):
        # Find the biggest contour by looking for the biggest contour, using the list of contours areas
        biggest_contour = max(contour_areas, key=lambda x: x[0])[1]

        # Calculates the bounding corner points that define a generalized rectangle containing the biggest contour
        x,y,w,h = cv2.boundingRect(biggest_contour)

        # Get the area of the biggest contour
        area = cv2.contourArea(biggest_contour)

        # Draw a generalized rectangle around this biggest contour using the calculated bounding cornor points
        frame_rect = cv2.rectangle(inimg,(x,y),(x+w,y+h),(0,255,0),2)

        # If the largest contour found is within the size (aka area) range
        if ((area > area_min) and (area < area_max)):
            # Calculate the center of the rectangle containing the biggest contour
            xcenter = int(x + (w/2))
            ycenter = int(y + (h/2))

            # Cast the calculated center coords of the rectangle into a string
            str_xcenter = str(xcenter)
            str_ycenter = str(ycenter)

            # Cast the calculated area of the drawn rectangle into a string
            str_rect_size = str(w * h)

            # Use hard-wired serial port to send the rectangle center coords as strings, with the terminating char 's'
            jevois.sendSerial(str_xcenter + str_delim + str_ycenter + str_delim + str_rect_size + str_delim)

            # Set debugging output image to 'found contours' image with rectangle drawn around the largest contour
            outimg = frame_rect

        # If largest contour found is NOT within area threshold
        else:
            # Send filler message to jevois using hard-wired serial port saying that there is NOT a single contour found
            jevois.sendSerial(nopeString)

            # Set debugging output image to 'found contours' image with rectangle drawn around the largest contour
            outimg = frame_rect

    # If there wasn't a single eligible contour found
    else:

        # Send filler message to jevois using hard-wired serial port saying that there is NOT a single contour found
        jevois.sendSerial(nopeString)

        # Use post-processed image (that does NOT have any contours) as the output image
        outimg = contours_frame


    #================== END OF PIPELINE ===========================================================
    #==============================================================================================
    #==============================================================================================

    #fps = self.timer.stop()
    if has_out_frame == True:        
        return outimg


class PythonZeroSpotTracker:

    # Initializer method when creating FRC2020 object
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("sandbox", 100, jevois.LOG_INFO)


        global HLS_low_thresh, HLS_high_thresh, HLS_erode_its, HLS_dilate_its, HSV_low_thresh, HSV_high_thresh, HSV_erode_its, HSV_dilate_its, bitwise_and_erode_its, bitwise_and_dilate_its, area_min, area_max, nopeString, str_delim

        # String to say there's nothing
        nopeString = "n"

        # String to seperate data to be parsed
        str_delim = ','

        # HLS (aka HSL) range
        HLS_low_thresh = np.array([21, 43, 0])
        HLS_high_thresh = np.array([31,255,251])
        # HSL erode and dilate iterations
        HLS_erode_its = 2
        HLS_dilate_its = 4

        # HSV range
        HSV_low_thresh = np.array([21, 41, 73])
        HSV_high_thresh = np.array([35, 210, 255])
        # HSV erode and dilate iterations
        HSV_erode_its = 2
        HSV_dilate_its = 5

        # Bitwise OR (aka 'stitch layer') erode and dilate iterations
        bitwise_and_erode_its = 5
        bitwise_and_dilate_its = 5

        # Contour area range
        area_min = 170
        area_max = 6000

    def processNoUSB(self, inframe):
        # Get JeVois BGR image input
        bgr_frame = inframe.getCvBGR()
        Pipeline(inimg=bgr_frame, has_out_frame=False)

    ## Process function with USB output
    def process(self, inframe, outframe):
        # Get JeVois BGR image input
        bgr_frame = inframe.getCvBGR()
        outimg = Pipeline(inimg=bgr_frame, has_out_frame=True)
        # Convert our OpenCv output image to video output format and send to host over USB:
        outframe.sendCv(outimg)
