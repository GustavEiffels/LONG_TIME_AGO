import numpy as np
import cv2

img = cv2.imread('./pikachu.jpg', cv2.IMREAD_GRAYSCALE)
print('img.shape', img.shape)


# image 를 1 차원 배열로 저장
img_1 = img.flatten()
print("img_1.shape", img_1.shape)


# 원본이미지로 되돌리기
# 512 * 512 로 나눈 만큼의 차원으로 구조를 변경
# reshape = 배열의 구조를 변경
img_2 = img_1.reshape(-1, 274, 184)
print("img_2.shape", img_2.shape)

# 크기 변경
re_img = cv2.resize(img, (300, 500))


cv2.imshow('pikachu', img)
cv2.imshow('pikach_Re', re_img)
cv2.waitKey(0)
cv2.destroyAllWindows()