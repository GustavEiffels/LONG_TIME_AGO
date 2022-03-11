from scipy import sparse
import numpy as np

# dense matrix 로 표현
ar = np.eye(3, k=1)
print(f' ar : \t\n {ar}')
print()
''' result 
 ar : 	
 [[0. 1. 0.]
 [0. 0. 1.]
 [0. 0. 0.]]
'''

''' 희소 행렬로 표현  0 이 아닌 경우만 표현 '''
sp = sparse.csr_matrix(ar)
print(f' sp : \t\n {sp}')
print()
'''
sp : 	
   (0, 1)	1.0
  (1, 2)	1.0
  ----> 영화 추천 시스템 같은 데이터가 많은 곳에서 메모리를 많이 절약할 수 있다 . 
'''

# 거리 계산은 각각의 값들을
# 알아야하기 때문에 밀집행렬로 다시 되돌려야한다
''' 희소 행렬을 밀집 행렬로 되돌리기 '''
origin = sp.toarray()
print(f' origin 원래대로 되돌림  : \t\n {origin}')
print()
'''
origin 원래대로 되돌림  : 	
 [[0. 1. 0.]
 [0. 0. 1.]
 [0. 0. 0.]]
'''

