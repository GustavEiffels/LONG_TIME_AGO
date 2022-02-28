import cv2
import numpy
import numpy as np
import _00_Base_for_Copy as bs

''' image 가져오기 '''
image = bs.readImageGray('./filter_sharpen.jpg')

''' image 출력  '''
cv2.imshow('image', image)

''' 2개의 마스크 생성 '''
data1 = [0, -1, -0,
         -1, 5, -1,
         0, -1, 0]

data2 = [-1, -1, -1,
         -1, 9, -1,
         -1, -1, -1]

mask1 = np.array(data1, np.float32).reshape(3, 3)
mask2 = np.array(data2, np.float32).reshape(3, 3)

''' 확인 '''
print(f' mask1 : \n {mask1}')
print()
print(f' mask2 : \n {mask2}')

''' shapening 을 위한 회선 수행 함수 '''


def filter(image, mask):
    # 행과 열의 수 구하기
    rows, cols = image.shape[:2]

    ''' 원본의 행 과 열의 크기는 회선의 결과를 저장할 행렬을 생성 '''
    dst = np.zeros((rows, cols), np.float32)

    ''' mask 의 중심 좌표 찾아오기 '''
    x_center, y_center = mask.shape[1] // 2, mask.shape[0] // 2

    ''' 회선 수행 - 맨 왼쪽과 오른쪽은 회선을 돌릴 수가 없으므로 0 부터 시작하면 안된다 '''
    for i in range(y_center, rows - y_center):
        for j in range(x_center, cols - x_center):
            sum = 0.0
            for u in range(mask.shape[0]):
                for v in range(mask.shape[1]):
                    y = i + u - y_center
                    x = j + v - x_center
                    sum += image[y, x] * mask[u, v]
                dst[i, j] = sum
    return dst


''' sharpening 을 수행 '''

''' 가중치의 합이 1 이 넘기 때문에 255가 넘는 수가 나올 수 있다 '''
sharpen1 = filter(image, mask1)
sharpen2 = filter(image, mask2)

''' 그렇기 때문에 정규화 수행이 필요하다 '''
sharpen1 = cv2.convertScaleAbs(sharpen1)
sharpen2 = cv2.convertScaleAbs(sharpen2)

''' Sharpening Print '''
cv2.imshow('sharpen1', sharpen1)
cv2.imshow('sharpen2', sharpen2)

''' 키보드를 누르면 종료하는 코드 '''
cv2.waitKey()
cv2.destroyAllWindows()
