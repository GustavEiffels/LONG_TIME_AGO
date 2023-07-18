# 선형대수
import blue as blue
import numpy as np

import pandas as pd

import matplotlib.pyplot as plt


# numpy 와 호환되는 자료 구조 ( Series, DataFrame )
import red as red
from _plotly_utils.colors.cmocean import gray
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

''' 스칼라와 벡터의 곱 '''

a = np.array([1, 2])
b = 2 * a
c = -a
plt.annotate('', xy=b, xytext=(0, 0), arrowprops=red)
plt.text(0.8, 3.1, "$2a$")
plt.text(2.2, 3.8, "$(2, 4)$")

plt.annotate('', xy=a, xytext=(0, 0), arrowprops=gray)
plt.text(0.1, 1.3, "$a$")
plt.text(1.1, 1.4, "$(1, 2)$")
plt.plot(c[0], c[1], 'ro', ms=10)

plt.annotate('', xy=c, xytext=(0, 0), arrowprops=blue)
plt.text(-1.3, -0.8, "$-a$")
plt.text(-3, -2.5, "$(-1, -2)$")
plt.plot(0, 0, 'kP', ms=20)

# 축에 벼오지는 레이블 설정
plt.xticks(np.arange(-5, 6))
plt.yticks(np.arange(-5, 6))

# 축의 범위
plt.xlim(-4.4, 5.4)
plt.ylim(-3.2, 5.2)

# 화면 출력
plt.show()
