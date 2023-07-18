# 실제 생성은 학습할 때만

import numpy as np

# 일차원 배열 생성
ar = np.array([1, 2, 3])

# ar 의 자료형을 확인
print(type(ar))

# ar의 요소의 자료형 확인
print(ar.dtype)

# 1차원을 출력
print(ar.ndim)

# 1차원인데 데이터는 3개
print(ar.shape)

# 2차원배열 생성하기 ----> 이미지가 대부분 이런 형식
ar = np.array([[1, 2, 3], [4, 5, 6]])
print(ar.ndim)
print(ar.shape)