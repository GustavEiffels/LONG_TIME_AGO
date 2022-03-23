# DummyClassifier
import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import  accuracy_score
from sklearn.datasets import load_digits
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import confusion_matrix
from sklearn.decomposition import PCA
from sklearn.mixture import GaussianMixture
import pandas as pd
from sklearn.model_selection import StratifiedShuffleSplit
import matplotlib.image as mpimg
from pandas.plotting import scatter_matrix
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import OrdinalEncoder
from sklearn.preprocessing import OneHotEncoder
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
#산포도 출력
import platform
from matplotlib import font_manager, rc
import matplotlib
#매킨토시의 경우
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
#윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)
#우분투 리눅스의 경우
else:
    font_name = '/usr/share/fonts/truetype/nanum/NanumMyeongjo.ttf'
    rc('font', family=font_name)
#음수 출력
matplotlib.rcParams['axes.unicode_minus'] = False
# 붓꽃 데이터의 분류 모델 생성

import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import  accuracy_score
from sklearn.datasets import load_digits
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import confusion_matrix
from sklearn.decomposition import PCA
from sklearn.mixture import GaussianMixture
import pandas as pd
from sklearn.model_selection import StratifiedShuffleSplit
import matplotlib.image as mpimg
from pandas.plotting import scatter_matrix
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import OrdinalEncoder
from sklearn.preprocessing import OneHotEncoder
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
#산포도 출력
import platform
from matplotlib import font_manager, rc
import matplotlib
#매킨토시의 경우
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
#윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)
#우분투 리눅스의 경우
else:
    font_name = '/usr/share/fonts/truetype/nanum/NanumMyeongjo.ttf'
    rc('font', family=font_name)
#음수 출력
matplotlib.rcParams['axes.unicode_minus'] = False


## 데이터 가져오기
from sklearn.datasets import load_iris

iris = load_iris()

'''독립 변수'''
features = iris.data

''' 종속 변수 '''
target = iris.target

''' 훈련 데이터와 테스트 데이터 분리 '''
from sklearn.model_selection import train_test_split

features_train, features_test, target_train, target_test = train_test_split(features, target, test_size=0.2, random_state= 42)

print(f'features.shape : {features.shape}')
print()
print(f'features.train.shape : {features_train.shape}')
print()


# DummyClassifier 모델 생성
'''DummyClassifier 모델 생성 '''
from sklearn.dummy import DummyClassifier
print("DummyClassifier 모델 생성")

dummy = DummyClassifier(strategy='uniform', random_state=42)
dummy.fit(features_train, target_train)

''' 정확도 --> score'''
print('정확도 = score')
print(dummy.score(features_train, target_train))
print(dummy.score(features_test, target_test))
print()
'''  정확도가 낮아서 잘 사용하지 않는다 '''



# KNN

# model 을 생성

from sklearn.neighbors import KNeighborsClassifier


knn = KNeighborsClassifier(n_neighbors=5)


# loan_200.csv 파일을 읽어서 payment_inc_ratio 와 dti를 이용 outcome 을 예측하기
loan_200 = pd.read_csv('./data/loan200.csv')

''' 훈련에 사용할 feature 와 target을 분리 '''
X = loan_200.loc[1:, ['payment_inc_ratio', 'dti']]
y = loan_200.loc[1:, 'outcome']

''' 예측을 사용할 데이터 '''
newloan = loan_200.loc[0:0, ['payment_inc_ratio', 'dti']]

''' 훈련 '''
knn.fit(X, y)

''' 예측 '''
print(knn.predict(newloan))
print()

''' 확률 확인 '''
print(" 확률 확인 ")
print(knn.predict_proba(newloan))
print()