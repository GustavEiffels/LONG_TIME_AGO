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

# 파이썬 ≥3.5 필수
import sys
assert sys.version_info >= (3, 5)
# 공통 모듈 임포트
import numpy as np
import pandas as pd
import os
# 깔끔한 그래프 출력을 위해 %matplotlib inline
import matplotlib as mpl
import matplotlib.pyplot as plt
mpl.rc('axes', labelsize=14)
mpl.rc('xtick', labelsize=12)
mpl.rc('ytick', labelsize=12)

# 그림을 저장할 위치 - 현재 디렉토리에 images/classification
PROJECT_ROOT_DIR = "."
CHAPTER_ID = "classification"
IMAGES_PATH = os.path.join(PROJECT_ROOT_DIR, "images", CHAPTER_ID)

os.makedirs(IMAGES_PATH, exist_ok=True)

# matplotlib.pyplot 으로 출력한 이미지를 저장하기 위한 함수
def save_fig(fig_id, tight_layout=True, fig_extension="png", resolution=300):
    path = os.path.join(IMAGES_PATH, fig_id + "." + fig_extension)
    print("그림 저장:", fig_id)
    if tight_layout:
        plt.tight_layout()
        plt.savefig(path, format=fig_extension, dpi=resolution)

# 그래프 한글 처리
from matplotlib import font_manager, rc
import platform
path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)
# 그래프 음수 처리
mpl.rcParams['axes.unicode_minus'] = False
# Jupyter Notebook의 출력을 소수점 이하 3자리로 제한
# %precision 3
# DataFrame의 출력을 소수점 이하 3자리로 제한
pd.set_option('display.precision', 3) # 'display.percision' 라고 작성해야 정상적으로 동작 함
import seaborn as sns
import scipy as sp
from scipy import stats

# 사이킷런 ≥0.20 필수
import sklearn
assert sklearn.__version__ >= "0.20"

# 노트북 실행 결과를 동일하게 유지하기 위해
np.random.seed(42)



''' DATA LOAD'''
df = sns.load_dataset('titanic')
df.info()


''' ----------------------------- 결측치 처리  ----------------------------- '''
''' 열 삭제 '''
# deck 열은 결측치가 너무 많아서 , 열을 삭제
# embark_town 은 중복된 열 이라서 열을 삭제

rdf = df.drop(['deck', 'embark_town'], axis=1)
print(rdf.info())
print()
print(rdf.head())
print()

''' ----------------------------- 결측치 처리 행 처리  ----------------------------- '''
''' 행 삭제 '''
# age 의 열의 결측치는 아주 많지 않아서 결측치에 해당하는 행을 삭제
rdf = rdf.dropna(subset=['age'], how='any', axis=0)
print(rdf.info())
print()
print(rdf.head())
print()


''' ----------------------------- 결측치 처리 대체 처리  ----------------------------- '''
''' 가장 많이 출현한 값으로 대체  '''
most_freq = rdf['embarked'].value_counts(dropna=True).idxmax()
rdf['embarked'].fillna(most_freq, inplace=True)

rdf = rdf.dropna(subset=['age'], how='any', axis=0)
print(rdf.info())
print()
print(rdf.head())
print()

'''
- feature ( 독립 변수, 설명 변수 ) 선택 

- 지도 학습이나 준 지도 학습에서는 feature 와 target을 구별해서 작업을 수행 
- 비지도 학습은 타겟은 없다 
- 강화 학습은 타겟 대신에 보상을 준다 
- 보상은 현재 결정되는 것이 아니고 차후에 결정이 된다.

'''


''' ------------ feature 선택 ------------  '''
print(''' ------------ feature 선택 ------------  ''')
ndf = rdf[['survived', 'pclass', 'sex', 'age', 'sibsp', 'parch', 'embarked']]
print(ndf.info())


'''# <범주형 데이터 OneHotEncoding>
- OneHotEncoding : 범주형 (category) 데이터를 클래스 종류 만큼의 열로 생성 
- 자신의 클래스에 해당하는 열만 1로 만드는 작업 
- 하는 이유 : 숫자 형태로 변환되어야만, 거리 계산을 할 수 있기 때문 
- sex ,embarked를 oneHotEncoding 
- pandas 의 dummies 함수나 sklearn의 함수를 이용해도 된다 '''



''' ---------- sex One Hot Encoding ---------- '''
print(''' ---------- sex One Hot Encoding ---------- ''')
onehot_sex = pd.get_dummies(ndf['sex'])
ndf = pd.concat([ndf, onehot_sex], axis=1)
print()

print(''' ---------- embarked Hot Encoding ---------- ''')
onehot_embarked = pd.get_dummies(ndf['embarked'])
ndf = pd.concat([ndf, onehot_embarked], axis=1)

''' OneHotEncoding 하기 전의 열 제거  '''
ndf.drop(['sex', 'embarked'], axis=1 , inplace=True)
print(ndf.info())


## Feature 와 Target 의 분리

''' Feature 와 Target의 분리 '''
print(''' Feature 와 Target의 분리 ''')
print()

''' feature 은 2 차원 배열 구조 '''
''' target 은 1 차원 배열 구조 '''
X = ndf[['pclass', 'age', 'sibsp', 'parch', 'female', 'male', 'C', 'Q', 'S']]
y = ndf['survived']




'''# feature 정규화 

- 정규화 하지 않으면 단위의 차이로 중요도가 달라지기 때문에
- 잘못된 모델을 생성하는 경우가 있다 
- Machine Learning 에서는 정규화 나 표준화가 중요하다 '''

''' ---------- feature 정규화 ---------- '''
print(''' ---------- feature 정규화 ---------- ''')
from sklearn import preprocessing

X = preprocessing.StandardScaler().fit(X).transform(X)
print(X[:5])
print()


''' 훈련 데이터와 검증 데이터 분리 '''
print(''' 훈련 데이터와 검증 데이터 분리 ''')
# 7 : 3
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

print(f'X_train.Shape : X_test.Shape')
print(f'{X_train.shape} : {X_test.shape}')
print()

print(f'y_train.Shape : y_test.Shape')
print(f'{y_train.shape} : {y_test.shape}')
print()


# 모델 생성 및 훈련


''' ---------- 모델 생성 및 훈련  ---------- '''
print(''' ---------- 모델 생성 및 훈련  ---------- ''')
print()

from sklearn.neighbors import KNeighborsClassifier

knn = KNeighborsClassifier(n_neighbors=5)

knn.fit(X_train, y_train)


# 예측


''' ---------- 예 측 ----------  '''
print(''' ---------- 예 측 ----------  ''')
y_hat = knn.predict(X_test)
print(f'y_hat[0:10] : \t\n {y_hat[0:10]}')
print()
print(f'y_test.values[0:10] : \t\n {y_test.values[0:10]}')
print()

print(" 데이터 10 개 가지고 판별하기 힘들다 !")
print()



''' ---------  평가 지표 확인 --------- '''
print(''' ---------  평가 지표 확인 --------- ''')
from sklearn import metrics

# 오차 행렬
knn_matrix = metrics.confusion_matrix(y_test, y_hat)
print(knn_matrix)
print()

# 평가 지표 보고서
knn_report = metrics.classification_report(y_test, y_hat)
print(knn_report)
print()