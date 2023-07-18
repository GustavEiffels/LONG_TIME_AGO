# r_, c_ : indexer ---> 기능은 hstack , vstack 과 유사
import numpy as np

ar = np.arange(4)
br = np.arange(4, 17, 4)
print(f'ar : {ar}')
print(f'br : {br}')
print()

m1 = ar.reshape(2, 2)
m2 = br.reshape(2, 2)


print(f'hstack : {np.hstack((m1, m2))}')
print("============")
print(f'np.c_ : {np.c_[m1, m2]}')

