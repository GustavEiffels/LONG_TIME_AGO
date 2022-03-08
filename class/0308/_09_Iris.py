from sklearn.datasets import load_iris
import numpy as np
import pandas as pd

''' iris data Load '''
iris = load_iris()

# .data ---> data 만 확인이 가능
print(iris.data)
print()

''' DATA Type of linear algebra for Data Analysis '''
# 1. Scala 타입
print(f' Scala  :  {iris.data[0, 0]}')
'''result
Scala  :  5.1
'''
print()

# 2. Vector Data Type
# 기본적인 표기법
x1 = np.array([[5.1], [1.3], [1.4], [0.3]])
print(f' Vector Basic Type  : \t\n {x1}')
print()

# 1차원 배열도 벡터로 간주
x2 = np.array(([5.1, 3.5, 1.4, 0.3]))
print(f' Vector Dimension  : \t\n {x2}')
print()

# 다차원을 1차원으로 변형
print(f' Vector Dimension Transform flatten : \t\n {x1.flatten()}')
print()

print(f' Vector Dimension Transform reshape : \t\n {x1.reshape(-1)}')
print()

# 1차원을 2차원으로 변경
# 4 , 1 은 4 행 1 열로 변경하는 것
# 4, -1 은 4행으로 변경
print(f' Vector Dimension Transform : \t\n {x1.flatten()}')
print()

print(f' Vector Dimension Transform ---> poly Dimension : \t\n {x2.reshape((4, 1))}')
print()
print(f' Vector Dimension Transform ---> One Dimension : \t\n {x2.reshape((4, -1))}')
print()

# 행렬
matrix = np.array([[5.1, 3.5, 1.4, 0.2],
                   [4.8, 3.2, 7.8, 1.7]])
print(matrix)
