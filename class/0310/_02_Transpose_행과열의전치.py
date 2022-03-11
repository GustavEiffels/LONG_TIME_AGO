import numpy as np

ar = np.array([1, 2, 3])
print(f' 1차원 배열 전치 : {ar.T}')
print()
# result : 1차원 배열 전치 : [1 2 3]

# 2차원 배열
ar = np.array([[1, 2, 3], [4, 5, 6]])
print(f' 2차원 배열 ar :\t\n {ar}')
print()
# result
# 2차원 배열 ar :
#  [[1 2 3]
#  [4 5 6]]

print(f' 2차원 배열의 전치  ar. T : \t\n {ar.T}')
print()
# result
# 2차원 배열의 전치  ar. T :
#  [[1 4]
#  [2 5]
#  [3 6]]

print(f' 2차원 배열의 전치  ar.transpose() : \t\n {ar.transpose()}')
print()
# result
# 2 차원 배열의 전치 ar.transpose():
# [[1 4]
# [2 5]
# [3 6]]

# 3차원 배열
print("3 dimension Array -----")
ar = np.arange(12).reshape(2, 3, -1)
print(f' 3차원 배열 ar  : \t\n {ar}')
print()
# result
# 3 dimension Array -----
#  3차원 배열 ar  :
#  [[[ 0  1]
#   [ 2  3]
#   [ 4  5]]
#
#  [[ 6  7]
#   [ 8  9]
#   [10 11]]]

# 3차원 배열 변형
print('---------- 3 dimension transform : ar.T ----------')
print(f' 3차원 배열 변경 --> 첫번째 행과 마지막 면이 바뀜  : \t\n {ar.T}')
print()
# result
# ---------- 3 dimension transform ar.T ----------
#  3차원 배열 변경 --> 첫번째 행과 마지막 면이 바뀜  :
#  [[[ 0  6]
#   [ 2  8]
#   [ 4 10]]
#
#  [[ 1  7]
#   [ 3  9]
#   [ 5 11]]]
print('---------- 3 dimension transform : Transpose ----------')
print(f' 3차원 배열 변경 --> 첫번째 행과 마지막 면이 바뀜  : \t\n {ar.transpose()}')
print()
# result
# ---------- 3 dimension transform ar.T ----------
#  3차원 배열 변경 --> 첫번째 행과 마지막 면이 바뀜  :
#  [[[ 0  6]
#   [ 2  8]
#   [ 4 10]]
#
#  [[ 1  7]
#   [ 3  9]
#   [ 5 11]]]

print('---------- 3 dimension transform : Transpose 설정  ----------')
print(f' 3차원 배열 변경 --> parmaeter를 사용하여 원하는 구조로 '
      f'변경이 가능하다 '
      f'  : \t\n {ar.transpose(2, 1, 0)}'
      f' \n --------------------'
      f'  \t\n {ar.transpose(1, 2, 0)}')
print()
# result
# ---------- 3 dimension transform : Transpose 설정  ----------
#  3차원 배열 변경 --> parmaeter를 사용하여 원하는 구조로 변경이 가능하다   :
#  [[[ 0  6]
#   [ 2  8]
#   [ 4 10]]
#
#  [[ 1  7]
#   [ 3  9]
#   [ 5 11]]]
#  --------------------
#  [[[ 0  6]
#   [ 1  7]]
#
#  [[ 2  8]
#   [ 3  9]]
#
#  [[ 4 10]
#   [ 5 11]]]