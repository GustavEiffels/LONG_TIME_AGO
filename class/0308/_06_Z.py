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

''' Z 검정 '''
''' 모집단의 평균과 표준 편차를 알고 있을 때 새로운 표본의 평균이 
모집단의 평균과 차이가 없는지 여부를 결정 '''

''' 단일 표본 Z 검정 '''
# Sample Data 를 생성

np.random.seed(42)
N = 10
mu_0 = 0
x = ss.norm(mu_0).rvs(N)
print(x)
print()
''' result
 [ 0.49671415 -0.1382643   0.64768854  1.52302986 -0.23415337 -0.23413696
  1.57921282  0.76743473 -0.46947439  0.54256004]
'''

''' 단일 표본 z 검정을 수행해주는 함수 '''
def ztest_1samp(x, sigma2=1, mu=0):
    z = (x.mean() - mu) / np.sqrt(sigma2 / len(x))
    return print(z, 2 * sp.stats.norm().sf(np.abs(z)))

''' 샘플 데이터를 넣어서 수행 '''
ztest_1samp(x)

''' 유의 수준이 5% 이면 , 귀무 가설을 기각 
: 표본의 평균과 모집단의 평균이 같지 않다 .

이 데이터는 정규  
'''

# Sample 의 개수를 늘리
N = 100
x = sp.stats.norm(mu_0).rvs(N)
ztest_1samp(x)


import numpy as np
import scipy as sp
from scipy import stats

N = 10
mu_0 = 0
np.random.seed(0)
x = sp.stats.norm(mu_0).rvs(N)
print(x)

def ztest_1samp(x, sigma2=1, mu=0):
    z = (x.mean() - mu) / np.sqrt(sigma2/len(x))
    return z, 2 * sp.stats.norm().sf(np.abs(z))

print(ztest_1samp(x))

N = 100
mu_0 = 0
np.random.seed(0)
x = sp.stats.norm(mu_0).rvs(N)
print(ztest_1samp(x))

