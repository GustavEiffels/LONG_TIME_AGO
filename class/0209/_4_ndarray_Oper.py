import numpy as np

x = 100
ar1 = np.array([1, 2, 3])
ar2 = np.array([4, 5, 6])

matrix1 = np.array(range(0, 6, 1)).reshape(2, 3)
matrix2 = np.array(range(0, 6, 1)).reshape(3, 2)

# 동일한 크기의 배열끼리 연산
print("동일한 크기의 배열끼리 연산")
print(ar1 + ar2)
print()

# 2개의 배열은 2차원인 것은 같지만 구조가 다르기 때문에 연산 불가
# print("2개의 배열은 2차원인 것은 같지만 구조가 다르기 때문에 연산 불가")
# print(matrix2 + matrix1)
# print()

# scala Data 와 배열의 연산
# 배열의 각 요소에 scala data를 연산을 한 후 결과를 배열로 리턴
print("배열의 각 요소에 scala data를 연산을 한 후 결과를 배열로 리턴")
print(x+ar1)
print()

# 차원이 다른 배열끼리의 연산
# 1 차원 배열의 요소 개수 와 2 차원 배열의 행을 구성하는 열의 개수가 같아서 수행
print("1 차원 배열의 요소 개수 와 2 차원 배열의 행을 구성하는 열의 개수가 같아서 수행")
print(ar1 + matrix1)
print()

# 차원이 다른 배열끼리의 연산 - 2
# 1 차원 배열의 요소 개수 와 2 차원 배열의 행을 구성하는 열의 개수가 달라서 Error
# print("1 차원 배열의 요소 개수 와 2 차원 배열의 행을 구성하는 열의 개수가 달라서 Error")
# print(ar1 + matrix2)
# print()


