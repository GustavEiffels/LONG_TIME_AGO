import numpy as np

ar = np.arange(10)
print(ar)
# ---> [0 1 2 3 4 5 6 7 8 9]
print()
print("--------------------------")

print(" Reshape :ar 을 2 차원 배열로 변환 ===================== ")
br = ar.reshape((2, 5))
print(br)
'''
[[0 1 2 3 4]
 [5 6 7 8 9]]
'''
print()
print("ReShape 다시 복원----------------------- ")
print(br.reshape(-1))
print()

print("---------------============= 1차원 배열을 3차원으로 변환 ")
# 1 차원 배열을 생성
ar = np.arange(20)
# 1 차원 배열을 3차원으로 변환
print(ar.reshape(2, 5, 2))

print("---------------============= 2대신 -1  ")
# 마지막 차원의 개수가 -1 일때 알아서 분할하여 생성한다
print(ar.reshape(2, 5, -1))
