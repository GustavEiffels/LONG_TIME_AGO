import cv2
import numpy as np

# 출력할 데이터 생성
img = np.zeros((512, 512, 3), np.uint8)

# false 와 True 차이
pts1 = np.array([[100, 100], [200, 300], [300, 1000], [400, 800]])
pts2 = np.array([[100, 100], [200, 300], [300, 1000], [400, 800]])

cv2.polylines(img, [pts1], isClosed=True, color=(0, 255, 0))
cv2.polylines(img, [pts2], isClosed=False, color=(0, 0, 255))

# 윈도우에 출력
cv2.imshow('img', img)

# keyboard 누를 때 까지 대기
key = cv2.waitKey(0)

# 모든 윈도우 닫기
cv2.destroyAllWindows()