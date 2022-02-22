import cv2
from matplotlib import pyplot as plt


# 이미지 읽어 오기
image = cv2.imread('./download.png', cv2.IMREAD_COLOR)

# 이미지를 파일로 저장
cv2.imwrite('./pikachu.jpg', image)

