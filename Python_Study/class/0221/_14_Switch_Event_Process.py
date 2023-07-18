import numpy as np
import cv2

'''
keyboard event 처리 
---- switch // dictionary 사용 
'''

'''
ord 함수는 2byte 문자의 앞부분만 추출해서 
1 byte ascii code 로 만들어 주는 함수 
---- 한글은 사용하면 안됨
'''
switch_case = {ord('a'): 'a키 입력', ord('b'): 'b키 입력'}


# image 생성
image = np.ones((200, 300), np.float64)
cv2.namedWindow('Keyboard Event')
cv2.imshow('Keyboard Event', image)

# 무한 반복문
while True:
    # 키보드 입력 대기
    key = cv2.waitKey(0)
    print(key)
    if key == 27:
        break
    try:
        result = switch_case[key]
        print(result)
    except:
        result = -1

cv2.destroyAllWindows()