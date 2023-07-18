import numpy as np

ar = np.array([1, 2, 3, 4, 5])
print(f"ar : {ar}")
print()

# 범위 대신 list 를 이용해서 Indexing 하는 것을 Fancy Indexing 이라고 한다
print("범위 대신 list 를 이용해서 Indexing 하는 것을 Fancy Indexing 이라고 한다")
xr = ar[[0, 3, 4]]
print(f"xr = {xr}")
print()

# Fancy Indexing 은 복제를 실행함
print("Fancy Indexing 은 복제를 실행함")
xr[0] = 999999999
print(f"ar : {ar}")
print(f"xr = {xr}")