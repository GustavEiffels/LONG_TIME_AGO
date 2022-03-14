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

''' 로지스틱 함수를 오른쪽으로 5 , 아래쪽으로 1 만큼 평행 이동 '''

''' 로지스틱 함수 '''
def logistic(x):
    return 1/(1 + np.exp(-x))
'''  x 축 이동 '''
xx = np.linspace(-10, 10, 100)
plt.plot(xx, logistic(xx), ls='-')
plt.plot(xx, logistic(xx-5), ls='-')
plt.title("로지스틱 평행이동 ")
plt.show()

'''  y 축 이동 '''
xx = np.linspace(-10, 10, 100)
plt.plot(xx, logistic(xx), ls='-')
plt.plot(xx, logistic(xx-5)-1, ls='-')
plt.title("로지스틱 평행이동 ")
plt.show()

''' 함수 스케일링 '''
xx = np.linspace(-10, 10, 100)
plt.plot(xx, logistic(xx), ls='-')
plt.plot(xx, 2*logistic(2*xx), ls='-')
plt.title("함수 스케일링  ")
plt.show()