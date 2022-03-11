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

plt.rc("font", size=18)  # 그림의 폰트 크기를 18로 고정
gray = {"facecolor": "gray"}
black = {"facecolor": "black"}
red = {"facecolor": "red"}
green = {"facecolor": "green"}
blue = {"facecolor": "blue"}

''' Rank 선형 독립인 벡터의 최대 개수 '''
# 앞의 데이터를 어떻게 조작해도 뒤의 데이터를 만들 수 없음  - Rank 2
ar = np.array([[1, 2], [3, 3]])
print(f'np.linalg.matrix_rank(ar) : {np.linalg.matrix_rank(ar)}')
print()

# result
''' np.linalg.matrix_rank(ar) : 2 '''

# 앞의 2개의 선형 조합으로 세번째 데이터를만들 수 있기 때문에 - Rank2
ar = np.array([[1, 2], [3, 3], [5, 7]])
print(f'np.linalg.matrix_rank(ar) : {np.linalg.matrix_rank(ar)}')
print()

# result
''' np.linalg.matrix_rank(ar) : 2 '''