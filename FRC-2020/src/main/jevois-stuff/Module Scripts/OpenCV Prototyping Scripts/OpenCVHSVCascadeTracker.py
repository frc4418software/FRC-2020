import libjevois as jevois
import cv2
import numpy as np

cap = cv2.VideoCapture(0)

weights_path = "cascade.xml"
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


if not (cap.isOpened()):
    print('\nCould not open video device\n')


while(True):
    ret, frame = cap.read()

    resized_frame = cv2.resize(frame, (320, 240))
    cv2.imshow('resized', resized_frame)

    # Get input BGR image from JeVois
    bgr_frame = resized_frame
    
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

        outimg = rect_frame

    # If list of returned rectangles in image IS empty
    elif len(power_cells) == 0:
        # Output BGR image without rectangles (without identified balls because couldn't find any)
        outimg = bgr_frame
    
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()