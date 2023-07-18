# 선형대수
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



''' 선형 대수와 해석 기하 '''
# 1. 벡터의 길이
# ----> 각 좌표에서 원점의 좌표를 뺀 값을 제곱해서 더한후
# 제곱근을 취함 : 유클리드 거리

a = np.array(([2, 2]))
print(f'np.linalg.norm(a) : {np.linalg.norm(a)}')
print()


# result --- 벡터의 길이
'''
np.linalg.norm(a) : 2.8284271247461903
'''


import math
print(f' 8 의 제곱근 : {math.sqrt(8)}')


