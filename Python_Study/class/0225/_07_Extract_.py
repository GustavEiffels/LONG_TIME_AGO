import cv2
import _00_Base_for_Copy as bs0
import numpy as np

''' image load '''
src = cv2.imread('../images/plane_256x256.jpg',cv2.IMREAD_GRAYSCALE)

''' 중간 pixel 에서 위아래 표준 편차 1 정도를 적용 '''
median_intensitiy = np.median(src)

lower_threshold = int(max(0, (1.0 - 0.33) * median_intensitiy))
upper_threshold = int(min(255, (1.0 + 0.33) * median_intensitiy))

dst1 = cv2.Canny(src, 50, 200)
dst2 = cv2.Canny(src, lower_threshold, upper_threshold)
''' Origin Image Confirm'''
cv2.imshow('src', src)
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)

''' keyboard controller '''
bs0.keyboardseet()