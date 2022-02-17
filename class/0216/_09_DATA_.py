import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import platform
from sklearn import preprocessing
from matplotlib import font_manager, rc
import seaborn as sns

'''
데이터 읽어오기 
'''
mpg = pd.read_csv('./auto-mpg.csv', header=None)

'''
컬럼 이름 만들기 
'''

mpg.columns = [
    'mpg',
    'cylinders',
    'displacement',
    'horsepower',
    'weight',
    'acceleration',
    'model year',
    'origin',
    'name'
]

'''
horsepower 를 숫자 자료형으로 변경 
'''
mpg['horsepower'] = mpg['horsepower'].replace('?', '0')
mpg['horsepower'] = mpg['horsepower'].astype('float')

'''
3개로 분활하기 위한 경계값 만들기 
'''
count, bin_dividers = np.histogram(mpg['horsepower'], bins=2)
print(bin_dividers)

'''
각 분할에 할당할 값을 생성
'''
# 3개로 분할할 거라서 3개의 값이 있어야한다

bin_names = ['low output',  'High output']

mpg['hp_bin'] = pd.cut(x=mpg['horsepower'],
                       labels=bin_names,
                       bins=bin_dividers,
                       include_lowest=True)
print(mpg[['horsepower', 'hp_bin']])

