import mpg_set_column as mp
import numpy as np
import pandas as pd


mp.mpg.info()
print()

mpg1 = mp.mpg
'''
horsepower 를 3개의 구간으로 분할
'''

'''
실수로 분할하기 위해서 ?를 None 으로 치환 
'''
mpg1['horsepower'].replace('?', np.nan, inplace=True)

# horsepower 가 None 인 행을 삭제
mpg1.dropna(subset=['horsepower'], axis=0, inplace=True)

# 실수로 변환
mpg1['horsepower'] = mpg1['horsepower'].astype('float')
mpg1.info()
print()

'''
구간의 이름을 생성 
'''
bin_names = ['저출력', '보통 출력', '고출력']

# 구간의 경계값을 생성
count, bin_dividers = np.histogram(mpg1['horsepower'], bins=3)
print(bin_dividers)
print()

'''
구간 분할 
'''
print("======================= 구간 분할 ======================= ")
mpg1['hp_bin'] = pd.cut(x=mpg1['horsepower'],
                        bins=bin_dividers,
                        labels=bin_names,
                        include_lowest=True)
print(mpg1)
print()

'''
one hot encoding
-- category 데이터의 개수만큼 컬럼이 생성 
-- 자신의 값에 해당하는 컬럼에만 1 을 대입하고 나머지는 0
'''
horsepwer_dummies = pd.get_dummies(mpg1['hp_bin'])
print(horsepwer_dummies)
print()

'''
sklearn one hot encoding 
'''
from sklearn import preprocessing
one_hot = preprocessing.LabelBinarizer()
print(one_hot.fit_transform(mpg1['hp_bin']))
print()

'''
여러 개의 특성을 one hot encoding 
'''
multiclass_feature = [
    ("Python", "Java"),
    ("C++", "Python"),
    ("C++", "Java"),
    ("Java", "JavaScript"),
    ("Python", "JavaScript"),
    ("Java", "JavaScript")
]

one_hot_multiclass = preprocessing.MultiLabelBinarizer()

'''
훈련 
'''
print("===================")
one_hot_multiclass.fit_transform(multiclass_feature)
print(one_hot_multiclass.fit_transform(multiclass_feature))
print()
'''
실행 
'''
print(one_hot_multiclass.classes_)
print()

'''
하나의 컬럼에 일련번호 형태로 OneHotEncoding 
'''
label_encoder = preprocessing.LabelEncoder()
print(label_encoder.fit_transform(mpg1['hp_bin']))
print()

'''
희소행렬로 표현 ---> 0 이 아주 많은 행렬 
0 이 아닌 좌표만 가지고 있다 .
---> 0 이 아주 많은 행렬에서 1 의 위치만 기억하는 것 !! 
'''
features = [['java', 1], ['C++', 2], ["C#", 1], ["Python", 2]]

one_encoder = preprocessing.OneHotEncoder()
print(one_encoder.fit_transform(features))
print()

# 밀집 행렬로 표현 -- OneHotEncoding 과 유사한 결과
one_encoder = preprocessing.OneHotEncoder(sparse=False)
print(one_encoder.fit_transform(features))
print()

