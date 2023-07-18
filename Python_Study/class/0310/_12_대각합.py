import numpy as np
import matplotlib.pyplot as plt

vector = np.arange(16).reshape(4, -1)
print(f' vector = \t\n {vector}')
print()
# result
# vector =
#  [[ 0  1  2  3]
#  [ 4  5  6  7]
#  [ 8  9 10 11]
#  [12 13 14 15]]


print(f' np.trace(vector) :  {np.trace(vector)}')
print()
#result
# np.trace(vector) :  30
