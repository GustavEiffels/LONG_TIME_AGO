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


''' 동전을 15번 던졌을 대 12번 나온 경우에 대한 검정 '''

# 시행 횟수
N = 15

''' 이 동전이 공정하다면 각각의 확률은 0.5 '''
# 1 이 나올 확률
mu = 0.5

# 이항 분포 객체를 생성
rv = ss.binom(N, mu)

# simulation 을 하기 위해서 범위를 설정
''' 0 ~  N  까지 '''
xx = np.arange(N+1)


''' 확률 질량 함수 그리기 - 이산 확률 변수에서 각각의 값이 나올 확률 '''
plt.subplot(211)
plt.stem(xx, rv.pmf(xx))
plt.ylabel("pmf")
plt.title(" 검정 통계량 분포 ( N = 15 인 이항 분포 ) 의 확률 질량 함수 ")
plt.annotate(' 검정 통계량 t  = 12 ', xy=(12, 0.02), xytext=(12, 0.1),
             arrowprops={'facecolor' : 'black'})

''' 누적 분포 함수 기리기 - 현재값까지 나올 확률 '''
plt.subplot(212)
plt.stem(xx, rv.cdf(xx))
plt.ylabel("cdf")
plt.title("검정 통계량 분포 ( N = 15 인 이항 분포 )의 누적 분포 함수 ")
plt.show()

''' 귀무 가설은 이 동전은 공정하다 - 1이 나올 확률은 0.5 
    대립 가설은 이 동전은 공정하지 않다  - 1 이 나올 확률이 0.5 가 아니다 '''
''' 유의 확률 - 15번 던졌을 때 12 번 이상 나올 확률 '''

# 양측 검정이라면
''' 11 까지의 누적 확률 , 11 번 나올 때 까지의 누적 확률  '''
print(2*(1-rv.cdf(11)))
# 0.03515625

''' 유의 수준이 5% 라면 유의 확률이 유의 수준보다 작기 때문에 구미가설 기각 
: 이 동전은 공정하지 않다 '''

''' 유의 수준이 1% 라면 유의 확률이 유의 수준보다 크기 때문에 귀무 가설을 기각 
: 이 동전은 공정하고 이런 결과가 나올 수도 있다 .'''


''' 단측 검정 
이 동전은 앞면이 나올 가능 성이 높다 '''
print(1-rv.cdf(11))
# result --> 0.017578125