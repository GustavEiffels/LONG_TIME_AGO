import cv2
import numpy as np

image = cv2.imread('equalize.jpg', cv2.IMREAD_GRAYSCALE)

# 히스토그램 생성 - 값이 가운데 영역에 치우쳐 있음
bsize, ranges = [64], [0, 256]
hist = cv2.calcHist([image], [0], None, bsize, ranges)
print(hist.flatten())

cv2.imshow("image", image)

# 평활화 - 값의 분포를 균등하게 함
dst = cv2.equalizeHist(image)
cv2.imshow("dst", dst)

hist = cv2.calcHist([dst], [0], None, bsize, ranges)
print(hist.flatten())

cv2.waitKey(0)
cv2.destroyAllWindows()
