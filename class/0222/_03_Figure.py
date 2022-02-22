import _00_BaseWindow_Color_ as bs0
import cv2
import numpy as np

# 출력할 데이터 생성
img = np.zeros((512, 512, 3), np.uint8)+255

'''
직선 
'''
cv2.line(img, (120, 50), (300, 300), (255, 0, 0), 3)

'''
사각형 
'''
cv2.rectangle(img, (100, 100), (400, 400), (0, 255, 0), 2)

# 윈도우에 출력
cv2.imshow('img', img)

# keyboard 누를 때 까지 대기
key = cv2.waitKey(0)

# 모든 윈도우 닫기
cv2.destroyAllWindows()