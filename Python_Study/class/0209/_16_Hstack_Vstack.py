# hstack , vstack - concatenate 에서
# axis 를 1을 설정하느냐 생략하느냐의 차이

import numpy as np

ar = np.arange(4)
br = np.arange(4, 17, 4)
print(f'ar : {ar}')
print(f'br : {br}')
print()

m1 = ar.reshape(2, 2)
m2 = br.reshape(2, 2)

# Vertical Stack
print("Vertical Stack---")
print(np.vstack((m1, m2)))
print()
print("Horizon --------------------------------------")
print("Horizon Stack ----")
print(np.hstack((m1, m2)))
print()
