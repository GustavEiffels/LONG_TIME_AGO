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


# 평균이 0 인 정규 분포를 가진 확률 변수의 10개 생성
np.random.seed(1)
x = ss.norm(0).rvs(10)
print('sample ---> 10')
print(x)
print()


''' 유의 수준을 5%를 적용하게 되면 귀무 가설 기가 - 평균은 0 이 아니다'''
print(ss.ttest_1samp(x, popmean=0))
print()


# 데이터의 개수를 늘림
x = ss.norm(0).rvs(10)
print('sample ---> 1000')
print(ss.ttest_1samp(x, popmean=0))
print()

