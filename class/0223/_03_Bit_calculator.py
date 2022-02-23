import numpy as np
import cv2
import _00_Lena_Image_Load as lena

src = lena.srcGray

image1 = np.zeros((300, 200), np.uint8)
image2 = image1.copy()

''' 높이 와 너비 저장 '''
h, w = image1.shape[:2]
print(f' h : {h} \n n : {w}')
print()

''' 중앙점의 좌표 python 은 / 를 쓰면 나머지를 모두 출력 '''
''' //을 사용 '''
cx, cy = w//2, h//2
print(f' cx : {cx} \n cy : {cy}')
print()

''' image1 에 원 그리기 '''
cv2.circle(image1, (cx, cy), 100, 255, -1)

''' image2 에 사각형 그리기 '''
cv2.rectangle(image2, (0, 0, cx, h), 255, -1)

''' or 적용 '''
image3 = cv2.bitwise_or(image1, image2)
image4 = cv2.bitwise_and(image1, image2)
image5 = cv2.bitwise_xor(image1, image2)

cv2.imshow('image1', image1)
cv2.imshow('image2', image2)

''' 합집합 '''
# 0 or 0 은 0 , 0 or 1 은  1  ---> set
cv2.imshow('or', image3)

''' 교집합 '''
# 0 and 0 은 0 , 1 and 0 = 0 ----> 지울 때 사용
cv2.imshow('and', image4)

''' xor // 교집합 영역은 검정색 , 교집합 영역이 아닌 것은 흰색 '''
# 같으면 1 다르면 0
# 0 xor 0 = 1 ,  1 xor 1 = 0, 1 xor 0 = 1
# 두개 비교할 때 사용하기 좋음
cv2.imshow('xor', image5)

cv2.waitKey()
cv2.destroyAllWindows()