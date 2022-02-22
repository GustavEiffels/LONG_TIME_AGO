import cv2
import _pickachu as pi
'''
3차원 배열 
'''

img = pi.img
dst1 = cv2.rotate(img, cv2.ROTATE_90_COUNTERCLOCKWISE)
dst2 = cv2.rotate(img, cv2.ROTATE_180)
dst3 = cv2.rotate(img, cv2.ROTATE_90_CLOCKWISE)

cv2.imshow('img', img)
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)
cv2.imshow('dst3', dst3)
cv2.waitKey(0)
cv2.destroyAllWindows()
