import matplotlib.pyplot as plt
import cv2
import numpy as np

src = cv2.imread('./lena.jpg')

''' 각 채널의 색상 '''
histColor = ('b', 'g', 'r')

for i in range(3):
    hist = cv2.calcHist(images=[src], channels=[i], mask=None,
                        histSize=[256], ranges=[0, 256])
    plt.plot(hist, color=histColor[i])

plt.show()