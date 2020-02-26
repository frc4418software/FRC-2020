import cv2
import numpy as np

cap = cv2.VideoCapture(0)

HLS_low_thresh = np.array([21,43,0])
HLS_high_thresh = np.array([31,255,251])
HLS_erode_its = 2
HLS_dilate_its = 4

HSV_low_thresh = np.array([21, 41, 73])
HSV_high_thresh = np.array([35, 210, 255])
HSV_erode_its = 2
HSV_dilate_its = 5

bitwise_and_erode_its = 5
bitwise_and_dilate_its = 5

area_min = 170
area_max = 6000


if not (cap.isOpened()):
    print('\nCould not open video device\n')

while(True):
    ret, frame = cap.read()

    resized_frame = cv2.resize(frame, (320, 240))
    cv2.imshow('resized', resized_frame)

    frame_HLS = cv2.cvtColor(resized_frame, cv2.COLOR_BGR2HLS)
    HLS_mask = cv2.inRange(frame_HLS, HLS_low_thresh, HLS_high_thresh)
    #cv2.imshow('HLS mask', HLS_mask)

    HLS_mask_eroded = cv2.erode(HLS_mask, kernel=None, iterations=HLS_erode_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HLS eroded', HLS_mask_eroded)

    HSL_mask_dilated = cv2.dilate(HLS_mask_eroded, kernel=None, iterations=HLS_dilate_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HLS dilated', HSL_mask_dilated)



    frame_HSV = cv2.cvtColor(resized_frame, cv2.COLOR_BGR2HSV)
    HSV_mask = cv2.inRange(frame_HSV, HSV_low_thresh, HSV_high_thresh)
    #cv2.imshow('HSV mask', HSV_mask)

    HSV_mask_eroded = cv2.erode(HSV_mask, kernel=None, iterations=HSV_erode_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HSV eroded', HSV_mask_eroded)

    HSV_mask_dilated = cv2.dilate(HSV_mask_eroded, kernel=None, iterations=HSV_dilate_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('HSV dilated', HSV_mask_dilated)



    bitwise_and_frame = cv2.bitwise_and(HSL_mask_dilated, HSV_mask_dilated)
    #cv2.imshow('bitwise and', bitwise_and_frame)

    bitwise_and_eroded = cv2.erode(bitwise_and_frame, kernel=None, iterations=bitwise_and_erode_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('bitwise and eroded', bitwise_and_eroded)

    bitwise_and_dilated = cv2.dilate(bitwise_and_eroded, kernel=None, iterations=bitwise_and_dilate_its, borderType=cv2.BORDER_CONSTANT)
    #cv2.imshow('bitwise and dilated', bitwise_and_dilated)



    contours, hierarchy = cv2.findContours(bitwise_and_dilated, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    contours_frame = cv2.drawContours(resized_frame, contours, -1, (0,255,0), 3)
    #cv2.imshow('all contours', contours_frame)

    contour_areas = [(cv2.contourArea(contour), contour) for contour in contours]

    if (len(contour_areas) != 0):
        # Find the biggest contour by looking for the biggest contour, using the list of contours areas
        biggest_contour = max(contour_areas, key=lambda x: x[0])[1]

        # Calculates the bounding corner points that define a generalized rectangle containing the biggest contour
        x,y,w,h = cv2.boundingRect(biggest_contour)

        # Get the area of the biggest contour
        area = cv2.contourArea(biggest_contour)

        # Draw a generalized rectangle around this biggest contour using the calculated bounding cornor points
        frame_rect = cv2.rectangle(resized_frame,(x,y),(x+w,y+h),(0,255,0),2)

        if ((area > area_min) and (area < area_max)):

            # Calculate the center of the rectangle containing the biggest contour
            xcenter = int(x + (w/2))
            ycenter = int(y + (h/2))

            # Cast the calculated center coords of the rectangle into a string
            str_xcenter = str(xcenter)
            str_ycenter = str(ycenter)

            # Use hard-wired serial port to send the rectangle center coords as strings, with the terminating char 's'
            #===========================jevois.sendSerial('x' + str_xcenter)
            #===========================jevois.sendSerial('y' + str_ycenter)

            outimg = frame_rect
            cv2.imshow('outimg', outimg)

        # If largest contour found is NOT within area threshold
        else:

            # Send filler message to jevois using hard-wired serial port saying that there is NOT a single contour found
            #===========================jevois.sendSerial('nahs')

            # Use post-processed image (that does NOT have contour within area threshold) as the output image
            outimg = frame_rect
            cv2.imshow('outimg', outimg)

    # If the list of contour areas IS empty (also means that there is NOT least one contour)
    else:

        # Use post-processed image (that does NOT have any contours) as the output image
        outimg = contours_frame
        cv2.imshow('outimg', outimg)


    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
