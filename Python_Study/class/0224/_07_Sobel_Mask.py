import cv2
import _00_Base_for_Copy as bs

origin = bs.readImageGray('./rect.jpg')

''' image print '''
cv2.imshow('origin image', origin)

''' Apply Sobel Mask '''
gx = cv2.Sobel(origin, cv2.CV_32F, 1, 0, ksize=3)
gy = cv2.Sobel(origin, cv2.CV_32F, 0, 1, ksize=3)

''' 극 좌표로 변환 '''
mag, angle = cv2.cartToPolar(gx, gy, angleInDegrees=True)

''' image Binarization '''
ret, edge = cv2.threshold(mag, 100, 255, cv2.THRESH_BINARY)

''' image print '''
cv2.imshow('gx', gx)
cv2.imshow('gy', gy)
cv2.imshow('edge', edge)

''' key board set'''
bs.keyboardseet()