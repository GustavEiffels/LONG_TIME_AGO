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

''' data load '''
trainin_rel = pd.read_csv('../python_statistics-main 2/training_ind.csv')
toy_df = trainin_rel[:6].copy()
print(toy_df)
print()

''' 전체에서 순위를 구하기 '''
rank = ss.rankdata(np.concatenate([toy_df['A'], toy_df['B']]))
rank_df = pd.DataFrame({'A': rank[:5], 'B': rank[5: 10]}).astype(int)
print(rank_df)
print()

# A열의 순위 합을 구함
n1 = len(rank_df['A'])
u = rank_df['A'].sum() - (n1 * (n1 + 1))/2
print(u)
print()

u, p = ss.mannwhitneyu(trainin_rel['A'],
                       trainin_rel['B'],
                       alternative='two-sided')
print(p)