import cv2
import numpy as np
import time
import matplotlib.pyplot as plt


def readImageGray(url):
    return cv2.imread(url, cv2.IMREAD_GRAYSCALE)


def keyboardseet():
    cv2.waitKey()
    cv2.destroyAllWindows()


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


''' Robers Mask 를 위한 함수 '''


def differential(image, data1, data2):
    mask1 = np.array(data1, np.float32).reshape(3, 3)
    mask2 = np.array(data2, np.float32).reshape(3, 3)

    dst1 = filter(image, mask1)
    dst2 = filter(image, mask2)
    dst = cv2.magnitude(dst1, dst2)  # 회선 결과인 두 행렬의 크기 계산
    dst1, dst2 = np.abs(dst1), np.abs(dst2)  # 회선 결과 행렬 양수 변경

    dst = np.clip(dst, 0, 255).astype("uint8")
    dst1 = np.clip(dst1, 0, 255).astype("uint8")
    dst2 = np.clip(dst2, 0, 255).astype("uint8")
    return dst, dst1, dst2


''' Max_min_Filtering '''


# ksize = mask의 크기
# mode = 0 --> 최소 필터링 / 1 ---> 최대 필터링

def min_max_filter(image, ksize, mode):
    # 행의 개수 와 열의 개수를 가져온다
    rows, cols = image.shape[:2]
    # 회선 결과를 저장할 배열
    dst = np.zeros((rows, cols), np.uint8)
    # 중앙
    center = ksize // 2

    # 회선을 수행
    for i in range(center, rows - center):
        for j in range(center, cols - center):
            # 마스크 영역 생성
            y1, y2 = i - center, i + center + 1
            x1, x2 = j - center, j + center + 1
            mask = image[y1:y2, x1:x2]
            # 최소 최대 가져오기
            dst[i, j] = cv2.minMaxLoc(mask)[mode]
    return dst


''' Open CV ImShow method '''


# 입력 받은 값을 cv2 의 이름으로 출력


def imshowFunction(src):
    name = f'src'
    return cv2.imshow(name, src)


''' 반복문을 사용해서 이미지의 크기를 변경하는 방식 - 순방향 사상 '''


def scaling_loop(img, size):
    start_time = time.perf_counter()
    dst = np.zeros(size[::-1], img.dtype)
    ''' 확대 축소 비율 계산 '''

    ratio_Y, ratio_X = np.divide(size[::-1], img.shape[:2])
    for y in range(img.shape[0]):
        for x in range(img.shape[1]):
            i, j = int(y * ratio_Y), int(x * ratio_X)
            dst[i, j] = img[y, x]

    ''' pref 는 nano 초 단위로 값을 들고오기 때문에 * 1000 '''
    elapsed = (time.perf_counter() - start_time) * 1000
    print("loop Running Time : ", "%0.2f ms" % elapsed)
    return dst


''' 행렬 단위 접근을 이용해서 image 를 변경 '''


def scaling_matrix(img, size):
    start_time = time.perf_counter()
    dst = np.zeros(size[::-1], img.dtype)
    ''' 확대 축소 비율 계산 '''

    ratio_Y, ratio_X = np.divide(size[::-1], img.shape[:2])

    y = np.arange(0, img.shape[0], 1)
    x = np.arange(0, img.shape[1], 1)

    ''' 격자 그리드 생성 '''
    y, x = np.meshgrid(y, x)
    i, j = np.int32(y * ratio_Y), np.int32(x * ratio_X)
    dst[i, j] = img[y, x]

    ''' pref 는 nano 초 단위로 값을 들고오기 때문에 * 1000 '''
    elapsed = (time.perf_counter() - start_time) * 1000
    print("matrix Running Time : ", "%0.2f ms" % elapsed)

    return dst


''' 최근점 이웃 보간 '''


def scaling_nearest(img, size):
    dst = np.zeros(size[::-1], img.dtype)

    ''' 확대 축소 비율 계산 '''
    ratio_Y, ratio_X = np.divide(size[::-1], img.shape[:2])

    i = np.arange(0, size[0], 1)
    j = np.arange(0, size[1], 1)

    ''' 격자 그리드 생성 '''
    i, j = np.meshgrid(i, j)

    y, x = np.int32(i / ratio_Y), np.int32(j / ratio_X)
    dst[i, j] = img[y, x]

    return dst