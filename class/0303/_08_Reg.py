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


import pandas_datareader.data as web


np.random.seed(42)

mu = 1


rv = sp.norm(loc=mu)

''' 로그 정규 분포 데이터 생성 '''
x1 = rv.rvs(1000)

''' 로그 정규 분포 '''
x2 = np.exp(0.5*x1)


''' 그래프 두개 그리기 '''
fig, ax = plt.subplots(1, 2)
sns.distplot(x1, kde=False, ax=ax[0])
ax[0].set_title(' 정규 분포 ')


sns.distplot(x2, kde=False, ax=ax[1])
ax[1].set_title('로그 정규 분포 ')
plt.show()