import numpy as np

ar = np.array([[1, 2], [3, 4]])
print(f' np.linalg.det(ar) = {np.linalg.det(ar)}')
print()
'''
result
np.linalg.det(ar) = -2.0000000000000004
'''

mat = np.array([[1, 1, 1], [2, 1, 6], [2, 3, 4]])
print(f' np.linalg.det(mat) =  {np.linalg.det(mat)}')
'''
result 
# ( 1 * (4 - 18 ) -1 * ( 8 -12 ) + 1 * (6 -2 ) )
np.linalg.det(mat) =  -6.0
'''