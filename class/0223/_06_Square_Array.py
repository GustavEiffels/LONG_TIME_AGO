import numpy as np
import cv2

# 행렬 생성
rands = np.zeros((5, 5), np.uint16)

''' 랜덤한 좌표 추출 '''
''' 사각형의 시작점 좌표로 사용할 데이터 5개 생성 '''
starts = cv2.randn(rands[:, :2], 100, 50)

''' 사각형의 끝 점 좌표로 사용할 데이터 5개 생성 '''
ends = cv2.randn(rands[:, 2:-1], 300, 50)

print(f'starts : \n {starts}')
print()
print(f'ends : \n {ends}')
print()

''' 사각형의 모임을 매개변수로 받아서 내용을 출력하는 함수 '''


def print_rects(rects):
    print("사각형 원소 \t 사각형 정보 \t\t\t\t\t 크기")
    # 인덱스를 I 에 넘기고 원래정보를 tuple 에 넘긴다.
    for i, (x, y, w, h, a) in enumerate(rects):
        print("rects[%i] = [(%3d, %3d) from (%3d, %3d)] %5d" % (i, x, y, w, h, a))
    print()


''' 사각형 만들기 '''
# 만들기 위해서 차분을 함
# 시작 좌표와 끝 좌표를 빼기
sizes = cv2.absdiff(starts, ends)
# 차이를 곱해서 너비 생성
areas = sizes[:, 0] * sizes[:, 1]
rects = rands.copy()
rects[:, 2:-1] = sizes
rects[:, -1] = areas
print_rects(rects)

# 사각형 정렬 -- areas 로 사각형을 정렬
idx = cv2.sortIdx(areas, cv2.SORT_EVERY_COLUMN).flatten()
sort_rects = rects[idx.astype('int')]
print(sort_rects)