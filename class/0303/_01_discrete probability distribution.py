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




# 표본 생성
x = np.arange(1, 7)

# 각 표본의 확률 생성
y = np.array([0.1, 0.1, 0.2, 0.1, 0.1, 0.4])

''' 확률 질량 함수 '''
plt.stem(x, y)
plt.title(' 주사위 확률 질량 함수 ')
plt.xlabel(' 주사위 눈 ')
plt.ylabel(' 눈의 확률 ')

plt.xlim(0.7)
plt.ylim(-0.01, 0.6)
plt.xticks(np.arange(6) + 1)
plt.show()
