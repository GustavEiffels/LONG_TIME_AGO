import cv2
import numpy as np

import _00_Base_for_Copy as bs
'''image load '''
image = bs.readImageGray('./dft_300.jpg')
cv2.imshow('Origin image', image)

''' Create kernel '''
''' 가운데 비중이 높으면 이미지는 선명해짐 '''
kernel = np.array([[0, -1, 0], [-1, 5, -1], [0, -1, 0]])
print(kernel)


image_kernel = cv2.filter2D(image, -1, kernel)

''' image show '''
cv2.imshow('image_kernel', image_kernel)

''' key board 를 누르면 종료하는 코드 '''
bs.keyboardseet()
