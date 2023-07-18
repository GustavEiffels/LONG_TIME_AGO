import cv2

img = cv2.imread('./pikachu.jpg')
# 데이터를 복제해서 새로운 이미지를 생성
dst = img.copy()

dst[100:150, 0:50] = 0

cv2.imshow('img', img)
cv2.imshow('dst', dst)

cv2.waitKey(0)
cv2.destroyAllWindows()