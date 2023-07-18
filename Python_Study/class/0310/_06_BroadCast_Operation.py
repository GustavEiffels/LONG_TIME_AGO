import numpy as np

# broad cast 연산
# 배열과 스칼라 데이터의 연산 - broad cast 연산
'''
차원의 개수가 작은 데이터를 
차원의 개수가 같은 데이터로 변환해서 
동일한 위치의 데이터끼리 연산을 수행하는 것을 말한다 
'''
ar = np.array([100, 200, 300])
print(f' broad cast Operation : \t\n {ar}')
print()
# result
# broad cast Operation :
#  [100 200 300]

# broad casting 연산
mat = np.array([[10, 20, 30], [40, 50, 60], [70, 80, 90]])
print(f' ar+mat --> \t\n {ar+mat}')
print()
# result
# ar+mat --> [[110 220 330]
#  [140 250 360]
#  [170 280 390]]

# 행이 2개이기 때문애 , 벡터를 복사해서 동일한 모양을 만들 수 있다.
mat = np.array([[10, 20, 30], [40, 50, 60]])
print(f' ar + mat ---> \t\n {ar+mat}')
print()
# result
# ar + mat --->
#  [[110 220 330]
#  [140 250 360]]

# 행이 3개 열이 2개--> vector를 복사해서 동일한 모양을 만들 수 없다 .
mat = np.array([[10, 20], [30, 40], [50, 60]])
print(f' ar + mat ---> \t\n {ar+mat}')
print()
# result
# Traceback (most recent call last):
#   File "/Users/mac/PycharmProjects/pythonProject/class/0310/_06_BroadCast_Operation.py", line 33, in <module>
#     print(f' ar + mat ---> \t\n {ar+mat}')
# ValueError: operands could not be broadcast together with shapes (3,) (3,2)