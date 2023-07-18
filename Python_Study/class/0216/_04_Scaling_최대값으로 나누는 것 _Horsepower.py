import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import platform
from matplotlib import font_manager, rc

# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# For Printing Minus
plt.rcParams['axes.unicode_minus'] = False

''' 데이터 읽어 오기 '''
mpg = pd.read_csv('./auto-mpg.csv', header=None)

'''
컬럼 이름 만들기 
'''
mpg.columns=['mpg',
             'Cylinders',
             'displacement',
             'horsepower',
             'weight',
             'acceleration',
             'model year',
             'origin',
             'name']

mpg.info()

'''
— mpg 가 갤런당  마일 
		우리나라는 L/ KM 를 사용하기 때문에 
			변환해서 열을 추가 
# 1.60934/3.78541
'''
# 소수 둘째 자리 까지 반올림
# python 은 나눗샘을 하면 남은 메모리까지 계속 나누어 짐으로 소수점 자리를 추가
mpg['kpi'] = (mpg['mpg'] * 1.60934/3.78541).round(2)
print(mpg.head())

# 자료형을 확인할 수 있는 방법
mpg.info()
print(mpg.dtypes)

'''
horse power 열의 데이터를 실수로 변환 
'''

# 1. 원인 찾기
''' horse power 의 값들을 확인 '''
print(mpg['horsepower'].unique())
print()

# 1. 물음표를 NaN 으로 변환
# 데이터 변경시 사용할 replace 사용
mpg['horsepower'].replace('?', np.nan, inplace=True)
# 리턴을 하기 때문에 담아줘야한다
mpg['horsepower'] = mpg['horsepower'].astype('float')
print(mpg['horsepower'].unique())
mpg.info()

'''
정수 데이터를 mapping 이 되는 문자열로 변환
'''
mpg['origin'].replace({1: 'USA', 2: 'EU', 3: 'JPN'}, inplace=True)
print(mpg.head())


'''
범주형으로 변환 
'''
# category 변환시 info 를 사용해서 확인
mpg['origin'] = mpg['origin'].astype('category')
mpg.info()


'''
scaling - 최대값
'''
mpg['scale_horsepower'] = mpg['horsepower'] / mpg['horsepower'].max()
print(mpg.head())


'''
horsepower :scaling - 최대값 - 최소 값 
'''
print("horsepower :scaling - 최대값 - 최소 값")
val = mpg['horsepower'].max()-mpg['horsepower'].min()
mpg['scale_horsepower'] = mpg['horsepower'] /val

print(mpg)