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


'''
데이터를 생성 
'''

houses = pd.DataFrame()
houses['price'] = [80000, 90000, 100000]
houses['bathrooms'] = [2, 4, 300]
houses['square_feet'] = [1000, 1200, 1400]
# square _feet 은 평수

print(houses)
print()

'''
새로운 특성 추가 - bathroom 이 20 개가 넘는 경우 새로운 특성으로 변환 
'''
houses['outlier'] = np.where(houses['bathrooms'] < 20, 0, 1)
print(houses)
print()


'''
데이터를 변환 === log 변환 
-- log 를 적용하는 것 
'''
# 숫자의 단위가 너무 크기때문에
# 이상치를 줘서 영향을 줄일 수 있음

houses['log_square_feet'] = [np.log(x) for x in houses['square_feet']]
print(houses)
print()

'''
스케일링 - scailing
'''
'''
1. RobustScaler // 이상치에 덜 민감한 스케일링
'''
scailer = sklearn.preprocessing.RobustScaler()
scailer.fit(houses[['bathrooms']])
print(scailer.transform(houses[['bathrooms']]))
print()

'''
2 . StandardScaler : 이상치에 민감한 스케일링
'''
scailer = sklearn.preprocessing.StandardScaler()
scailer.fit(houses[['bathrooms']])
print(scailer.transform(houses[['bathrooms']]))
print()

'''
3. MinMaxScaler
'''
scailer = sklearn.preprocessing.MinMaxScaler()
scailer.fit(houses[['bathrooms']])
print(scailer.transform(houses[['bathrooms']]))
print()
