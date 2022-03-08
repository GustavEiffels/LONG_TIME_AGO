import numpy as np
import pandas as pd
import statsmodels.api as sm

# 시각화 패키지
import matplotlib.pyplot as plt
import seaborn as sns

# 통계 관련 package
import scipy as sp
import scipy.stats as ss

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


import pandas_datareader.data as web
import datetime
import urllib.request

''' Sklearn 을 이용한 표본 추출 '''
from sklearn.model_selection import train_test_split



'''
태아의 머리 둘레 측정 데이터
4명의 관측자가 3명의 태아를 대상으로 측정
초음파로 태아의 머리 둘레측정 데이터가 재현성이 있는지를 조사
'''
''' Data Load '''
inFile = 'altman_12_6.txt'
url_base = 'https://raw.githubusercontent.com/thomas-haslwanter/statsintro_python/master/ipynb/Data/data_altman/'
url = url_base + inFile
data = np.genfromtxt(urllib.request.urlopen(url), delimiter=',')
# data 확인
print(data)

''' DATA FRAME 으로 변환 '''
df = pd.DataFrame(data, columns=['head_size', 'fetus', 'observer'])
print(df)
print()

''' boxplot '''
df.boxplot(column='head_size', by='fetus', grid=False)
plt.show()

''' 이원 분산 분석 '''
from statsmodels.formula.api import ols

lm = ols('head_size ~C(fetus) + C(observer) + C(fetus) : C(observer)',
         df).fit()
print(sm.stats.anova_lm(lm))

# p-value 값이 작아서 분산의 차이가 무의미 하다