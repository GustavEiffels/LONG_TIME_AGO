import cv2
import numpy as np

import _00_Base_for_Copy as bs

''' 침식 연산 -- 배경  부분의 잡음이 제거 '''

image = bs.readImageGray('./morph.jpg')


'''Binary - 이진화 수행 // 임계 값을 기준으로 2개의 값으로 분할'''
th_img = cv2.threshold(image, 128, 255, cv2.THRESH_BINARY)[1]


''' Create mask '''
data = [0, 1, 0,
        1, 1, 1,
        0, 1, 0]
mask = np.array(data, np.uint8).reshape(3, 3)

''' 침식 -- 배경의 잡음이 많이 제거됨  '''
erode_img = cv2.erode(th_img, mask)

''' 팽창 -- 객체 내부의 잡음이 많이 제거됨 '''
dilate_img = cv2.dilate(th_img, mask)

''' 열림 연산 '''
open_img = cv2.morphologyEx(th_img, cv2.MORPH_OPEN, mask)

''' 닫힘 연산 '''
close_img = cv2.morphologyEx(th_img, cv2.MORPH_CLOSE, mask)

close_img1 = cv2.morphologyEx(th_img, cv2.MORPH_CLOSE, mask, iterations=3)

''' 열림 연산을 수행한 데이터를 가지고 닫힘 연산을 수행 '''
close_img2 = cv2.morphologyEx(open_img, cv2.MORPH_CLOSE, mask)


''' image print '''
cv2.imshow('origin', image)

''' 이진화한 이미지 '''
cv2.imshow('th_img', th_img)

''' 이진화한 이미지에 침식 연산 수행 '''
cv2.imshow('erode_img', erode_img)

''' 이진화한 이미지에 팽창 연산을 수행 '''
cv2.imshow('dilate_img', dilate_img)

''' 이진화한 이미지에 열림 연산을 수행 '''
cv2.imshow('Open_img', open_img)

''' 이진화한 이미지에 닫힘 연산을 수행 '''
cv2.imshow('Close_img', close_img)
cv2.imshow('Close_img_iteration', close_img1)

''' 열림 연산을 수행한 연산에 닫힘 연산을 수행'''
# 열림 연산과 닫힘 연산은 반대로 수행하기 때문에 사용하면 깔끔한 이미지를 얻을 수 있다.
cv2.imshow('Close_image applied in Open_image', close_img2)


''' keyboard event process '''
bs.keyboardseet()