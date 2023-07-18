import numpy as np
import pandas as pd

# 시각화 패키지
import matplotlib.pyplot as plt
import seaborn as sns

# 통계 관련 package
import scipy as sp
import scipy.stats as ss

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
import datetime



''' 지수 분포 - 다음 사건이 발생하기 까지의 대기 시간의 모형 '''
# 실제 데이터에서는 단위 시간을 설정하고 표본에서 아래와 같은 값을 구해야한다

''' 사건의 발생 횟수 '''
loc = 0.5

''' 표준 편차 '''
scale = 1.0/0.5

pd = ss.expon(loc=loc, scale=scale)

x = np.linspace(1, 20, 100)

pdf = []

for num in x:
    result = pd.pdf(num)
    pdf.append(result)

plt.plot(x, pdf, linewidth=2.0)
plt.show()

# pdf 는 각각의 확률
# cdf 는 누적된 확률


pdf = []

for num in x:
    result = pd.cdf(num)
    pdf.append(result)

plt.plot(x, pdf, linewidth=2.0)
plt.show()
