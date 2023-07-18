import cv2
import numpy as np
from matplotlib import pyplot as plt

# 이미지 읽어오기
image8 = cv2.imread('./download.png', cv2.IMREAD_COLOR)

# 16 비트는 0 ~ 65535 사이의 정수로
image16 = np.uint16(image8*(65535/255))

# 32 비트는 0 부터 1 사이의 실수로 표현
# 32 bit 는 줄여서 저장했기 때문에 그냥 볼 수 없다
image32 = np.float32(image8 * (1/255))

# image 저장
cv2.imwrite('./write_test16.tif', image16)
cv2.imwrite('./write_test32.tif', image32)

# 이미지 출력
cv2.imshow("image8", image8)
cv2.imshow("image16", image16)
cv2.imshow("image32", (image32*255).astype("uint8"))

cv2.waitKey(0)
cv2.destroyAllWindows()