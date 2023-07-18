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



''' 일원 분산 분석 '''
# 2개 변수 ( 1개의 독립변수 ,1개의 종속 변수 ) 를 가지고 분류가 있는 경우

import urllib.request

# url로 데이터 얻어오기
url = 'https://raw.githubusercontent.com/thomas-haslwanter/statsintro_python/master/ipynb/Data/data_altman/altman_910.txt'
data = np.genfromtxt(urllib.request.urlopen(url), delimiter=',')
# [1] 데이터가 엽산 수치를 말함
print(data)

''' 그룹별로 분할 '''
group1 = data[data[:, 1] == 1, 0]
group2 = data[data[:, 1] == 2, 0]
group3 = data[data[:, 1] == 3, 0]

# 1번 그룹의 엽산 수치
print(group1)


''' box plot  으로 만들기 '''
plot_data = [group1, group2, group3]
ax = plt.boxplot(plot_data)
plt.title("box plot  으로 만들기")
plt.show()

''' 분산 분석 실행 '''
# F 통계
F_statistic, p_value = ss.f_oneway(group1, group2, group3)
print(p_value)
''' 유의 수준을 5% 로 적용 '''
''' 귀무 가설 - 모든 그룹의 분산이 동일하다 '''
# result  : 0.043589334959178244

if p_value < 0.05:
    print(' 귀무 가설을 기각해서 각 그룹의 차이는 유의미 하다. ')
else:
    print(' 귀무 가설을 기각하지 못해서 각 그룹의 분산의 차이는 무의미')

from statsmodels.formula.api import ols

df = pd.DataFrame(data , columns=['value', 'treatment'])

model = ols('value ~C(treatment)', df).fit()
print(sm.stats.anova_lm(model))