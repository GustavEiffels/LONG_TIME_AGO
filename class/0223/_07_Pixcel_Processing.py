import numpy as np

''' 행렬의 모든 원소를 2개로 변경 '''
mat1 = np.arange(10).reshape(2, 5)
mat2 = np.arange(10).reshape(2, 5)

''' 2 차원으로 만들어 졌는지 확인 '''
print(f'mat1 : \n {mat1}')
print()
print(f'mat2 : \n {mat2}')
print()

''' 순서대로 접근 '''
for _ in range(mat1.shape[0]):
    for j in range(mat1.shape[1]):
        mat1[_, j] = mat1[_, j]*2

print(f'mat1 : \n {mat1}')
print()

for _ in range(mat2.shape[0]):
    for j in range(mat2.shape[1]):
        mat2.itemset((_, j), mat2.item(_, j)*2)
print(f'mat2 : \n {mat2}')
print()