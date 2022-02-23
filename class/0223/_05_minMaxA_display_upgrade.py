import cv2
import numpy as np

image = cv2.imread('./minMax.jpeg', cv2.IMREAD_GRAYSCALE)

cv2.imshow("image", image)

''' 최소값과 최대값 찾아오기 '''
(min_val, max_val, _, _) = cv2.minMaxLoc(image)
print(min_val, max_val)


''' 최대 최소 를 가지고 255를 나눔 = 비율 구하기 '''
ratio = 255/(max_val - min_val)

''' image 를 보정해서 새로운 image 생성 '''
dst = np.round((image-min_val)*ratio).astype('uint8')
(dst_min_val, dst_max_val, _, _) = cv2.minMaxLoc(dst)
print(f'After {dst_min_val}, {dst_max_val}')
cv2.imshow('dst', dst)

cv2.waitKey()
cv2.destroyAllWindows()