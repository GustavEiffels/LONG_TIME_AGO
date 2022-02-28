import cv2
import numpy as np
import _00_Base_for_Copy as bs0

''' image load '''
src = bs0.readImageGray('../images/lena.jpg')

''' Apply Canny '''

dst1 = cv2.Canny(src, 50, 200)
dst2 = cv2.Canny(src, 50, 100)

''' Origin Image Confirm '''
cv2.imshow('src', src)
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)

''' keyboard controller '''
bs0.keyboardseet()
