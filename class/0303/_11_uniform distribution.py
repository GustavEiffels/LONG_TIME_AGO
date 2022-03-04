import numpy as np
import pandas as pd
import seaborn as sns
from scipy import stats as sp

# 시각화 패키지
import matplotlib.pyplot as plt

# 시각화에서 한글을 사용하기 위한 설정
import platform
from matplotlib import font_manager, rc

if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# 시각화에서 음수를 표현하기 위한 설정
import matplotlib

matplotlib.rcParams['axes.unicode_minus'] = False


''' 균일 분포 - 모든 데이터의 분포가 거의 동일한 형태의 분포 '''

# 균일 분포 객체를 생성

rv1 = sp.uniform()
rv2 = sp.uniform()


# Sample Data 50000개 생성
x_1 = rv1.rvs(5000)
x_2 = rv2.rvs(5000)

plt.figure(figsize=(12, 6))

plt.subplot(131)
sns.distplot(x_1, kde=False)
plt.title(' 균일 분포 ')
plt.xlabel(' 표본 값 ')
plt.xlim(-0.2, 2.2)

plt.subplot(132)
sns.distplot(x_2, kde=False)
plt.title(' 균일 분포 ')
plt.xlabel(' 표본 값 ')
plt.xlim(-0.2, 2.2)

plt.subplot(133)
sns.distplot(x_2 + x_1, kde=False)
plt.title(' 균일 분포 x_1 + x_2 ')
plt.xlabel(' 표본 값  x_1 + x_2 ')
plt.xlim(-0.2, 2.2)

plt.show()
