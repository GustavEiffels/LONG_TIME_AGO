import numpy as np

import pandas as pd

import matplotlib.pyplot as plt


# numpy 와 호환되는 자료 구조 ( Series, DataFrame )
from matplotlib import font_manager, rc

# 운영 체제
import platform

# 시각화
import seaborn as sns

# 통계
import statsmodels.api as sm
import statsmodels.formula.api as swf
from statsmodels.stats import power

# 데이터 전처리 & 머신러닝
import sklearn

import matplotlib

path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)

matplotlib.rcParams['axes.unicode_minus'] = False

plt.rc("font", size=18)  # 그림의 폰트 크기를 18로 고정
gray = {"facecolor": "gray"}
black = {"facecolor": "black"}
red = {"facecolor": "red"}
green = {"facecolor": "green"}
blue = {"facecolor": "blue"}


''' 지수함수 '''

''' 오일러 수 확인 '''
print(f'np.e : {np.e}')
print()
'''
result
np.e : 2.718281828459045
'''


''' 지수함수를 이용해서 구한 값 '''
print(f'np.exp(-10) : {np.exp(-10)}')
print(f'np.exp(-10) : {np.exp(-10)}')
print(f'np.exp(-1) : {np.exp(-1)}')
print(f'np.exp(0) : {np.exp(0)}')
print(f'np.exp(1) : {np.exp(1)}')
print(f'np.exp(10) : {np.exp(10)}')
print()
'''
result 
np.exp(-10) : 4.5399929762484854e-05
np.exp(-10) : 4.5399929762484854e-05
np.exp(-1) : 0.36787944117144233
np.exp(0) : 1.0
np.exp(1) : 2.718281828459045
np.exp(10) : 22026.465794806718
'''

''' 
그래프로 그리기 
--- 데이터 값이 증가할 때 조금 더 빠른 속도로 증가
'''
xx = np.linspace(-2, 2, 100)
yy = np.exp(xx)
plt.plot(xx, yy)
plt.title("지수함수 -2 에서 2 까지")
plt.show()