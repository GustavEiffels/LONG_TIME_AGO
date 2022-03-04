import numpy as np
import pandas as pd
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


# 기댓값이 1 이고 표준 편차가 2인 정규 분포 객체를 생성
rv = sp.norm(loc=1, scale=2)
print(rv)


''' 확률 밀도 함수 '''

xx = np.linspace(-8, 8, 1000)
# 확률 밀도 함수를 이용해서 데이터를 생성
pdf = rv.pdf(xx)
# 밀도 함수 출력
plt.plot(xx, pdf)
plt.title(' 확률 밀도 함수 ')
plt.show()


''' 누적 분포 함수 '''

xx = np.linspace(-8, 8, 1000)
# 확률 밀도 함수를 이용해서 데이터를 생성
cdf = rv.cdf(xx)
# 밀도 함수 출력
plt.plot(xx, cdf)
plt.title(' 누적 분포 함수 ')
plt.show()

''' 3행 5 열 만큼 샘플 데이터 생성 '''
rv.rvs(size=(3, 5), random_state=42)
print(rv.rvs(size=(3, 5), random_state=42))