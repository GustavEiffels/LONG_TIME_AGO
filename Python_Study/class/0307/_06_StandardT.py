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


# 서로 다른 10명의 사람에게 수면제 1을 복용했을 때의 수면 증가 시간과
# 수면제 2를 복용했을 때, 수면 증가 시간이 같은지 비교
# 유의 확률 5%로 검정

x1 = np.array([0.7, -1.6, -0.2, -1.2, -0.1, 3.4, 3.7, 0.8, 0.0, 2.0])
x2 = np.array([1.9, 0.8, 1.1, 0.1, -0.1, 4.4, 5.5, 1/6, 4.6, 3.4])


''' 2개의 데이터의 분산 확인 '''
print(np.var(x1))
print()
print(np.var(x2))
print()

r = ss.ttest_rel(x1, x2)
print(r.pvalue)
print()

if r.pvalue >= 0.05:
    # 효과가 없다라는 말 !
    print('귀무가설을 기각할 수 없어 2개의 데이터의 평균은 같다 ')
else:
    print('귀무 가설을 기각해서 2개 데이터의 평균은 다르다 ')