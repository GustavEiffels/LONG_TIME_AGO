import numpy as np

x = np.array([1, 2, 3])
y = np.array([4, 5, 6])
print(f' vector 의 연산 --> @ 사용 '
      f': 1 * 4 + 2 * 5 + 3 * 6 = \t\n {x@y}')
print()
# result
# vector 의 연산 : 1 * 4 + 2 * 5 + 3 * 6 =
#  32


print(f' vector 의 연산 --> dot 사용 '
      f': 1 * 4 + 2 * 5 + 3 * 6 = \t\n {np.dot(x.T, y)}')
print()
# result
# vector 의 연산 --> dot 사용 : 1 * 4 + 2 * 5 + 3 * 6 =
#  32


''' 유사도 '''
# 3개의 벡터가 존재
# 0 과 1로만 존재
# y 와 z 중 누가 x 와 가장 유사한가 ?
x = np.array([0, 1, 1])
y = np.array([0, 0, 1])
z = np.array([0, 0, 0])

print(f' x@y 의 내적 : {x@y}')
print()
# result : 1

print(f' x@z 의 내적 : {x@z}')
print()
# result : 0

'''
 x 와 y의 내적이 더 크다 . 
'''
# 1 이 아주 많은 행렬이라면 같은 위치에 1이 많이 있으면 내적이 커지게 된다
# 이 원리를 이용해서 문장이나 이미지의 유사도를 평가
# 이를 cosine 유사도를 이용하는 방식이라고 한다 .
'''
유사도를 평가하기 위해서는 차원이 같아야한다 . 
x = np.array([0, 1, 1])
y = np.array([0, 0, 1, 1]) 의 경우 
차원의 크기가 달라서 내적을 구하지 못함 
'''
