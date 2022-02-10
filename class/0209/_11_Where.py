import numpy as np

# 1 차원 배열 생성
ar = np.arange(4)
br = np.array([10 ,20, 30, 40])
print(ar)
print(br)
print()

# condition 생성
condition = np.array([True, False, True, False])

# where 사용하기
# True 일때 ar 값이 출력
# False 일때는 br 값이 출력
print('where 사용하기 ===================== ')
print(np.where(condition, ar, br))
