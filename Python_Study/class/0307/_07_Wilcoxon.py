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


''' Data Load '''
training_rel = pd.read_csv('../python_statistics-main 2/training_rel.csv')
toy_df = training_rel[:6].copy()
print(toy_df)
print()


''' 데이터의 차이 확인 '''

diff = toy_df['후'] - toy_df['전']
toy_df['차'] = diff
print(toy_df)
print()


''' 차이의 절대 값을 이용해서 순위를 생성 '''
rank = ss.rankdata(abs(diff).astype(int))
toy_df['순위'] = rank
print(toy_df)
print()

''' 차이가 음수 일 대 와 양수 일때의 순위합을 구함 '''
r_minus = np.sum((diff < 0) * rank)
r_plus = np.sum((diff > 0) * rank)

print(f' minus : {r_minus} / plus : {r_plus} ')
print()


''' 데이터에 변화가 발생한 경우에 순위 합을 확인 '''
toy_df['후'] = toy_df['전'] + np.arange(1, 7)
diff = toy_df['후'] - toy_df['전']

''' 차이의 절대값을 가지고 순위 구하기 '''
rank = ss.rankdata(abs(diff)).astype(int)

''' 차이가 음수 일 때 , 양수 일때의 순위 합을 구하기 '''
r_minus = np.sum((diff < 0)*rank)
r_plus = np.sum((diff > 0)*rank)

''' 음의 순위 합과 양의 순위 합이 차이가 많이 남 '''
print(f' minus : {r_minus} / plus : {r_plus} ')
print()
# 중앙값이 증가했을 가능성이 높다

''' 데이터에 변화가 발생한 경우에 순위 합을 확인 '''
toy_df['후'] = toy_df['전'] + [1, -2, 3, -4, 5, -6]
diff = toy_df['후'] - toy_df['전']

''' 차이의 절대값을 가지고 순위 구하기 '''
rank = ss.rankdata(abs(diff)).astype(int)

''' 차이가 음수 일 때 , 양수 일때의 순위 합을 구하기 '''
r_minus = np.sum((diff < 0)*rank)
r_plus = np.sum((diff > 0)*rank)

''' 음의 순위 합과 양의 순위 합이 차이가 거의 나지 않음  '''
print(f' minus : {r_minus} / plus : {r_plus} ')
print()
# 중앙값이 증가했을 가능성이 낮다