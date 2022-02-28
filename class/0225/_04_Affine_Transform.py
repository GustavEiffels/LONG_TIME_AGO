import cv2
import numpy as np
import _00_Base_for_Copy as bs

''' image load '''
src = bs.readImageGray('./affine.jpg')
print(src.shape)
print()

''' checking original image '''
cv2.imshow('original Image', src)

''' array for making affine array'''
''' result of src.shape = (330, 320) '''
''' 범위 안에 들어가는 값을 설정 '''
pt1 = np.array([(30, 50), (12, 123), (300, 100)], np.float32)
pt2 = np.array([(120, 213), (198, 189), (129, 256)], np.float32)

aff_mat = cv2.getAffineTransform(pt1, pt2)

''' Data for making Affine array '''
center = (200, 300)
angle = 30
scale = 1

rot_mat = cv2.getRotationMatrix2D(center, angle, scale)

''' transform using affine array  '''
dst1 = cv2.warpAffine(src, aff_mat, src.shape[::-1], cv2.INTER_LINEAR)
dst2 = cv2.warpAffine(src, rot_mat, src.shape[::-1], cv2.INTER_LINEAR)

''' affine confirm '''
cv2.imshow('dst1', dst1)
cv2.imshow('dst2', dst2)

''' keyboard controller '''
bs.keyboardseet()