import numpy as np

ar = np.arange(4)
br = np.arange(4, 17, 4)
print(f'ar : {ar}')
print(f'br : {br}')
print()

m1 = ar.reshape(2, 2)
m2 = br.reshape(2, 2)

print(f"Dstack : {np.dstack((m1, m2))}")

