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
seaborn 의 titanic 데이터 가져오기
'''
titanic = sns.load_dataset('titanic')

'''
RangeIndex 의 개수와 데이터의 개수가 맞지 않으면 결측치가 존재한다
'''
titanic.info()
print()

'''
각 데이터의 개수를 리턴 - 옵션이 없으면 None 은 제외 
'''
print("각 데이터의 개수를 리턴 - 옵션이 없으면 None 은 제외")
print(titanic['deck'].value_counts())
print()

'''
None 을 제외하지 않고 return 
'''
print("None 을 제외하지 않고 return")
print(titanic['deck'].value_counts(dropna=False))
print()

'''
isnull 이용 
'''
print(titanic['deck'].isnull().sum(axis=0))

