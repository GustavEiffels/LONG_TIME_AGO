import numpy as np

ar = np.arange(10)
print(ar)
# -------> [0 1 2 3 4 5 6 7 8 9]
print("--------------horizon-----------------")
print()

# 마지막 데이터를 포함
ar = np.linspace(0, 1, 6)
print(ar)
print("--------------horizon-----------------")
print()
# -------> [0.  0.2 0.4 0.6 0.8 1. ]


# 마지막 데이터를 미포함
ar = np.linspace(0, 1, 6, endpoint=False)
print(ar)
print("--------------horizon-----------------")
print()
# -------> [0.         0.16666667 0.33333333 0.5        0.66666667 0.83333333]
