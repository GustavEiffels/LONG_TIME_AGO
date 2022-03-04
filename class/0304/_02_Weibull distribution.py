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
from reliability.Distributions import Weibull_Distribution as wb


''' alpha = 수명   beta = 가중치 '''
dist = wb(alpha=50, beta=2)

''' pdf '''
dist.PDF()
plt.show()

''' cdf '''
dist.CDF()
plt.show()


''' alpha = 수명   beta = 가중치 '''
dist1 = wb(alpha=50, beta=4)

''' pdf '''
dist1.PDF()
plt.show()

''' cdf '''
dist1.CDF()
plt.show()
#
#
#
''' alpha = 수명   beta = 가중치 '''
dist2 = wb(alpha=100, beta=2)

''' pdf '''
dist2.PDF()
plt.show()

''' cdf '''
dist2.CDF()
plt.show()

