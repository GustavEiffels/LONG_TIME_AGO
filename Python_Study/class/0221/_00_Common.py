import numpy as np
'''데이터 분석을 위한 자료구조 와 선형 대수'''


''' 어느정도 전처리 및 시각화'''
import pandas as pd

''' 시각화 package '''
import matplotlib.pyplot as plt

''' 시각화 할 때 한글을 출력할 수 있도록 하기 위해서'''
import platform
from matplotlib import font_manager, rc

# For Printing Hangle // BMDoHyeon-OTF // AppleGothic
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

''' 음수를 표현하기 위해서 '''
plt.rcParams['axes.unicode_minus'] = False


'''고급 시각화 와 기본 데이터 제공 '''
import seaborn as sns

'''데이터 전처리 와 machine learning 알고리즘 제공 '''
import sklearn
