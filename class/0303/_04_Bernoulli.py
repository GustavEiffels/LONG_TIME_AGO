import numpy as np
import pandas as pd
import seaborn as sns
from scipy import stats as sp

# 시각화 패키지
import matplotlib.pyplot as plt

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


''' 1 이 나올 확률이 0.6 인 베르누이 분포 객체 생성 '''
rv = sp.bernoulli(0.6)

''' 표본 생성 '''
# 표본을 list  로 생성
xx = [0, 1]
plt.bar(xx, rv.pmf(xx))
plt.xticks([0, 1], ['x=0', 'x=1'])
plt.title(' 베르누이 분포의 확률 질량 함수 ')
plt.show()


''' Sample 생성 '''
x = rv.rvs(100, random_state=42)
print(x)

sns.countplot(x)
plt.show()


''' 이항 분포  '''
N = 100
mu = 0.5
rv = sp.binom(N, mu)
xx = np.arange(N+1)
result = rv.pmf(xx)

# 1이 나올 확률 확인
print(result)

plt.bar(xx, result, align='center')
plt.show()

''''''
x = rv.rvs(100)
sns.countplot(x=x)
plt.show()
