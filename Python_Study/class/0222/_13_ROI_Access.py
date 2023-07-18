import cv2

img = cv2.imread('./pikachu.jpg', cv2.IMREAD_GRAYSCALE)

'''
특정 픽셀 값 변경 
'''
img[100, 150] = 0

'''
특정 범위의 데이터 변경 
'''
img[100:150, 150:170] = 0

cv2.imshow('img', img)

cv2.waitKey(0)
cv2.destroyAllWindows()