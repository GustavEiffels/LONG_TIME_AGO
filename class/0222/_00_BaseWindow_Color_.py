import numpy as np
import cv2

'''
출력할 데이터 생성 
'''
img = np.zeros((512, 512, 3), np.uint8)+255

'''
window 출력
'''
cv2.imshow('img', img)

'''
keyboard 누를 때 까지 대기 
'''
key = cv2.waitKey(0)

'''
모든 windows 닫기
'''
cv2.destroyAllWindows()