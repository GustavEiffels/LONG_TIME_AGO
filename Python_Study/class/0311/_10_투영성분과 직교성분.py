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

''' '''
a = np.array([1, 2])
b = np.array([2, 0])
a2 = (a @ b) / np.linalg.norm(b) * np.array([1, 0])
a1 = a - a2

plt.annotate('', xy=b, xytext=(0, 0), arrowprops=green)
plt.annotate('', xy=a2, xytext=(0, 0), arrowprops=blue)
plt.annotate('', xy=a1, xytext=(0, 0), arrowprops=blue)
plt.annotate('', xy=a, xytext=(0, 0), arrowprops=red)
plt.plot(0, 0, 'kP', ms=10)
plt.plot(a[0], a[1], 'ro', ms=10)
plt.plot(b[0], b[1], 'ro', ms=10)
plt.text(0.35, 1.15, "$a$")
plt.text(1.55, 0.15, "$b$")
plt.text(-0.5, 1.05, "$a^{\perp b}$")
plt.text(0.50, 0.15, "$a^{\Vert b}$")
plt.xticks(np.arange(-10, 10))
plt.yticks(np.arange(-10, 10))
plt.xlim(-1.2, 4.1)
plt.ylim(-0.5, 3.2)
plt.show()