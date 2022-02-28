import cv2
import numpy as np
import time
import _00_Base_for_Copy as bs0

''' image load '''
src = bs0.readImageGray('./scaling.jpg')

''' 원본 크기 확인 '''
print(src.shape)

''' scaling '''
# loop
dst_loop = bs0.scaling_loop(src, (150, 125))

# matrix
dst_matrix =bs0.scaling_matrix(src, (150, 125))

# matrix expand
dst_matrix_expand = bs0.scaling_matrix(src, (600, 500))

# nearst_expand
dst_nearst_expand = bs0.scaling_nearest(src, (600, 500))

''' 원본 이미지 확인 '''
cv2.imshow('src', src)

''' 축소된 image 확인 '''
cv2.imshow('dst_loop', dst_loop)

cv2.imshow('dst_matrix', dst_matrix)

''' 확장 '''
cv2.imshow('dst_matix_expand', dst_matrix_expand)

cv2.imshow('dst_nearst_expand', dst_nearst_expand)


''' keyboard Controller '''
bs0.keyboardseet()