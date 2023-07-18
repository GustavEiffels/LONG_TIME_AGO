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
결측치 제거
'''

'''
누락된 데이터 생성 
'''
features = np.array([
    [1.1, 11.1],
    [2.1, 21.1],
    [3.1, 31.1],
    [4.1, 41.1],
    [np.nan, 51.1]
])

print(features)
print()
'''
~ 연산자를 이용한 None 제거 
isnan 이 True 인 데이터를 제외하고 가져오기 
'''
features = np.array([
    [1.1, 11.1],
    [2.1, 21.1],
    [3.1, 31.1],
    [4.1, 41.1],
    [np.nan, 51.1]
])

print(features[~np.isnan(features).any(axis=1)])

'''
None 의 개수가 500 개 이상인 열 제거
'''
titanic = sns.load_dataset('titanic')
titanic.info()
print()

titanic_thresh = titanic.dropna(axis=1, thresh=500)
titanic_thresh.info()
print()

'''
age 가 None 인 데이터만 삭제 
'''
titanic_age = titanic.dropna(subset=['age'], how='any')
titanic_age.info()
print()