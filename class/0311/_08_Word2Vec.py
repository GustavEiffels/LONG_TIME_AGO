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

a = np.array([2, 2])
b = np.array([3, 4])
c = np.array([4, 1])
d = a + (c - a)
e = b + (c - a)
plt.annotate('', xy=b, xytext=a, arrowprops=black)
plt.annotate('', xy=e, xytext=d, arrowprops=black)
plt.annotate('', xy=c, xytext=[0, 0], arrowprops=gray)
plt.plot(0, 0, 'kP', ms=10)
plt.plot(a[0], a[1], 'ro', ms=10)
plt.plot(b[0], b[1], 'ro', ms=10)
plt.plot(c[0], c[1], 'ro', ms=10)
plt.text(1.6, 1.5, "서울")
plt.text(2.5, 4.3, "한국")
plt.text(3.5, 0.5, "웰링턴")
plt.text(4.9, 3.2, "뉴질랜드")
plt.xticks(np.arange(-2, 7))
plt.yticks(np.arange(-1, 6))
plt.xlim(-1.4, 6.4)
plt.ylim(-0.6, 5.8)
plt.show()
