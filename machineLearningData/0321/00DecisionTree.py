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


''' Model 생성 및 훈련  '''
print(''' Model 생성 및 훈련  ''')
print()
from sklearn.datasets import load_iris
from sklearn.tree import DecisionTreeClassifier

''' sklearn 에서 제공하는 붓꽃 데이터 가져오기 '''
iris = load_iris()

''' feature 생성 - 꽃잎 길이 와 너비 '''
X = iris.data[:, 2:]

''' Target 생성 '''
y = iris.target

print("X.shape")
print(X.shape)
print()

print("y.shape")
print(y.shape)
print()



''' 모델 생성 '''
print(''' 모델 생성 ''')
print()

tree_clf = DecisionTreeClassifier(max_depth=2, random_state=42)
''' Random_State 를 사용한다는 말은 
전체 데이터 중 일부분을 사용한다는 말이다 !
'''

''' 훈련 '''
tree_clf.fit(X,y)



''' Tree Model 시각화 '''
print(''' Tree Model 시각화 ''')


from sklearn.tree import export_graphviz
import graphviz

''' Tree Model 시각화 파일 생성 '''
export_graphviz(tree_clf, out_file='iris_tree.dot',
feature_names=iris.feature_names[2:],
class_names=iris.target_names,
rounded=True,
filled=True)

''' 화면에 출력 '''
with open('iris_tree.dot') as f:
    dot_graph = f.read()

src = graphviz.Source(dot_graph)
print(src)
print()
src
plt.show()


''' 예측 및 확률 확인 '''
print(''' 예측 및 확률 확인 ''')
print()

''' 예측 '''
print(''' 예측 ''')
print(tree_clf.predict([[5, 1.5]]))
print()

''' 확률 추정 '''
print(''' 확률 추정 ''')
print(tree_clf.predict_proba([[5, 1.5]]))
print()