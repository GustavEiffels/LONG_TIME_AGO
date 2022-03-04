import numpy as np
import pandas as pd

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



''' 누적 분포 함수 ( CDF )'''
# 일정한 간격으로 0 ~ 360 까지의 데이터를 생성
t = np.linspace(-100, 500, 100)
print('t ------')
print(t)
print()
F = t / 360
print('F ------')
print(F)
print()

F[t < 0] = 0
F[t > 360] = 1
print(F)

''' 누적 분포 함수 '''
# 시작점에서 각 확률들을 합산해서 계산해주는 함수
plt.plot(t, F)

plt.ylim(-0.1, 1.1)
plt.xlim(-100, 500)
plt.xticks([0, 180, 360])
plt.show()


''' 데이터를 미분 '''
p = np.gradient(F, 600/1000)

# 구간들의 확률이 거의 비슷하다
plt.plot(t, p)
plt.ylim(-0.0001, p.max()*1.1)
plt.xticks([0, 180, 360])
plt.show()