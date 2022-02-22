import cv2
import numpy as np

# 출력할 데이터 생성
img = np.zeros((512, 512, 3), np.uint8)+255

# numpy 배열의 구성
print(img.shape)
'''
tuple 형태 
(512, 512, 3)
'''

'''
전체 영역의 가운데를 기준으로 원 생성
// 하지 않으면 실수가 출력 됨으로 
'''
cv2.circle(img, (img.shape[0]//2, img.shape[1]//2), 200, color=(100, 100, 100))
cv2.circle(img, (img.shape[0]//2, img.shape[1]//2), 100, color=(200, 200, 200), thickness=-1)

# 윈도우에 출력
cv2.imshow('img', img)

# keyboard 누를 때 까지 대기
key = cv2.waitKey(0)

# 모든 윈도우 닫기
cv2.destroyAllWindows()