import numpy as np

# 2 차원 배열 생성
ar = np.array(range(10, 161, 10)).reshape(4,-1)
print(ar)
print()
# 기본적으로 행방향으로 분할
print("행방향으로 2개 분할 ")
ar_split = np.split(ar, 2)
print(ar_split)
print()

# 열방향으로 분할
ar_split = np.split(ar, 2, axis=1)
print("열방향으로 2개 분할")
print(ar_split)
print()