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


''' 로그 함수 '''
# 로그 함수는 0 이하의 값에는 반응하지 않음
print(f'np.log(0) : {np.log(0)}')
'''  RuntimeWarning: 
divide by zero encountered in log print(f'np.log(0) : {np.log(0)}')'''
print()
print(f'np.log(0.8) : {np.log(0.8)}')
print(f'np.log(1) : {np.log(1)}')
print(f'np.log(10) : {np.log(10)}')
print(f'np.log(100) : {np.log(100)}')
print()
''' result
 np.log(0) : -inf
np.log(0.8) : -0.2231435513142097
np.log(1) : 0.0
np.log(10) : 2.302585092994046
np.log(100) : 4.605170185988092
'''

xx = np.linspace(0.01, 8, 100)
yy = np.log(xx)
plt.plot(xx, yy)
plt.show()