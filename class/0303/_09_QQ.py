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



''' Sample data '''
x = np.random.rand(100)
print(x)

''' Q Q Plot 그려보기 '''
plt.figure(figsize=(8, 8))
sp.probplot(x, plot=plt)
plt.show()