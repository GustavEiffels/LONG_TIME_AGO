import numpy as np
import cv2
import _00_BaseWindow_Color_ as bs

'''
트랙바 이벤트 처리 함수 
'''


def onChange(value):
    global img
    # 현재 값과 트랙바에서 선택한 값의 차이를 img 에 적용
    add_value = value - int(img[0][0])
    img = img + add_value
    cv2.imshow('img', img)


'''
출력할 데이터 생성 
'''
img = np.zeros((512, 512), np.uint8)

'''
window 출력
'''
cv2.imshow('img', img)


cv2.createTrackbar("trackbar", "img", img[0][0], 255, onChange)

'''
키보드 누를 때 까지 대기 
'''
key = cv2.waitKey(0)

'''
모든 윈도우 닫기
'''
cv2.destroyAllWindows()
