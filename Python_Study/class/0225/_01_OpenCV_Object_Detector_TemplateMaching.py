import cv2
import numpy as np
import _00_Base_for_Copy as bs0


''' Template Matching '''
'''
원본 이미지에서 다른 이미지와 동일한 이미지를 찾는 것 
'''

src = bs0.readImageGray('./alphabet.bmp')
tmp_A = bs0.readImageGray('./A.bmp')
tmp_S = bs0.readImageGray('./S.bmp')
tmp_B = bs0.readImageGray('./B.bmp')

'''  출력 영상 색 변환을 위해서 '''
dst = cv2.cvtColor(src, cv2.COLOR_GRAY2BGR)

''' Search '''
result_A = cv2.matchTemplate(src, tmp_A, cv2.TM_SQDIFF_NORMED)
print(f'result_A : \n {result_A}')

''' 찾은 결과의 최소값과 위치를 찾음 '''
min_Value, _, minLocation, _ = cv2.minMaxLoc(result_A)
print(f'min_Value : \n {min_Value} \n minLocation : \n {minLocation}')


''' 찾은 영역에 사각형 그리기 '''

''' Template  높이와 너비를 계산 '''
w, h = tmp_A.shape[:2]
cv2.rectangle(dst, minLocation,
              (minLocation[0] + h, minLocation[1] + w),
              (255, 0, 0), 3)


''' show origin Image '''
cv2.imshow("Base", src)
cv2.imshow("tmp_A", tmp_A)
cv2.imshow("tmp_B", tmp_B)
cv2.imshow("tmp_S", tmp_S)

''' result show '''
bs0.imshowFunction(dst)


''' key setting '''
bs0.keyboardseet()