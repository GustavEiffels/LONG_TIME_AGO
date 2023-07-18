import random

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


''' 등분산 검정 '''
# 2 개의 데이터 집합의 분산이 같은지 확인
''' 2개 데이터의 개수 '''
N1 = 100
N2 = 100

# 2개 데이터의 표준 편차
sigma_1 = 1.0
sigma_2 = 1.2


x1 = ss.norm(0, sigma_1).rvs(N1)
x2 = ss.norm(0, sigma_2).rvs(N2)

print(f' x1 : {x1}')
print()
print(f' x2 : {x2}')
print()

# 데이터의 분포 알아보기
ax = sns.distplot(x1, kde=False, fit=ss.norm, label='1번 데이터')
ax = sns.distplot(x2, kde=False, fit=ss.norm, label='2번 데이터')
ax.lines[0].set_linestyle(':')
plt.legend()
plt.show()


random.seed(42)
# simulation
print(f' x1 의 표준 편차 : {x1.std()}')
print()
print(f' x2 의 표준 편차 : {x2.std()}')
print()
''' 2개 데이터 집단의 분산 차이가 유의미 한 것인지 확인 '''
'''
result 
 x1 의 표준 편차 : 0.970136650015704

 x2 의 표준 편차 : 1.2757835226439156
'''

''' 2개 데이터 집단의 분산 차이가 유의미 한 것인지 확인 '''
print(f'바틀렛 등분산 검정 : {ss.bartlett(x1, x2)}')
print(f'플리그너 등분산 검정 : {ss.fligner(x1, x2)}')
print(f'플리그너 등분산 검정 : {ss.levene(x1, x2)}')

'''result 
바틀렛 등분산 검정 : BartlettResult(statistic=1.229846364725496, pvalue=0.2674369185523491)
플리그너 등분산 검정 : FlignerResult(statistic=1.758289919245334, pvalue=0.18483795887964272)
플리그너 등분산 검정 : LeveneResult(statistic=1.6988199331334062, pvalue=0.19395591072437474)
'''

'''  실행 마다 값이 다르게 나오기 때문에 seed 를 고정시켜서 하는 것이 좋다 . '''