import cv2
import numpy as np

''' file load '''
img1 = cv2.imread('./abs_test1.jpeg', cv2.IMREAD_GRAYSCALE)
img2 = cv2.imread('./abs_test2.jpeg', cv2.IMREAD_GRAYSCALE)

''' 확인 '''
print(f'img1 : {img1}')
print()
print(f'img2 : {img2}')
print()


''' numpy 의 modulo 방식 '''
# 255 가 넘는 숫자는 0 부터 다시 시작해서 값이 설정
add_img1 = img1 + img2

''' openCV 의 더하기  255 이상은 255 '''
add_img2 = cv2.add(img1, img2)


''' 가중치를 적용하는 방식 '''
add_img3 = cv2.add(img1*0.5, img2*0.5)
# type 문제가 발생할 수 있기 때문에 설정
add_img3 = np.clip(add_img3, 0, 255).astype('uint8')

''' 가중치를 다르게 적용 '''
add_img4 = cv2.add(img1*0.4, img2*0.5)
add_img4 = np.clip(add_img4, 0, 255).astype('uint8')

add_img5 = cv2.add(img1*0.8, img2*0.9)
add_img5 = np.clip(add_img5, 0, 255).astype('uint8')

''' 원본 출력 '''
cv2.imshow('img1', img1)
cv2.imshow('img2', img2)

''' 더하기 출력 '''
cv2.imshow('add_img1', add_img1)
cv2.imshow('add_img2', add_img2)

''' 가중치 더하기 출력 '''
cv2.imshow('add_img3', add_img3)

''' 가중치를 다르게 적용 '''
cv2.imshow('add_img4', add_img4)
cv2.imshow('add_img5', add_img5)

cv2.waitKey()
cv2.destroyAllWindows()