import libjevois as jevois
import cv2
import numpy as np

def Pipeline(inimg, has_out_frame=False):

    frame_HSV = cv2.cvtColor(inimg, cv2.COLOR_BGR2HSV)
    HSV_mask = cv2.inRange(frame_HSV, HSV_low_thresh, HSV_high_thresh)
    #cv2.imshow('HSV mask', HSV_mask)

    HSV_mask_eroded = cv2.erode(HSV_mask, kernel=None, iterations=HSV_erode_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HSV eroded', HSV_mask_eroded)

    HSV_mask_dilated = cv2.dilate(HSV_mask_eroded, kernel=None, iterations=HSV_dilate_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HSV dilated', HSV_mask_dilated)


    contours, hierarchy = cv2.findContours(HSV_mask_dilated, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    # Get input image shape to set initial min and max xy
    height, width, _ = inimg.shape
    
    min_x, min_y = width, height
    max_x = max_y = 0

    # Cleanest way if saying "hey, is the list of contours empty?"
    if not contours:
        jevois.sendSerial(nopeString)
        outimg = inimg
    # If list of contours is NOT empty
    else:
        # Computes the bounding box for the total rect contour
        for contour, hier in zip(contours, hierarchy):
            (x,y,w,h) = cv2.boundingRect(contour)
            # Keep updating the mininum and maximum for x and y
                # Starting minimum xy is inimg width/height and starting maximum xy is 0
                    # Initial min and max xy values are meant to make any valid coord within frame
                    # the obvious min or max xy value initially
            min_x, max_x = min(x, min_x), max(x+w, max_x)
            min_y, max_y = min(y, min_y), max(y+h, max_y)

        total_width = max_x - min_x
        total_height = max_y - min_y

        # If the overall contour's size is bigger than nothing
        if total_width >= width_min and total_height >= height_min:
            # Draws the total rect contour on the input image
            total_rect_frame = cv2.rectangle(inimg, (min_x, min_y), (max_x, max_y), (255,0,0), 2)
            
            # Calculate the center of the rectangle containing the biggest contour
            xcenter = (max_x - min_x) // 2
            ycenter = (max_y - min_y) // 2
            # Cast the calculated center coords of the rectangle into a string
            str_xcenter = str(xcenter)
            str_ycenter = str(ycenter)

            # Cast the calculated area of the drawn rectangle into a string
            str_rect_size = str(total_width * total_height)

            # Use hard-wired serial port to send the rectangle center coords and rectangle size as strings
            jevois.sendSerial(str_xcenter + str_delim + str_ycenter + str_delim + str_rect_size + str_delim)
            
            outimg = total_rect_frame
        else:
            jevois.sendSerial(nopeString)

            # Use post-processed image (that does NOT have contour within area threshold) as the output image
            outimg = inimg


    if has_out_frame == True:
        # Convert our output image to video output format and send to host over USB:
        return outimg



class PythonReflectiveHSVTracker:
    # ###################################################################################################
    ## Constructor
    def __init__(self):
        global nopeString, HSV_low_thresh, HSV_high_thresh, HSV_erode_its, HSV_dilate_its, width_min, height_min, str_delim, str_termi_char

        # String to seperate data to be parsed
        str_delim = ','

        # String to say there's nothing
        nopeString = "n"

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
        outimg = Pipeline(inimg=get_inframe, has_out_frame=True)
        outframe.sendCv(outimg)