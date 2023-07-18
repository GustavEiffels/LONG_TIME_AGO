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
PROJECT_ROOT_DIR = "images"
CHAPTER_ID = "classification"
IMAGES_PATH = os.path.join(PROJECT_ROOT_DIR, "images/images", CHAPTER_ID)

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




''' DATA 가져오기 '''
print(''' DATA 가져오기 ''')
print()

import seaborn as sns
''' seaborn의 데이터는 항상 DataFrame 이다 
scikit_learn 과 가장 다른점 중 하나 
'''
df = sns.load_dataset('titanic')
df.info()


''' ----------------------------- 결측치 처리 열 처리  ----------------------------- '''
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

''' ------------ feature 선택 ------------  '''
print(''' ------------ feature 선택 ------------  ''')
ndf = rdf[['survived', 'pclass', 'sex', 'age', 'sibsp', 'parch', 'embarked']]
print(ndf.info())


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




''' 속성 선택 '''
print(''' 속성 선택 ''')
print()

X = ndf[['pclass', 'age', 'sibsp', 'parch', 'female', 'male', 'C', 'Q', 'S']]

y = ndf['survived']

''' 분류 나 회귀를 할 때는, 되도록이면 속성을 정규화 해주는 것이 좋다 '''
from sklearn import preprocessing
X = preprocessing.StandardScaler().fit(X).transform(X)

''' 훈련데이터와 검증 데이터 분할 '''
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

''' 확인 '''
print(f'X_train.shape : \t\n {X_train.shape}')
print()
print(f'X_test.shape : \t\n {X_test.shape}')
print()



''' Model을 생성하고 훈련 '''
from sklearn.tree import DecisionTreeClassifier


# max_depth=5 --> 깊이 설정
# criterion='entropy' ---> 훈련은 entropy 로
tree_model = DecisionTreeClassifier(criterion='entropy', max_depth=5)

''' 훈련 '''
tree_model.fit(X_train, y_train)

''' 예측 '''
y_hat = tree_model.predict(X_test)



''' 확인 '''
print(''' 확인 ''')
print()
print(y_hat[0:10])
print(y_test.values[0:10])

''''전체 데이터를 확인하는 것은 아니기 때문에 자주 사용하는 방식은 아니다'''



''' 오차 행렬을 출력 '''
print(''' 오차 행렬을 출력 ''')
print()
from sklearn import metrics
tree_matrix = metrics.confusion_matrix(y_test, y_hat)
print(tree_matrix)


''' 평가 지표 확인 '''
print(''' 평가 지표 확인 ''')
print()
tree_report = metrics.classification_report(y_test, y_hat)
print(tree_report)



''' Feature의 중요도 '''
print(''' Feature의 중요도 ''')
print()

n_features = X.data.shape[1]

plt.barh(np.arange(n_features), tree_model.feature_importances_, align='center')
plt.yticks(np.arange(n_features), ['plclass', 'age', 'sibsp', 'parch', 'female', 'male', 'C', 'Q', 'S'])
plt.xlabel('특성 중요도')
plt.ylabel('특성')
plt.ylim(-1, n_features)
plt.show()



''' HyperParameter 튜닝 '''
from sklearn.model_selection import GridSearchCV
print(''' HyperParameter 튜닝 ''')
print()

''' Hyper Parameter 목록 생성 '''
params = {
    'max_depth' : [8, 12, 16, 20],
    'min_samples_split' : [16, 24]
}


''' 5겹 교차 검증 이용 '''
grid_cv = GridSearchCV(tree_model, param_grid=params, scoring='accuracy',cv=5)

grid_cv.fit(X_train,y_train)
print(f' 최적의 parameter : \t\n {grid_cv.best_params_}')