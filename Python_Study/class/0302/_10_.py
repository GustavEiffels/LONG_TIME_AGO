import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns
import platform
from matplotlib import font_manager, rc
import scipy as sp

if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# 시각화에서 음수를 표현하기 위한 설정
import matplotlib

matplotlib.rcParams['axes.unicode_minus'] = False
x = np.random.normal(size = 1000)

#잡음 추가
x = np.hstack([x, 5 * np.ones(50)])

bins = np.linspace(-6, 6, 49)
ns, _ = np.histogram(x, bins = bins)
print(ns)

sample_mean = np.mean(x)
sample_median = np.median(x)
mode_index = np.argmax(ns)
sample_mode = 0.5 * (bins[mode_index] + bins[mode_index + 1])

print("평균:", sample_mean)
print("중앙:", sample_median)
print("최빈:", sample_mode)

#분포를 시각화
sns.distplot(x, bins = bins)

#평균, 중앙, 최빈 값을 표시
plt.axvline(sample_mean, c='k', ls=':', label='표본 평균')
plt.axvline(sample_median, c='k', ls='--', label='표본 중앙값')
plt.axvline(sample_mode, c='k', ls='-', label='표본 최빈값')

plt.legend()

plt.show()

#왜도 와 첨도 출력
print("왜도:", sp.stats.skew(x))
# 왜도 - 양수면 오른쪽에 데이터가 많고 음수면 왼쪽에 데이터가 많음
print("첨도:", sp.stats.kurtosis(x))
# 첨도 - 3이상이면 중앙에 데이터가 많음

#moment 함수를 이용해서 확인
print(sp.stats.moment(x, 1)) #평균 과 유사
print(sp.stats.moment(x, 2)) #분산 과 유사
print(sp.stats.moment(x, 3)) #왜도 와 유사
print(sp.stats.moment(x, 4)) #첨도 와 유사