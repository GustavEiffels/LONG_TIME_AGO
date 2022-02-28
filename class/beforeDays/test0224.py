import cv2
import numpy as np

img = cv2.imread('./affine.jpg')

print("Color Image")
print(img)
print()

imgBW = cv2.imread('./affine.jpg', cv2.IMREAD_GRAYSCALE)
print("GrayScale Image ")
print(imgBW)

cv2.imshow('Color Image', img)
cv2.imshow('GrayScale Image', imgBW)

cv2.waitKey()
cv2.destroyAllWindows()