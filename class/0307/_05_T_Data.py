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


''' tdata.cvs 읽어서 성적의 평균이 75점이라고 할 수 있는지 , 유의수준 5%로 양측 검정'''
tdata = pd.read_csv('../python_statistics-main 2/tdata.csv', encoding='cp949')
tdata.info()
print()

result = ss.ttest_1samp(tdata['성적'], popmean=75).pvalue
print('평균이 75점이라고 할 수 있는지')
print(result)
print()

if result > 0.05:
    # result 가 0.05 보다 크다면 귀무가설 채택
    print(' 귀무가설을 체택해서 평균이 75 라고 할 수 있다.')
else:
    print(" 귀무 가설을 기각해서 평균이 75라고 할 수가 없다 ")
