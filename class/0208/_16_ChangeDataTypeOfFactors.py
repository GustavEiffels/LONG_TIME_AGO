import numpy as np
print()
# 일반적으로 생성하면 int 64, int 32 이다
ar = np.array([1, 2, 3])
print(ar.dtype)
# -----------> int64
print("-------------------------")

# U로 바뀜
ar = np.array([1, 2, '3'])
print(ar.dtype)
# -----------> <U21
print("-------------------------")

ar = np.array([1, 2, '3'], dtype=np.int32)
print(ar.dtype)
# -----------> int32
print("-------------------------")



# Type 을 float 32로 변경
ar = ar.astype(np.float32)
print(ar.dtype)
# -----------> int32
print("-------------------------")
