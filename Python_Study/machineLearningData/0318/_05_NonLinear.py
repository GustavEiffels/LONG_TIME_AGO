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


''' 다차원 데이터 -- 달 모양의 데이터 만들기 '''
print(''' --------------- 다차원 데이터 - 달 모양의 데이터 만들기 --------------- ''')
from sklearn.datasets import make_moons

''' Sample Data 생성 '''
X, y = make_moons(n_samples=100, noise=0.15, random_state=42)

def plot_dataset(X, y, axes):
    plt.plot(X[:, 0][y==0], X[:, 1][y==0], 'bs' )
    plt.plot(X[:, 0][y==1], X[:, 1][y==1], 'g^')
    plt.axis(axes)

    plt.grid(True, which='both')
    plt.xlabel(r'$x_1$', fontsize=20)
    plt.xlabel(r'$x_2$', fontsize=20, rotation = 0)

plot_dataset(X, y, [-1.5, 2.5, -1, 1.5])
plt.show()



''' 다차원 데이터 -- 달 모양의 데이터 만들기 '''
print(''' --------------- 다차원 데이터 - 달 모양의 데이터 만들기 --------------- ''')
from sklearn.datasets import make_moons

''' Sample Data 생성 '''
X, y = make_moons(n_samples=100, noise=0.01, random_state=42)

def plot_dataset(X, y, axes):
    plt.plot(X[:, 0][y==0], X[:, 1][y==0], 'bs' )
    plt.plot(X[:, 0][y==1], X[:, 1][y==1], 'g^')
    plt.axis(axes)

    plt.grid(True, which='both')
    plt.xlabel(r'$x_1$', fontsize=20)
    plt.xlabel(r'$x_2$', fontsize=20, rotation = 0)

plot_dataset(X, y, [-1.5, 2.5, -1, 1.5])
plt.show()


''' model 생성 및 훈련 '''
print(''' model 생성 및 훈련 ''')
from sklearn.pipeline import Pipeline
from sklearn.svm import LinearSVC
from sklearn.preprocessing import StandardScaler, PolynomialFeatures


polynomial_svm_clf = Pipeline([('scaler', StandardScaler()),
                    ('linear_svc', LinearSVC(C=1, loss='hinge', random_state=42),)])

polynomial_svm_clf.fit(X, y)


def plot_predictions(clf, axes):
    x0s = np.linspace(axes[0], axes[1], 100)
    x1s = np.linspace(axes[2], axes[3], 100)

    x0, x1 = np.meshgrid(x0s, x1s)
    X = np.c_[x0.ravel(), x1.ravel()]

    y_pred = clf.predict(X).reshape(x0.shape)
    y_decision = clf.decision_function(X).reshape(x0.shape)

    plt.contourf(x0, x1, y_pred, cmp=plt.cm.brg, alpha=0.2)
    plt.contourf(x0, x1, y_decision, cmp=plt.cm.brg, alpha=0.1)

plot_predictions(polynomial_svm_clf, [-1.5, 2.5, -1, 1.5])
plot_dataset(X, y, [-1.5, 2.5, -1, 1.5])
plt.show()


''' model 생성 및 훈련 '''

print(''' model 생성 및 훈련 ''')
from sklearn.pipeline import Pipeline
from sklearn.svm import LinearSVC
from sklearn.preprocessing import StandardScaler, PolynomialFeatures

''' Pipe Line  사용하는 이유 '''
# SVM 은 Scale 에 대한 영향이 크기 때문에
# PipeLine 을 통해 Scale 을 하기 위해서

polynomial_svm_clf = Pipeline([('scaler', StandardScaler()),
                    ('linear_svc', LinearSVC(C=1, loss='hinge', random_state=42),
                    ('poly_features',PolynomialFeatures(degree=3)))])

polynomial_svm_clf.fit(X, y)


def plot_predictions(clf, axes):
    x0s = np.linspace(axes[0], axes[1], 100)
    x1s = np.linspace(axes[2], axes[3], 100)

    x0, x1 = np.meshgrid(x0s, x1s)
    X = np.c_[x0.ravel(), x1.ravel()]

    y_pred = clf.predict(X).reshape(x0.shape)
    y_decision = clf.decision_function(X).reshape(x0.shape)

    plt.contourf(x0, x1, y_pred, cmp=plt.cm.brg, alpha=0.2)
    plt.contourf(x0, x1, y_decision, cmp=plt.cm.brg, alpha=0.1)

plot_predictions(polynomial_svm_clf, [-1.5, 2.5, -1, 1.5])
plot_dataset(X, y, [-1.5, 2.5, -1, 1.5])
plt.show()


from sklearn.svm import SVC

# kernel trick 을 이용한 버선형 SVM
# 결정 경계를 비선형으로 생성

poly_kernel_svm_slf = Pipeline([
    ('scaler',StandardScaler()),
    ('svm_clf', SVC(C=10, kernel='poly', degree=3))
])

poly_kernel_svm_slf.fit(X, y)

plot_predictions(poly_kernel_svm_slf,[-1.5, 2.5, -1, 1.5])
plot_dataset(X, y, [-1.5, 2.5, -1, 1.5])

plt.show()

''' RBF '''
from sklearn.svm import SVC

# Gaussian RBF 커널을 이용한 비선형 SVM
# 결정 경계를 비선형으로 생성

poly_kernel_svm_slf = Pipeline([
    ('scaler',StandardScaler()),
    ('svm_clf', SVC(C=10, kernel='rbf'))
])

poly_kernel_svm_slf.fit(X, y)

plot_predictions(poly_kernel_svm_slf,[-1.5, 2.5, -1, 1.5])
plot_dataset(X, y, [-1.5, 2.5, -1, 1.5])

plt.show()