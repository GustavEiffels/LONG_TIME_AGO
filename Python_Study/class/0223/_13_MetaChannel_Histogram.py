import numpy as np
import cv2
src = np.array([[0, 0, 0, 0], [1, 2, 3, 4], [12, 213,  23, 45], [3, 4, 1, 2]],
               dtype=np.uint8)

# 0 ~ 7 까지를 4 개의 구간으로 나누어 히스토그램을 구현
hist1 = cv2.calcHist(images=[src], channels=[0], mask=None,
                     histSize=[4], ranges=[0, 8])
print("hist1")
print(hist1)
print()


# 0 ~ 3  까지를 4개의 구간으로 나누어서 히스토그램을 구함
hist2 = cv2.calcHist(images=[src], channels=[0], mask=None,
                     histSize=[4], ranges=[0, 4])
print("hist2")
print(hist2)

