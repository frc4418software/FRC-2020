import libjevois as jevois
import cv2
import numpy as np

def SendNoValidData():
    # Send filler message saying that there is no valid data to send
    jevois.sendSerial(nopeString)

def Pipeline(inimg, has_out_frame=False):

    frame_HSV = cv2.cvtColor(inimg, cv2.COLOR_BGR2HSV)
    HSV_mask = cv2.inRange(frame_HSV, HSV_low_thresh, HSV_high_thresh)
    #cv2.imshow('HSV mask', HSV_mask)

    HSV_mask_eroded = cv2.erode(HSV_mask, kernel=None, iterations=HSV_erode_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HSV eroded', HSV_mask_eroded)

    HSV_mask_dilated = cv2.dilate(HSV_mask_eroded, kernel=None, iterations=HSV_dilate_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HSV dilated', HSV_mask_dilated)


    contours, hierarchy = cv2.findContours(inimg, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    height, width, _ = resized_frame.shape
    min_x, min_y = width, height
    max_x = max_y = 0

    # Computes the bounding box for the contour, and draws it on the frame,
    for contour, hier in zip(contours, hierarchy):
        (x,y,w,h) = cv2.boundingRect(contour)
        min_x, max_x = min(x, min_x), max(x+w, max_x)
        min_y, max_y = min(y, min_y), max(y+h, max_y)
        if w > 80 and h > 80:
            complete_rect_frame = cv2.rectangle(resized_frame, (x,y), (x+w,y+h), (255, 0, 0), 2)
        else:
            SendNoValidData()
    
    # If the overall contour's size is bigger than nothing
    if max_x - min_x > 0 and max_y - min_y > 0:
        complete_rect_frame = cv2.rectangle(resized_frame, (min_x, min_y), (max_x, max_y), (255, 0, 0), 2)
        outimg = complete_rect_frame

        # Calculate the center of the rectangle containing the biggest contour
        xcenter = int((max_x - min_x) / 2)
        ycenter = int((y_max - y_min) / 2)
        # Cast the calculated center coords of the rectangle into a string
        str_xcenter = str(xcenter)
        str_ycenter = str(ycenter)

        # Cast the calculated area of the drawn rectangle into a string
        str_rect_size = str(w * h)

        # Use hard-wired serial port to send the rectangle center coords and rectangle size as strings
        jevois.sendSerial(str_xcenter + str_delim + str_ycenter + str_delim + str_rect_size)
    else:
        SendNoValidData()

        # Use post-processed image (that does NOT have contour within area threshold) as the output image
        outimg = resized_frame

    if has_out_frame == True:
        # Convert our output image to video output format and send to host over USB:
        outframe.sendCv(outimg)



class PythonReflectiveHSVTracker:
    # ###################################################################################################
    ## Constructor
    def __init__(self):
        global nopeString, HSV_low_thresh, HSV_high_thresh, HSV_erode_its, HSV_dilate_its, width_min, height_min, str_delim

        # String to say there's nothing
        nopeString = "n"

        # String to seperate data to be parsed
        str_delim = ','

        # Simple HSV array low-to-high threshold
        HSV_low_thresh = np.array([78, 30, 229])
        HSV_high_thresh = np.array([101, 103, 255])

        # HSV post-processing erode and dilate iterations config
        HSV_erode_its = 2
        HSV_dilate_its = 4

        #TODO: Check these, I just kinda guessed
        width_min = 40
        height_min = 20

    # ###################################################################################################
    ## Process function with no USB output
    def processNoUSB(self, inframe):
        get_inframe = inframe.getCvBGR()
        Pipeline(inimg=get_inframe, has_out_frame=False)

    # ###################################################################################################
    ## Process function with USB output
    def process(self, inframe, outframe):
        get_inframe = inframe.getCvBGR()
        Pipeline(inimg=get_inframe, has_out_frame=True)
