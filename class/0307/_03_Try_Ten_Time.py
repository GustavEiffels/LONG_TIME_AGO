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


''' 이항 검정 : 총 시도 횟수 10번 중 1 이  7 번 나왔을 때 유의 확률 '''
np.random.seed(33)
x = ss.binom_test(7, 10)
print(x)
# result ----> 0.34375
''' 유의 확률이 34.375% '''

# 유의 수준을 얼마로 잡던 유의 확률이 크기 때문에 , 귀무 가설을 기각 할 수 없음
# 이런 경우가 발생할 수도 있다 .



# 이항 검정 - tips 데이터에서 여자 손님 중 비흡연자가 흡연자보다 많다고 할 수 있는지
## 유의수준 10% 로 검정
''' 데이터 가져오기 '''
tips = sns.load_dataset('tips')
tips.info()
print()
''' 알아야 할 것들 !!  '''
# 1. 여자 손님의 수
# 2. 여자 손님 중 흡연자와 비흡연자의 수

''' 여자 손님의 수 - sex 가 Female'''
female = tips[tips['sex'] == 'Female']
female_cnt = female['sex'].count()
print(female_cnt)
print()

''' 흡연 여부 - smoker 속성이 No 이면 비흡연 , 그렇지 않으면 흡연 '''
non_smoke_cnt = female[female['smoker'] == 'No'].count()
# 여자중 비흡연자 수
print(non_smoke_cnt[0])
print()

# 이항 분포 사용 여자중 비흡연자수 , 여자 전체 수
x = ss.binom_test(non_smoke_cnt[0], female_cnt)
print(x)

if x < 0.1:
    print(' 귀무 가설이 기각 ')
    if non_smoke_cnt[0] > (female_cnt/2):
        print('가게를 이용하는 여성 손님들은 비흡연자일 확률이 높다')
    else:
        print('가게를 이용하신 여성 손님들은 비흡연자일 확률이 낮다')
else:
    print(' 귀무 가설 체택 ')


