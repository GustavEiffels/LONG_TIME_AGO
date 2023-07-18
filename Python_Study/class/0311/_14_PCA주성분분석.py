# 선형대수
import numpy as np

import pandas as pd

import matplotlib.pyplot as plt


# numpy 와 호환되는 자료 구조 ( Series, DataFrame )
from matplotlib import font_manager, rc

# 운영 체제
import platform

# 시각화
import seaborn as sns

# 통계
import statsmodels.api as sm
import statsmodels.formula.api as swf
from statsmodels.stats import power

# 데이터 전처리 & 머신러닝
import sklearn

import matplotlib

path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)

matplotlib.rcParams['axes.unicode_minus'] = False

plt.rc("font", size=18)  # 그림의 폰트 크기를 18로 고정
gray = {"facecolor": "gray"}
black = {"facecolor": "black"}
red = {"facecolor": "red"}
green = {"facecolor": "green"}
blue = {"facecolor": "blue"}



''' 붖꼬의 성분 간의 종속성 확인 '''
from sklearn.datasets import load_iris
iris = load_iris()

''' 10개 데이터의 꽃받침의 길이와 꽃받침 폭만 선택 '''
X = iris.data[:10, :2]
print(f' X : \t\n {X}')
print()
''' result
X : 	
 [[5.1 3.5]
 [4.9 3. ]
 [4.7 3.2]
 [4.6 3.1]
 [5.  3.6]
 [5.4 3.9]
 [4.6 3.4]
 [5.  3.4]
 [4.4 2.9]
 [4.9 3.1]] 
'''

''' 읽어온 데이터를 시각화 '''
plt.figure(figsize=(8, 6))

plt.plot(X.T, 'o:')
plt.xticks(range(2), ['꽃받침 길이', '꽃받침 폭'])
plt.xlim(-0.5, 2)
plt.ylim(2.5, 6)
plt.title('붓꽃 크기 특성')
plt.legend(['[표본{}]'.format(i+1) for i in range(10)])
plt.show()

# 시각화 Scatter 사용

plt.figure(figsize=(8, 8))
ax = sns.scatterplot(0,
                     1,
                     data=pd.DataFrame(X),
                     s=100,
                     color='.2',
                     marker='s')
plt.xlabel('꽃받침의 길이')
plt.ylabel('꽃받침 폭')
plt.title('붖꽃의  크기 특성 ( 2차원 표시 )')
plt.show()