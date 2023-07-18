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

''' 재표본 추출 알고리즘 '''
# Data Load
click_rate = pd.read_csv('../python_statistics-main 2/click_rates.csv')
print(f'click_rate  : \n {click_rate}')
print()

''' 현재 위아래로 나열한 상태 '''
''' pivot을 실행  '''
clicks = click_rate.pivot(index='Click',
                          columns='Headline',
                          values='Rate')
print(f'clicks : \n {clicks}')
print()

# 관측치
click = click_rate.pivot(index='Click',
                         columns='Headline',
                         values='Rate')

# 기대치
row_average = clicks.mean(axis=1)
expectations = pd.DataFrame(
    {'Headline A': row_average,
     'Headline B': row_average,
     'Headline C': row_average}
)
print(f'expectations : \n {expectations}')
print()

''' 샘플링할 데이터를 생성 '''
import random

box = [1] * 34
box.extend([0] * 2966)
print(f' box[:10] : \n {box[:10]}')

# Random 하게 섞기
random.shuffle(box)

''' CHI2 제곱 통계량 계산 함수 '''


def chi2(observed, expected):
    pearson_residuals = []
    for row, expect in zip(observed, expected):
        pearson_residuals.append([(observe - expect) ** 2 / expect
                                  for observe in row])
    # return sum of squares
    return np.sum(pearson_residuals)



''' 클릭의 기대치 '''
expected_clicks = 34 / 3

''' 클릭하지 않을 기대치 '''
expected_noclicks = 1000 - expected_clicks

expected = [expected_clicks, expected_noclicks]

''' chi2 통계량을 계산 '''
chi2observed = chi2(clicks.values, expected)
print(f' 관측지의 카이 제곱 통계량 :   {chi2observed}')


# Sampling한 데이터로 chi2 통계량을 구해주는 함수

def perm_fun(box):
    sample_clicks = [sum(random.sample(box, 1000)),
                     sum(random.sample(box, 1000)),
                     sum(random.sample(box, 1000))]
    sample_noclicks = [1000 - n for n in sample_clicks]
    return chi2([sample_clicks, sample_noclicks], expected)

perm_chi2 = [perm_fun(box) for _ in range(2000)]


# p -value 계산
''' sampling 한 데이터의 카이제곱 통계량 값이 관측치의 카이제곱 통계량 보다 
큰 경우의 확률이 p-value '''
p_value = sum(perm_chi2 > chi2observed) / len(perm_chi2)
print(f'p_value : {p_value}')
print()

''' scipy 의 카이제곱 검정 '''
chisq, pvalue, df, expected = ss.chi2_contingency(clicks)

print(f' 관측치의 카이 제곱 통계량 : {chisq}')
print(f' 유의 확률 : {pvalue}')
print(f' 자유도 : {df}')
print(f' 기대치 : {expected}')
