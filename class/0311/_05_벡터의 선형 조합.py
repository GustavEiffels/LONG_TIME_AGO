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


''' 벡터의 선형 조합 - 여러 벡터에 스칼라 곱을 곱해서 더한 것을 지칭 '''


x1 = np.array([1, 2])
x2 = np.array([2, 1])
x3 = 0.5 * x1 + x2
plt.annotate('', xy=0.5*x1, xytext=(0, 0), arrowprops=gray)
plt.annotate('', xy=x2, xytext=(0, 0), arrowprops=gray)
plt.annotate('', xy=x3, xytext=(0, 0), arrowprops=black)
plt.plot(0, 0, 'kP', ms=10)
plt.plot(x1[0], x1[1], 'ro', ms=10)
plt.plot(x2[0], x2[1], 'ro', ms=10)
plt.plot(x3[0], x3[1], 'ro', ms=10)
plt.plot([x1[0], 0], [x1[1], 0], 'k--')
plt.text(0.6, 2.0, "$x_1$")
plt.text(-0.5, 0.5, "$0.5x_1$")
plt.text(1.15, 0.25, "$x_2$")
plt.text(2.5, 1.6, "$0.5x_1 + x_2$")
plt.xticks(np.arange(-2, 5))
plt.yticks(np.arange(-1, 4))
plt.xlim(-1.4, 4.4)
plt.ylim(-0.6, 3.8)
plt.title(" 벡터의 선형 조합 ")
plt.show()
