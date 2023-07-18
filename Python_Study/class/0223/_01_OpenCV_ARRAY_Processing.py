import numpy as np
import cv2

''' image 가져오기 '''
src = cv2.imread('./lena.jpg')

''' image 정보를 가져오기 '''
# 정보를 쪼개서 가져오기
rows, cols, channel = src.shape
print(f'확인을 위한 출력 : {rows}, {cols}, {channel}')

'''
변환 행렬 생성 // 순서 : 기준 좌표 , 각도 , 크기 비율 
'''
M1 = cv2.getRotationMatrix2D((rows/2, cols/2), 45, 0.5)
M2 = cv2.getRotationMatrix2D((rows/2, cols/2), -45, 0.5)
print(M1)
print(M2)
print()

''' 변환 행렬을 가지고 결과를 생성 '''
dst1 = cv2.warpAffine(src, M1, (rows, cols))
dst2 = cv2.warpAffine(src, M2, (rows, cols))


''' image 출력 '''
cv2.imshow('source', src)
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)

''' keyboard 입력 대기 '''
cv2.waitKey(0)

''' 모든 화면을 파괴 '''
cv2.destroyAllWindows()
