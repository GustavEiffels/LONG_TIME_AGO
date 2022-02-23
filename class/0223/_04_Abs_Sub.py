import numpy as np
import cv2

abs1 = cv2.imread('./abs_test1.jpeg', cv2.IMREAD_GRAYSCALE)
abs2 = cv2.imread('./abs_test2.jpeg', cv2.IMREAD_GRAYSCALE)

''' 차분 연산 '''
dif_img1 = cv2.subtract(abs1, abs2)
dif_img1_1 = cv2.subtract(abs2, abs1)

''' 음수 보전 '''
dif_img2 = cv2.subtract(np.int16(abs1), np.int16(abs2))

''' 음수 보전한 데이터를 절대 값으로 치환 '''
abs_dif1 = np.absolute(dif_img2).astype('uint8')

''' 차분 절대값 함수 이용 '''
abs_dif2 = cv2.absdiff(abs1, abs2)

''' 원본 파일 '''
cv2.imshow('abs1', abs1)
cv2.imshow('abs2', abs2)

''' 수정 파일 '''
cv2.imshow('dif_img1', dif_img1)
cv2.imshow('dif_img1_1', dif_img1_1)
cv2.imshow('dif_img2', dif_img2)
cv2.imshow('abs_dif1', abs_dif1)
cv2.imshow('abs_dif2', abs_dif2)

cv2.waitKey()
cv2.destroyAllWindows()