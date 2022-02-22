# 마우스 왼쪽 버튼, 오른쪽 버튼을 누르면 원을 그려줌
# shift 키 + 왼 = 사각형
# 마우스 왼쪽 2번 = 화면 clear
# keyboard down = window Exit

import numpy as np
import cv2
import time


'''
마우스 클릭한 시간을 저장
'''
last_time = None

'''
마우스 이벤트를 처리할 함수
'''
def onMouse(event, x, y, flags, param):
    # 함수 외부의 데이터를 가져와서 사용
    global img

    # global 로 만들지 않으면 last_time 이 함수 안에서 새로 생성되어 null이 된다
    global last_time

    # 마우스 왼쪽 버튼 눌렀을 때 이벤트 처리
    if event == cv2.EVENT_LBUTTONDOWN:
        if last_time is not None and time.time() - last_time < 0.5:
            param[0] = np.zeros(param[0].shape, np.uint8)+255
            last_time = None
            print("Double Click ! in 0.5 second")
        else:
            # 마우스 왼쪽 버튼 눌렀을 때 사각형 그림
            last_time = time.time()
            cv2.rectangle(param[0], (x-5, y-5), (x+5, y+5), (255, 23, 34))
            print("Mouse Left Button")



    # 마우스 우측 버튼 눌렀을 때 이벤트 처리
    if event == cv2.EVENT_RBUTTONDOWN:
        # 마우스 우측 버튼 눌렀을 때 원을 그림
        cv2.circle(param[0], (x, y), 5, (255, 19, 19), 1)
        print("Mouse Right Button")

    # 마우스 왼쪽 더블 클릭 시 이벤트 처리
    # 왼쪽 버튼 눌렀을 때 이벤트 처리와 같이 작동할 것으로 예상
    # elif event == cv2.EVENT_FLAG_SHIFTKEY:
    #     param[0] = np.zeros(param[0].shape, np.uint8)+255
    #     print("SHIFT")

    # 그리는 것은 memory 상 그리기 때문에
    # 다음과  같이 설정
    # param[0] 의 내용을 화면에 출력
    cv2.imshow('img', param[0])



'''
출력할 data 생성 
'''
# zeros= 0 행렬 / image는 기본적으로 2차원
# (512, 512, 3) ---> 3 이 있어서 컬러로 출력
img = np.zeros((512, 512, 3), np.uint8)+255


'''
window 에 출력
'''
cv2.imshow('img', img)


'''
Mouse event 연결 
-- param 에 img fmf list 로 전달
'''
cv2.setMouseCallback('img', onMouse, [img])


'''
keyboard event 를 사용해서 지연시간 부여 
'''
# key board 입력을 받기 전까지 대기
key = cv2.waitKey(0)

# 무슨 key를 눌렀는지 확인 가능
# esc 는 27 이다
print(key)



'''
모든 윈도우 닫기 
'''
cv2.destroyAllWindows()