import cv2
import numpy as np

img = cv2.imread('./contrast.jpg', cv2.IMREAD_GRAYSCALE)





''' 더미 영상 '''
noimg = np.zeros(img.shape[:2], img.dtype)

''' 원본 이미지의 평균의 절반 '''
avg = cv2.mean(img)[0]/2.0

''' 명암 대비 감소'''
dst1 = cv2.addWeighted(img, 0.5, noimg, 0, avg)

''' 명암 대비 증가 
// overflow 발생할 수 있기 때문에 평균을 빼줌 
객체 탐지에서 중요한 부분 '''
dst2 = cv2.addWeighted(img, 2.0, noimg, 0, -avg)

''' 출력 '''
cv2.imshow('img', img)
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)

cv2.waitKey()
cv2.destroyAllWindows()