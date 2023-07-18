import numpy as np
import pandas as pd
import statsmodels.api as sm

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

from statsmodels.formula.api import ols


''' 주사위 게임을 했을 때 , 아래와 같은 결과가 나오면 이 주사위는 공정한 주사위 인가 ?'''

N = 60
K = 6
theta_0 = np.ones(K)/K
print(f' theta_0 : {theta_0}')
print()

# 실제 데이터
np.random.seed(42)
x = np.random.choice(K, N, p=theta_0)
print(f' x : {x}')
print()

n = np.bincount(x, minlength=K)
print(f' n : {n}')

''' 위의 n 의 경우가 일반적인 경우인지 특별한 경우인지 확인 해보기 '''
# 일원 카이제곱 검정
result = sp.stats.chisquare(n)
print(f' 일원 카이제곱 검정의 결과 : {result}')
print(f' 일원 카이제곱 검정의 결과 statistic : {result.statistic}')
print(f' 일원 카이제곱 검정의 결과 p_value : {result.pvalue}')
print()


# 5가지 스포츠 음료에 대한 선호도 조사
n = [41, 30, 51, 71, 61]

result = sp.stats.chisquare(n)
print(result)
print(f' result.pvalue : {result.pvalue}')
# result
'''
Power_divergenceResult(statistic=20.488188976377952, pvalue=0.00039991784008227264)
result.pvalue : 0.00039991784008227264
'''
# pvalue 가 0.05 보다 작으므로 귀무가설을 기각
# 선호도에 차이가 있다고 간주
# 횟수가 높기 때문에