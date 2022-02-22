import cv2
import numpy as np

# 출력할 데이터 생성
img = np.zeros((512, 512, 3), np.uint8)

cv2.putText(img,
            'Open CV Programming',
            (50, 100),
            cv2.FONT_HERSHEY_TRIPLEX,
            1,
            (0, 0, 255), 2)

# 윈도우에 출력
cv2.imshow('img', img)

# keyboard 누를 때 까지 대기
key = cv2.waitKey(0)

# 모든 윈도우 닫기
cv2.destroyAllWindows()