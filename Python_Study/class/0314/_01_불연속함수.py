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


'''  불연속 함수 '''
'''특정 조건이나 값을 기준으로 출력되는 값이 갑자기 변하는 함수 '''

# numpy 의 sign 함수
print(f'np.sign(10) : {np.sign(10)}')
print(f' np.sign(0) : {np.sign(0)}')
print(f' np.sign(-1) : {np.sign(-1)}')
print()
'''
result 
np.sign(10) : 1
np.sign(0) : 0
np.sign(-1) : -1
'''

''' 단위 계단 함수 '''
def heaviside_step(x) ->float:
    # 대입된 parameter 가 numpy 의 ndarray 라면 - 자료형을 확인
    if isinstance(x, np.ndarray):
        return np.where(x >= 0, 1, 0)
    else:
        return 1.0 if x >= 0 else 0.0

print(f'heaviside_step(10) : {heaviside_step(10)}')
print(f'heaviside_step(np.array([10, 20, 30])) '
      f': {heaviside_step(np.array([10, 20, 30]))}')
print(f'heaviside_step(-1) : {heaviside_step(-1)}')
print()
'''
result
heaviside_step(10) : 1.0
heaviside_step(np.array([10, 20, 30])) : [1 1 1]
heaviside_step(-1) : 0.0
'''