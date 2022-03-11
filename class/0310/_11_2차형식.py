import numpy as np

# 2차 형식
x = np.array([1, 3, 5])

A = np.arange(1, 10).reshape(3, 3)
print(f'A : \t\n {A}')
print()

''' result 
A : 	
 [[1 2 3]
 [4 5 6]
 [7 8 9]]
'''

print(f' x.T @ A @ x : {x.T @ A @ x}')
'''result 
x.T @ A @ x : 549
'''



