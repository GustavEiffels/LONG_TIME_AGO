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

# 음주 데이터 분석

''' 데이터를 가져오고 확인 '''
drinks = pd.read_csv('../python_statistics-main 2/drinks.csv')
drinks.info()
print()

''' 결측치 대체 '''
print(" 결측치 대체 이후 ")
drinks['continent'] = drinks['continent'].fillna('OT')
drinks.info()
print()

''' 상관 분석 - 2개의 숫자 데이터 간의 관련성 파악 '''
corr = drinks[['beer_servings', 'wine_servings']].corr(method='pearson')
print(corr)
print()
# result
'''
                beer_servings  wine_servings
beer_servings       1.000000       0.527172
wine_servings       0.527172       1.000000
'''


''' cols 이름 '''
''' 주종간 상관계수 알아보기 '''
cols = ['beer_servings',
        'spirit_servings',
        'wine_servings',
        'total_litres_of_pure_alcohol']
corr = drinks[cols].corr(method='pearson')
print(corr)
print()
''' result '''
''' 
                                beer_servings  ...  total_litres_of_pure_alcohol
beer_servings                      1.000000  ...                      0.835839
spirit_servings                    0.458819  ...                      0.654968
wine_servings                      0.527172  ...                      0.667598
total_litres_of_pure_alcohol       0.835839  ...                      1.000000
'''


''' 상관 계수 시각화 '''
# 컬럼이름이 너무 길면 불편하기 때문에 줄여서 작성
''' 상관 관계가 높을수록 밝다 '''
# cols_view = ['bear', 'spirit', 'wine', 'alchol']
# sns.set(font_scale=1.5)
# hm = sns.heatmap(corr.values,
#                  cbar=True,
#                  annot=True,
#                  square=True,
#                  fmt='.2f',
#                  annot_kws={'size': 15},
#                  yticklabels=cols_view,
#                  xticklabels=cols_view)
#
# plt.show()


''' seaborn 에는 또다른 상관관계를 볼 수 있는 pairplot 이 있다 . '''
# sns.pairplot(drinks[cols], height=2.5)
# plt.show()


''' 대륙별 데이터 개수 비율 시각화 '''
label = drinks['continent'].value_counts().index.tolist()
print(label)
print()
''' result 
['AF', 'EU', 'AS', 'OT', 'OC', 'SA']
'''

# 국가 개수
fracs = drinks['continent'].value_counts().values.tolist()
''' 원형 그래프에서 중심 과의 거리 '''
explode = [0, 0, 0, 0.25, 0, 0]
print(fracs)
'''  result 
[53, 45, 44, 23, 16, 12]
'''
#
# plt.pie(fracs, explode=explode, labels=label, autopct='%.0f%%', shadow=True)
# plt.show()

''' 기술 통계 '''
result = drinks.groupby('continent').spirit_servings.agg(['mean', 'min', 'max', 'sum'])
print(result)
print()

''' 전체 평균보다 알콜을 많이 섭취하는 대륙을 조회 '''
# 알콜 평균
total_mean = drinks.total_litres_of_pure_alcohol.mean()

continent_mean = drinks.groupby('continent').total_litres_of_pure_alcohol.mean()

continent_over_mean = continent_mean[continent_mean >= total_mean]
print(continent_over_mean)


''' 맥주를 가장 많이 섭취하는 대륙 알아보기 '''
beer_continent = drinks.groupby('continent').beer_servings.mean().idxmax()
print(beer_continent)

''' 아프리카와 유럽의 맥주 소비량의 평균이 같은지 비교 -- 독립 표본 t 검정 '''
# 유의 수준을 얼마로 설정할건지 결정
''' 유의 수준을 5%로 설정하고 검정 
- 2개의 그룹이 서로 다른 그룹임으로 독립 표본 t 검정을 사용해야 한다 .
'''

# 1 . 아프리카 데이터를 찾아온다 .
africa = drinks.loc[drinks['continent'] == 'AF']
# 확인
print(africa.head())
print()

# 2. 유럽 데이터를 찾아온다
europe = drinks.loc[drinks['continent'] == 'EU']
# 확인
print(europe.head())
print()


# 3. 맥주 소비량만 확인
result = ss.ttest_ind(africa['beer_servings'],
                      europe['beer_servings'],
                      )

if result.pvalue >= 0.5:
    print('귀무 가설을 기각하지 못해서 아프리카와 유럽의 맥주 소비량은 다르지 않다 . ')
else:
    print('귀무 가설을 기각해서 아프리카와 유럽의 맥주 소비량은 다르다. ')

# 4. 분산이 같다고 가정
result = ss.ttest_ind(africa['beer_servings'],
                      europe['beer_servings'],
                      equal_var=False)

if result.pvalue >= 0.5:
    print('귀무 가설을 기각하지 못해서 아프리카와 유럽의 맥주 소비량은 다르지 않다 . ')
else:
    print('귀무 가설을 기각해서 아프리카와 유럽의 맥주 소비량은 다르다. ')


''' 등분산 검정 '''
# 2 개의 데이터 집합의 분산이 같은지 확인



