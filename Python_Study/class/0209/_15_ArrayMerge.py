import numpy as np

# 배열 2개 생성
ar = np.arange(4)
ar1 = np.arange(4, 8)

# concatenate 사용시 tuple 의 형태로 전달
arR = np.concatenate((ar, ar1))
print(arR)
print("result : [0 1 2 3 4 5 6 7] ")
print()

# 1 차원 배열은 axis 가 없다
# print("1 차원 배열은 axis 가 없다")
# errorR = np.concatenate((ar, ar1), axis=1)
# print(errorR)

# 2차원 배열을 생성
m1 = ar.reshape(2, 2)
m2 = ar1.reshape(2, 2)

# 2차원배열 합치기
print("2차원배열 합치기 -----")
mr = np.concatenate((m1, m2))
print(mr)
print("axis 적용시켜서 방향에 따라 합침 ")
mr = np.concatenate((m1, m2), axis=1)
print(mr)
print()