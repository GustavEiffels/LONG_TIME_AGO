import numpy as np
import cv2

image = np.ones((200, 300), np.float64)
cv2.namedWindow('Mouse Event')
cv2.imshow('Mouse Event', image)

'''
마우스 event 
'''


def onMouseEvent(event, x, y, flags, param):
    if event == cv2.EVENT_LBUTTONDOWN:
        print(event)
        print(flags)
        print(x, ":", y)


'''
등록 
'''
cv2.setMouseCallback('Mouse Event', onMouseEvent)

cv2.waitKey(0)
cv2.destroyAllWindows()
