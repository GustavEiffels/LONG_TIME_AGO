import cv2

img = cv2.imread('./pikachu.jpg')
img1 = cv2.imread('./pikachu.jpg')

img1[100, 150] = [255, 122, 123]


# 특정 범위의 데이터 변경
img1[100:150, 150:174] = [122, 234, 94]

cv2.imshow('img', img)
cv2.imshow('img1', img1)

cv2.waitKey(0)
cv2.destroyAllWindows()