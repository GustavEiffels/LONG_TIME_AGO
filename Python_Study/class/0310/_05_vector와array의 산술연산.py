import numpy as np

x = np.array([1, 2, 3, 4, 5])
y = np.array([10, 20, 30, 40, 50])
z = np.array([100, 200, 300, 400, 500, 600])

''' vector 연산 차원이 같을 때 '''
print(f' x + y = 차원이 같음으로 연산  가능 \t\n {x+y}')
print()
# result
# x + y = 차원이 같음으로 연산  가능
#  [11 22 33 44 55]

''' vector 연산 차원이 다를 때 '''
# print(f' x + z = 차원이 다르기 때문에 연산 불가 \t\n {x+z}')
# print()
# result
# print(f' x + z = 차원이 다르기 때문에 연산 불가 \t\n {x+z}')
# ValueError: operands could not be broadcast together with shapes (5,) (6,)

''' 행렬의 연산 '''
print('행렬 끼리의 연산 ')
print(np.array([[10, 20], [30, 40]])
      + np.array([[100, 200], [300, 400]]))
print()
# result
# 행렬 끼리의 연산
# [[110 220]
#  [330 440]]