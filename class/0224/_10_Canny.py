import cv2
import _00_Base_for_Copy as bs

image = bs.readImageGray('./canny.jpg')

''' 실제 작업에서는 threshold 값을 여러가지로 변경해봐야 한다. '''
canny = cv2.Canny(image, 100, 150)
canny2 = cv2.Canny(image, 80, 170)

''' image print '''
cv2.imshow('origin', image)
cv2.imshow('canny', canny)
cv2.imshow('canny2', canny2)

''' keyboard controller '''
bs.keyboardseet()