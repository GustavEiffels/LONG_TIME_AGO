import cv2
import numpy as np
import _00_Lena_Image_Load as lena

src = lena.srcGray
op = np.zeros(shape=(512, 512), dtype=np.uint8) + 200
print(f'src : \n {src}')
print()


''' numpy 의 더하기 연산 '''
''' overflow 가 발생해서 연산의 결과를 예측하기 어려움 '''
dst1 = src + op
print(f'dst1 : \n {dst1}')
print()

''' OpenCv 의 더하기 연산 '''
''' 255 보다 크면 255'''
dst2 = cv2.add(src, op)
print(f'dst2 : \n {dst2}')
print()


''' image 띄우기 '''
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)

''' keyboard 입력 대기 '''
cv2.waitKey(0)

''' 모든 화면을 파괴 '''
cv2.destroyAllWindows()