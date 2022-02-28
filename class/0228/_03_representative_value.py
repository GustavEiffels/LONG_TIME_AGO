import numpy as np
import pandas as pd
from scipy import stats

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

tdata = pd.read_csv('../python_statistics-main/tdata.csv', encoding='ms949')
print(tdata)
print()


''' 산술 평균 '''
print(f" 산술 평균 : {tdata['성적'].mean()}")
print()
''' 중앙값 '''
print(f" 중앙값 : {tdata['성적'].median()}")
print()
'''  10% 잘라내기 // 이상치에 대한 영향을 덜 받기 위해서 '''
print(f" 절사 평균 : {stats.trim_mean(tdata['성적'], 0.1)}")