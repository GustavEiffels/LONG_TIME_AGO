import numpy as np
import cv2
import time

''' 일반적인 접근 방식 '''
image = cv2.imread('./lena.jpg', cv2.IMREAD_GRAYSCALE)


def pixel_access1(image):
    image1 = np.zeros(image.shape[:2], image.dtype)
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            pixel = image[i, j]
            # 이미지 반전
            image1[i, j] = 255 - pixel

    return image1


''' item 함수 이용 '''


def pixel_access2(image):
    image2 = np.zeros(image.shape[:2], image.dtype)
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            pixel = image.item(i, j)
            image2.itemset((i, j), 255 - pixel)
    return image2


''' 룩업 테이블 이용 '''


def pixel_access3(image):
    lut = [255 - i for i in range(256)]
    lut = np.array(lut, np.uint8)
    image3 = lut[image]

    return image3


''' OpenCV 함수 이용 '''


def pixel_access4(image):
    image4 = cv2.subtract(255, image)
    return image4


''' numpy 연산자 이용 '''


def pixel_access5(image):
    # numpy 는 바로 연산이 가능하다
    image5 = 255 - image
    return image5


''' 소요되는 시간 측정을 위해 time module 을 import'''

''' 수행 시간을 체크하는 함수 '''


def time_check(func, msg):
    start_time = time.perf_counter()
    ret_img = func(image)
    elapsed = (time.perf_counter() - start_time) * 1000
    print(msg, "수행시간 : %.2f ms" % elapsed)
    return ret_img


image1 = time_check(pixel_access1, '방법 1 : 직접 접근 방식')
image2 = time_check(pixel_access2, '방법 2 : Item 함수 이용')
image3 = time_check(pixel_access3, '방법 3 : Look Up Table')
image4 = time_check(pixel_access4, '방법 4 : OpenCV 함수 이용 방식')
image5 = time_check(pixel_access5, '방법 5 : numpy의 vector 연산')

''' 같은 값인지 확인 '''
cv2.imshow("image", image)
cv2.imshow("image1", image1)
cv2.imshow("image2", image2)
cv2.imshow("image3", image3)
cv2.imshow("image4", image4)
cv2.imshow("image5", image5)

cv2.waitKey()
cv2.destroyAllWindows()
