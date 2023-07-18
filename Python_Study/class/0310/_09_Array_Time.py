import numpy as np

x = np.array([[1, 2, 3], [4, 5, 6]])
y = np.array([[1, 2], [3, 4], [5, 6]])

result = x@y
print(f' result (x@y) = \t\n {result}')
''' result 

result (x@y) = 	
 [[22 28]
 [49 64]]
 
'''

x = np.array([[1, 2, 3], [4, 5, 6]])
y = np.array([[1, 2], [3, 4], [5, 6]])

result1 = x@y
result2 = y@x
print(f' result (x@y) = \t\n {result1}')
print()
''' result 
result (x@y) = 	
 [[22 28]
 [49 64]]
'''


print(f' result (y@x) = \t\n {result2}')
print()
''' result 
result (y@x) = 	
 [[ 9 12 15]
 [19 26 33]
 [29 40 51]]
'''

# 덧셈에 대한 분배 법칙은 성립
z = np.array([[1, 2], [3, 4], [5, 6]])
print(f' x @ (y + z) : \t\n {x @ (y + z)}')
print('----------')
print(f' x @ y + x @ z : \t\n {x @ y + x @ z }')
print()

# 연속된 행렬의 곱은 곱하는 순서를 변경해도 된다.
print('------ 연속된 행렬의 곱은 곱하는 순서를 변경해도 된다. ------')
print(f' x @ y @  x = \t\n {x @ y @ x}')
print('----------')
print(f' x @ (y @ x) = \t\n {x @ (y @ x)}')
print()
'''result 
------ 연속된 행렬의 곱은 곱하는 순서를 변경해도 된다. ------
 x @ y @  x = 	
 [[134 184 234]
 [305 418 531]]
----------
 x @ (y @ x) = 	
 [[134 184 234]
 [305 418 531]]

'''

# 항등 행렬 과의 곱은 그대로
ar = np.array([[1, 2], [3, 4]])
i = np.eye(2)
print(f' ar = \t\n {ar}')
# result
# ar =
#  [[1 2]
#  [3 4]]

print('----------')
print(f' ar @ i = \t\n  {ar @ i}')
print()
# result
# ----------
#  ar @ i =
#   [[1. 2.]
#  [3. 4.]]

