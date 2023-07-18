import numpy as np

# 역행렬
mat = np.array([[1, 2], [3, 4]])
print(mat)
print()
# result
'''
[[1 2]
 [3 4]]
'''

print(f' 역행렬 : \t\n {np.linalg.inv(mat)}')
print()
# result :
'''
역행렬 : 	
 [[-2.   1. ]
 [ 1.5 -0.5]]
'''

# 역행렬 과의 곱 - 단위 행렬이 나와야 함
print(f' 역행렬 과의 곱 :\t\n {mat @ np.linalg.inv(mat)}')
print()
# result :
'''
역행렬 과의 곱 :	
 [[1.0000000e+00 0.0000000e+00]
 [8.8817842e-16 1.0000000e+00]]
'''

mat = np.array([[1, 2], [3, 4]])
print(f' 전치 행렬의 역행렬 : \t\n {np.linalg.inv(mat.T)}')
print()
# result
'''
전치 행렬의 역행렬 : 	
 [[-2.   1.5]
 [ 1.  -0.5]]
'''
print(f' 역행렬의 전치 행렬 : \t\n {np.linalg.inv(mat).T}')
print()
# result
'''
역행렬의 전치 행렬 : 	
 [[-2.   1.5]
 [ 1.  -0.5]]
'''

mat = np.array([[2, 0], [0, 2]])
print(mat)
print()
# result
'''
[[2 0]
 [0 2]]
'''
# 대칭 행렬의 역행렬로 대칭 행렬
print(np.linalg.inv(mat))
print()
# result
'''
[[0.5 0. ]
 [0.  0.5]]
'''


''' 역행렬을 이용한 연립 방정식 해결 '''
'''
x1 + x2 = 2
x2 + x3 = 3
x1 + x2 + x3 = 4
'''
# 방정식의 계수를 가지고 계수 행렬을 생성
ar = np.array([[1, 1, 0], [0, 1, 1], [1, 1, 1]])

# 계수 행렬의 역행렬을 구한다
ar_inv = np.linalg.inv(ar)
print(f' ar_inv : \t\n {ar_inv}')
print()
# result
'''
ar_inv : 	
 [[ 0. -1.  1.]
 [ 1.  1. -1.]
 [-1.  0.  1.]]
'''

# 결과 행렬을 생성
b = np.array([[2], [3], [4]])
print(f' b  = \t\n {b}')
print()
# result
'''
b  = 	
 [[2]
 [3]
 [4]]
'''

# 계수의 역행렬과 결과의 행렬의 곱
print(f' ar_inv @ b : \t\n {ar_inv @ b}')
print()
# result
'''
ar_inv @ b : 	
 [[1.]
 [1.]
 [2.]]
'''

''' 최소 자승 문제로 해결 '''
ar = np.array([[1, 1, 0], [0, 1, 1], [1, 1, 1]])
b = np.array([[2], [3], [4]])


x, resid, rank, s = np.linalg.lstsq(ar, b)
# 연립 방정식의 해
print(f' x = \t\n {x}')
print()
'''
연립 방정식의 해 
x = 	
 [[1.]
 [1.]
 [2.]]
'''
# 잔차
print(f'resid  = {resid}')
print()
'''
해가 있어서 잔차가 없다 
result 
resid  = []
'''

# rank
print(f'rank = {rank}')
print()
'''
rank = 3
'''
# s
print(f's = {s}')
print()
'''
s = [2.41421356 1.         0.41421356]
'''


''' 해가 없는 경우 '''
# 방정식의 계수를 가지고 계수 행렬을 생성
ar = np.array([[1, 1, 0], [0, 1, 1], [1, 1, 1], [1, 1, 2]])


# 결과 행렬을 생성
b = np.array([[2], [3], [4], [6.1]])
print(f' b  = \t\n {b}')
print()
# result
'''
[[2. ]
 [3. ]
 [4. ]
 [6.1]]
'''

x, resid, rank, s = np.linalg.lstsq(ar, b)
# 연립 방정식의 해
print(f' x = \t\n {x}')
print()
'''
연립 방정식의 해 
x = 	
 x = 	
 [[1.03333333]
 [0.95      ]
 [2.05      ]]
'''
# 잔차
print(f'resid  = {resid}')
print()
'''
result 
resid  = [0.00166667]
'''

# rank
print(f'rank = {rank}')
print()
'''
rank = 3
'''
# s
print(f's = {s}')
print()
'''
s = [3.35753997 1.15123198 0.6337115 ]
'''