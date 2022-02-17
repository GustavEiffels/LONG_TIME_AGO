import numpy as np
import pandas as pd
import sklearn
from sklearn import preprocessing
'''
    numpy 의 digitize 를 이용한 구간 분할
'''

age = np.array([[13], [21], [22], [31], [45], [28]])
'''
30 을 기준으로 2개로 분할 
'''
result = np.digitize(age, bins=[30])
print(result)
print()


'''
다른구간으로 나누고 싶을 때
'''

# 3개 구간으로 나누기
result = np.digitize(age, bins=[20, 30])
print(result)
print()

'''
scikit-learn 을 이용해서 구간 분할 
'''

# 2개의 구간으로 분할
print("# 2개의 구간으로 분할")

age = np.array([[13], [21], [22], [31], [45], [28]])

binarizer = preprocessing.Binarizer(threshold=30)
print(binarizer.transform(age))
print()


age = np.array([[13], [21], [22], [31], [45], [28], [11], [2131], [123], [44]])

# 이전의 numpy 의 digitize 와 유사한 결과
print("# 이전의 numpy 의 digitize 와 유사한 결과")
kb = preprocessing.KBinsDiscretizer(
    4, encode='ordinal'
    , strategy='quantile')
print(kb.fit_transform(age))
print()


# 희소 행렬로 리턴
print("# 희소 행렬로 리턴")
kb = preprocessing.KBinsDiscretizer(
    4, encode='onehot'
    , strategy='quantile')
print(kb.fit_transform(age))
print()


# 밀집 행렬로 리턴
print("# 밀집 행렬로 리턴")
kb = preprocessing.KBinsDiscretizer(
    4, encode='onehot-dense'
    , strategy='quantile')
print(kb.fit_transform(age))
print()