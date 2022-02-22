import cv2
from matplotlib import pyplot as plt

'''
image 읽어오기 
'''
image = cv2.imread('./download.png', cv2.IMREAD_COLOR)

cv2.imshow('plan', image)

# keyboard 누를 때 까지 대기
key = cv2.waitKey(0)

cv2.destroyAllWindows()