import numpy as np

# 대각 행렬 생성
ar = np.diag([1, 2, 3])
print(f' 대각행렬 : \t\n {ar}')
print()
# result
# 대각행렬 :
#  [[1 0 0]
#  [0 2 0]
#  [0 0 3]]

# 항등 행렬 생성 , eye , identity 이용
ar = np.eye(2)
print(f' 항등 행렬 : eye 사용 \t\n {ar}')
print()
# result
# 항등 행렬 : eye 사용
#  [[1. 0.]
#  [0. 1.]]

ar = np.identity(2)
print(f' 항등 행렬 : identity 사용 \t\n {ar}')
print()
# result
# 항등 행렬 : identity 사용
#  [[1. 0.]
#  [0. 1.]]


# diag 함수에 2 차원 행렬을 대입하면
# 대각 요소만 이루어진 1차원 vector 리턴
print(f' diag 함수 이용 '
      f'--> 1차원 vector return : \t\n {np.diag(ar)}')
print()
# result
# diag 함수 이용 --> 1차원 vector return :
#  [1. 1.]


''' 대칭 행렬 '''
ar = np.eye(2)
print(f' ar 은 대칭행렬 --> ar : \t\n {ar}')
print(f' ar 은 대칭행렬 --> ar.T : \t\n {ar.T}')
print()
# result
# ar 은 대칭행렬 --> ar :
#  [[1. 0.]
#  [0. 1.]]
#  ar 은 대칭행렬 --> ar.T :
#  [[1. 0.]
#  [0. 1.]]


''' 대칭행렬 -- 대각행렬은 아님 ! '''
ar = np.array([[1, 2], [2, 1]])
print(f' ar --> 대각 행렬은 아니다 : \t\n {ar}')
print(f' ar --> 대칭 행렬이긴 하다 : \t\n {ar.T}')
print()
# ar --> 대각 행렬은 아니다 :
#  [[1 2]
#  [2 1]]
#  ar --> 대칭 행렬이긴 하다 :
#  [[1 2]
#  [2 1]]