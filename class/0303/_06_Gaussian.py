import numpy as np
import scipy.stats as sp
import matplotlib.pyplot as plt

''' 정규 분포의 모수 ( 평균, 표준 폊차 ) 생성 '''
mu = 0
std = 1

''' 정규 분포 객체 생성 '''
rv = sp.norm(mu, std)

''' 간격 생성 '''
xx = np.linspace(-5, 5, 100)

''' 확률 밀도 함수 '''
result = rv.pdf(xx)
print(result)
plt.plot(xx, result)
plt.show()

''' sampling '''
# 평균이 0
print(" Sampling ")
x = rv.rvs(100)
print(x)


''' 누적 분포 cdf '''
# 단조 감소 그래프를 그린다
result = rv.cdf(xx)
plt.plot(xx, result)
plt.show()
