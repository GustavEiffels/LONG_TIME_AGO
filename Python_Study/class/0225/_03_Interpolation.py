import cv2
import _00_Base_for_Copy as bs0

# help(cv2.resize)

''' image load '''
src = bs0.readImageGray('./scaling.jpg')

''' 최근접 이웃 보간을 이용한 확대 '''
dst_nearest = cv2.resize(src, (600, 500), 0, 0, cv2.INTER_NEAREST)

''' 양선형 보간을 이용한 확대 '''
dst_bilinear = cv2.resize(src, (600, 500), 0, 0, cv2.INTER_LINEAR)

''' image show '''
cv2.imshow('src', src)
cv2.imshow('dst_nearest', dst_nearest)
cv2.imshow('dst_bilinear', dst_bilinear)

''' keyboard controller'''
bs0.keyboardseet()