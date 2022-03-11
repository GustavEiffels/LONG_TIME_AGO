import numpy as np

# 길이가 1인 벡터

a = np.array([1, 0])
print(f'np.linalg.norm(a) : {np.linalg.norm(a)}')
print()

# result
'''
np.linalg.norm(a) : 1.0
'''

b = np.array([0, 1])
print(f'np.linalg.norm(a) : {np.linalg.norm(a)}')
print()

# result
'''
np.linalg.norm(a) : 1.0
'''

# 소수가 나오게 되면 정밀도의 문제가 생긴다
c = np.array([1/np.sqrt(2), 1/np.sqrt(2)])
print(f'np.linalg.norm(c) : {np.linalg.norm(c)}')
print()

# result
'''
np.linalg.norm(c) : 0.9999999999999999
'''


# 벡터를 길이로 나누면 단위 벡터가 된다
a = np.array([1, 3])
print(f'np.linalg.norm(a) : {np.linalg.norm(a)}')

# result
'''
np.linalg.norm(a) : 3.1622776601683795
'''

b = np.array([1, 3]) / np.linalg.norm(a)
print(f'b : {b}')

# result
'''
b : [0.31622777 0.9486833 ]
'''

print(f' np.linalg.norm(b) : {np.linalg.norm(b)}')
# result
'''
np.linalg.norm(b) : 0.9999999999999999
'''