import cv2
import numpy as np

img = cv2.imread('./Heart10.jpg', cv2.IMREAD_GRAYSCALE)

cv2.imshow('img', img)
print(img[50])

''' img 이진화 '''

''' 120 이 넘으면  255 , 그렇지 않으면 0 으로 이진화 '''
ret, dst = cv2.threshold(img, 120, 255, cv2.THRESH_BINARY)
print(ret)
print(dst[50])
cv2.imshow('dst', dst)

''' 임계값을 찾아서 이진화 '''
ret1, dst1 = cv2.threshold(
    img, 120, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU
)
print(ret1)
print(dst1)
cv2.imshow('dst1', dst1)

dst2 = cv2.adaptiveThreshold(img, 255, cv2.ADAPTIVE_THRESH_MEAN_C,
                             cv2.THRESH_BINARY, 51, 7)
cv2.imshow("dst2", dst2)
print(dst2[50])

cv2.waitKey()
cv2.destroyAllWindows()