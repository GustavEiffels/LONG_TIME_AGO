import cv2
import _00_Common
import numpy as np
'''
window 출력할 데이터 생성   
'''
image = np.zeros((200, 400), np.uint8)
image[:] = 200

'''
window 생성 
'''
cv2.namedWindow('window', cv2.WINDOW_AUTOSIZE)

'''
window 출력 
'''
cv2.imshow('window', image)

'''
키보드 입력 대기
'''
cv2.waitKey(0)

'''
window 종료 
'''
cv2.destroyAllWindows()
