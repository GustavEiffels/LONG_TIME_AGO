import numpy as np
print("=================================================")
print()
# 1로 채워진 10 개의 데이터를 가진 배열 생성
ar1 = np.ones(10)
print(ar1)
print()
print("=================================================")


print()
# 1로 채워진  5*5 배열을 생성
ar2 = np.ones((5, 5))
print(ar2)
print()
print("=================================================")


# 2 * 2 정방향 행렬을 만들고 대각선 방향으로 1을 채운 행렬 생성
print("--------------------------------- 2*2 대각 행렬 생성 ")
ar3 = np.eye(2)
print(ar3)
print()
print("=================================================")


# 4 * 4 정방향 행렬을 만들고 대각선 방향으로 1을 채운 행렬 생성
print("--------------------------------- 4*4 대각 행렬 생성 ")
ar3_1 = np.eye(4)
print(ar3_1)
print()
print("=================================================")

# 대각 행렬의 1 의 위치를 수정하기 -- 1
print("--------------------------------- 4*4 대각 행렬 생성 ")
ar3_1 = np.eye(4, k=1)
print(ar3_1)
print()
print("=================================================")

# 대각 행렬의 1 의 위치를 수정하기 -- 2
print("--------------------------------- 4*4 대각 행렬 생성 ")
ar3_1 = np.eye(4, k=-1)
print(ar3_1)
print()
print("=================================================")

# 대각선에서 데이터를 추출해서 새로운 1차 배열 생성
print("대각선에서 데이터를 추출해서 새로운 1차 배열 생성")
br = np.diag(ar3_1, k=-1)
print(br)
print()
print("=================================================")
