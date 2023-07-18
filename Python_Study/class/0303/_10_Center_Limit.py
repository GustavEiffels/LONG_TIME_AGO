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

''' 중심 극한 정리 '''
np.random.seed(42)
xx = np.linspace(-2, 2, 100)
plt.figure(figsize=(6, 9))


# 5000 개 중에서 1번 2번 10번 순서대로 뽑는다
for i, N in enumerate([1, 2, 10]):
    print(i, ' : ', N)

    X = np.random.rand(5000, N)

    Xbar = (X.mean(axis=1) - 0.5) * np.sqrt(12*N)
    ax = plt.subplot(3, 2, 2*i + 1)

    # 각 값의 histogram을 생성
    sns.distplot(Xbar, bins=10, kde=False, norm_hist=True)

    ''' qq plot 을 정의 '''
    plt.xlim(-5, 5)
    plt.yticks([])
    ax.set_title("N = {0}".format(N))
    plt.subplot(3, 2, 2*i +2)

    ''' QQ PLOT 출력 '''
    sp.probplot(Xbar, plot=plt)

plt.show()