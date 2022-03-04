import numpy as np
import scipy.stats as ss
import seaborn as sns
import matplotlib.pyplot as plt

''' 베타 분포를 이용한 샘플 데이터 생성 - 모수는 15 와 12 '''
x = ss.beta(15, 12).rvs(10000)
print(x)
# 데이터가 10000 개, 형식 ndarray

''' 데이터의 히스토그램 출력 '''
sns.distplot(x, kde=False, norm_hist=False)
plt.title('Histogram of Sample Data')
plt.show()

''' beta  분포의 모수를 구해주는 함수를 생성 '''
def estimate_beta(x):
    x_bar = x.mean()
    s2 = x.var()

    # 1차 모멘트 값
    a = x_bar * (x_bar * (1 - x_bar) / s2-1)

    # 2차 모멘트 값
    b = (1-x_bar) * (x_bar * (1-x_bar)/s2-1)

    return a, b


# sample 데이터의 모수를 출력
params = estimate_beta(x)
print('sample 데이터의 모수를 출력')
print(params)

# 샘플 데이터를 생성할 때 설정한 모수와 거의 유사하다.
''' x = ss.beta(15, 12).rvs(10000) '''

''' 분포의 모양을 확인하는 방법도 있다. '''
# 1. 직접 그려보기

# 2. seaborn 이 직접 제공해주는 것을 사용


''' histogram 과 확률 분포 모형을 비교 '''
# 1 . histogram 을 그린다

xx = np.linspace(0, 1, 1000)
sns.distplot(x, kde=False, norm_hist=True)
plt.title("Histogram of Sample Data !!!")
plt.show()

# 2. beta 분포 곡선 그리기
'''  params = estimate_beta(x) '''
''' xx = np.linspace(0, 1, 1000) '''
plt.plot(xx, ss.beta(params[0], params[1]).pdf(xx))
plt.title('beta beta distribution curve')
plt.show()




xx = np.linspace(0, 1, 1000)
sns.distplot(x, kde=False, norm_hist=True)
plt.plot(xx, ss.beta(params[0], params[1]).pdf(xx))
plt.title('beta beta distribution curve And  Histogram of Sample Data !!!')
plt.show()

# sample 데이터의 히스토그램과 확률 분포 모형을 비교

xx = np.linspace(0, 1, 1000)
sns.distplot(x, kde=False, norm_hist=True, fit=ss.beta)
plt.title('compare with Sample Data and beta Histogram by Using seaborn')
plt.show()