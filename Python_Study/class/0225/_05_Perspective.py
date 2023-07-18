import cv2
import numpy as np
import _00_Base_for_Copy as bs0

''' image load '''
src = bs0.readImageGray('./perspective.jpg')
print(src.shape)
print()

''' 원근을 적용할 4개의 점 좌표  '''
''' (350, 400) '''
pt1 = np.float32([(80, 40), (315, 133), (75, 300), (335, 300)])

''' 보정된 좌표 '''
pt2 = np.float32([(50, 60), (340, 60), (50, 320), (340, 320)])

''' 원근 변환 행렬 '''
perspect_mat = cv2.getPerspectiveTransform(pt1, pt2)
print(perspect_mat)

dst = cv2.warpPerspective(src, perspect_mat, src.shape[1::-1])


''' image show '''
cv2.imshow('src', src)
cv2.imshow('dst', dst)

''' keyboard cntroller '''
bs0.keyboardseet()