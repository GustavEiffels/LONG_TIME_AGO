import numpy as np
import cv2

'''
히스토그램을 계산해서 리턴해주는 함수 
매개변수는 이미지 , 계급의 수 , 값의 범위 
'''

def calc_histo(img, hsize, ranges=[0, 256]):
    # 히스토 그램 저장할 배열
    hist = np.zeros((hsize, 1), np.float32)

    # 계급의 간격 생성
    gap = ranges[1]/hsize

    # 이미지를 순회하면서 개수를 구함
    for row in img:
        for pix in row:
            idx = int(pix/gap)
            hist[idx] += 1
        return hist

img = cv2.imread('./Heart10.jpg', cv2.IMREAD_GRAYSCALE)

hsize, ranges = [16], [0, 256]

hist = calc_histo(img, hsize[0], ranges)

print(hist.flatten())