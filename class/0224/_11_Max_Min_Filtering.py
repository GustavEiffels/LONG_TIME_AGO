import cv2
import _00_Base_for_Copy as bs

image = bs.readImageGray('./min_max.jpg')

''' image Print '''
cv2.imshow('Origin', image)


''' Max_min_Filtering'''
# 최소값 필터링
minfilter_img = bs.min_max_filter(image,  3, 0)

# 최대값 필터링
maxfilter_img = bs.min_max_filter(image, 3, 1)

''' show image '''
cv2.imshow('min', minfilter_img)
cv2.imshow('max', maxfilter_img)

''' Keyboard set '''
bs.keyboardseet()