import cv2
import numpy as np
import time
import matplotlib.pyplot as plt

'''
블러링 : 이미지를 점진적으로 변하게해서 부드러운 느낌을 나도록 하는 것 
'''

''' image load '''
image = cv2.imread('./filter.jpg', cv2.IMREAD_GRAYSCALE)

''' image print '''
cv2.imshow('image', image)


''' 블러링을 적용할 mask 행렬 '''
''' 일반적으로 홀수의 정방향 행렬로 생성한다. ----> 그 이유는 ?? '''

# 1차원 행렬로 생성
data = [1/9, 1/9, 1/9, 1/9, 1/9,
        1/9, 1/9, 1/9, 1/9, 1/9,
        1/9, 1/9, 1/9, 1/9, 1/9,
        1/9, 1/9, 1/9, 1/9, 1/9,
        1/9, 1/9, 1/9, 1/9, 1/9]
print("before reshape")
print(data)
print()

# reshape 를 이용해서 차원 변경 ---> 5 * 5 행렬로 생성
mask = np.array(data, np.float32).reshape(5, 5)

# 확인
print('mask --> 5 * 5  Matrix')
print(mask)

''' 회선 수행 -- 화소에 직접 접근 '''
''' 1. 행과 열의 수 구하기 '''
rows, cols = image.shape[:2]

''' 원본의 행 과 열의 크기로 회선의 결과르 저장할 행렬을 생성 '''
dst = np.zeros((rows, cols), np.float32)

''' mask 의 중심 좌표 찾아 오기 '''
x_center, y_center = mask.shape[1]//2, mask.shape[0]//2

''' 회선을 수행 맨 왼쪽과 오른쪽은 회선을 적용 시킬수 없음으로, 0 부터 시작하면 안된다 '''
for i in range(y_center, rows - y_center):
    for j in range(x_center, cols - x_center):
        sum = 0.0
        for u in range(mask.shape[0]):
            for v in range(mask.shape[1]):
                y, x = i + u - y_center, j + v - x_center
                sum += image[y, x] * mask[u, v]
            dst[i, j] = sum
''' 실수는 값을 출력을 잘하지 못한다 그럼으로 astype 추가'''
cv2.imshow("convolution complete", dst.astype('uint8'))


cv2.waitKey()
cv2.destroyAllWindows()
