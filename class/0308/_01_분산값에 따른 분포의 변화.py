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

''' Sklearn 을 이용한 표본 추출 '''
from sklearn.model_selection import train_test_split


'''sample data '''
center = [5, 5, 4.5]
colors = 'brg'

data_1 = []
for i in range(3):
    data_1.append(ss.norm(center[i], 0.1).rvs(100))
    plt.plot(np.arange(len(data_1[i])) + i*len(data_1[0]),
             data_1[i], '.', color=colors[i])
plt.show()


'''sample 분산이 변경  '''
center = [5, 5, 4.5]
colors = 'brg'

data_1 = []
for i in range(3):
    data_1.append(ss.norm(center[i], 2).rvs(100))
    plt.plot(np.arange(len(data_1[i])) + i*len(data_1[0]),
             data_1[i], '.', color=colors[i])
plt.show()