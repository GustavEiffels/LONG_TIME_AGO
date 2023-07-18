import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import platform

import sklearn.preprocessing
from matplotlib import font_manager, rc
from sklearn import preprocessing
from sklearn.preprocessing import PolynomialFeatures
from sklearn.covariance import EllipticEnvelope


# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# For Printing Minus
plt.rcParams['axes.unicode_minus'] = False


'''
titanic 데이터 불러오기 
'''
titanic = sns.load_dataset('titanic')
titanic.info()
print()

'''
결측치 대체 
-- 829 번째 데이터에 NaN 이 존재 
'''
print("before ======================")
print(titanic['embark_town'][825: 835])
print()

'''
이전 값으로 채우기
ffill, bfill 사용 가능 
method를 제거하고 다른 값으로 작성해도 된다 .
'''
print("after - ffill  ======================")
titanic['embark_town'].fillna(method='ffill', inplace=True)
print(titanic['embark_town'][825: 835])
print()


'''
가장 자주 등장하는 데이터로 채우기 
'''
titanic = sns.load_dataset('titanic')


'''
embark_town 의 개수를 확인 
'''
mode = titanic['embark_town'].value_counts()
print(mode)
print()

'''
결측치를 가장 빈번히 등장하는 단어로 채우기
'''
titanic['embark_town'].fillna(mode.idxmax(), inplace=True)
print(titanic['embark_town'][825: 835])
print()



'''
중간 값으로 대체 ---> SimpleImputer 이용

titanic age 에서 Nan 값이 어디 있는지 확인
'''

from sklearn.impute import SimpleImputer
# None 데이터 확인
print(titanic['age'][880:890])


# 중간 값 확인
print(titanic['age'].median())
print()
'''
데이터를 생성 
strategy 에 적으면 중간값 작성 
'''
simple_imputer =SimpleImputer(strategy='median')
result = simple_imputer.fit_transform(titanic[['age']])
print("채워진 데이터 확인")
print(result[888])


