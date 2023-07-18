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

# 데이터 가져오기
df = pd.read_csv('../python_statistics-main 2/coffee_dataset.csv')
df.info()

''' 데이터 200 개 sampling '''
df_sample = df.sample(200)
df_sample.info()

''' 커피를 마시는 사람과 그렇지 않은 사람의 키의 평균을 bootstrapping '''
# 10000 번을 수행

iterationNum = 10000

diffHeightList = []

for _ in range(iterationNum):
    # Sample Data에서 복원 추출
    bootSample = df_sample.sample(200, replace=True)

    # 커피를 마시지 않는 사람의 평균 키
    nonCoffeeHeightMean = \
        bootSample[bootSample['drinks_coffee'] == False].height.mean()

    # 커피를 마시는 사람의 평군 키
    coffeeHeightMean = \
        bootSample[bootSample['drinks_coffee'] == True].height.mean()

    # 키의 평균 차를 list 에 저장
    diff = nonCoffeeHeightMean - coffeeHeightMean
    diffHeightList.append(diff)


''' 신뢰수준은 99% 로 구간을 생성 '''
# 신뢰구간
''' 0.5 , 99.5 로 생성  '''
print(np.percentile(diffHeightList, 0.5), np.percentile(diffHeightList, 99.5))
