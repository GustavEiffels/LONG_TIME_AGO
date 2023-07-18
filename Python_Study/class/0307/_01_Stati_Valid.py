import random

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

''' 통계적 유의성 
유의확률 P -value : 계산해주는 사용자 정의 함수 
대부분의 경우는 이함수를 직접 생성하지는 않는다 '''

# 재표본 추출을 이용해서 새로 추출된 데이터의 평균의 차를 구해주는 함수
def perm_fun(x, nA, nB):
    # 2개의 시행 횟수를 전부 합침
    n = nA + nB

    idx_B = set(random.sample(range(n), nB))
    idx_A = set(range(n)) - idx_B

    return x.loc[idx_B].mean() - x.loc[idx_A].mean()


# 전환율의 차이를 계산
obs_pct_diff = 100 * (200/23739 - 182/22588)
print(' 전환율의 차이 : ', obs_pct_diff, '%')


# 부트스트래핑을 위한 작업
''' 전환되지 않은 경우 '''
conversion = [0] * 45945

''' 전환한 경우  '''
conversion.extend([1]*382)

# 앞의 list 를 Series 로 변환
conversion = pd.Series(conversion)

''' 여러번 Sampling 해서 평균의 차이를 계산 '''
perm_diffs = [100*perm_fun(conversion, 23739, 22588)
              for _ in range(1000)]


print(np.mean([diff > obs_pct_diff for diff in perm_diffs]))

