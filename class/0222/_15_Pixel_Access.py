import cv2

img = cv2.imread('./pikachu.jpg')


# 특정 색깔에 접근 
img[100:170, 0: 50, 0] = 255
img[100:170, 50: 100, 1] = 255
img[100:170, 100:150, 2] = 255

cv2.imshow('img', img)

cv2.waitKey(0)
cv2.destroyAllWindows()