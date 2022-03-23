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
# get_ipython().run_line_magic('precision', '3') # 왜 안먹음??
import seaborn as sns
import scipy as sp
from scipy import stats

# 사이킷런 ≥0.20 필수
import sklearn
assert sklearn.__version__ >= "0.20"

# 노트북 실행 결과를 동일하게 유지하기 위해
np.random.seed(42)

from sklearn.datasets import fetch_openml

mnist = fetch_openml('mnist_784', version=1, as_frame=False)
mnist.keys()

X, y = mnist['data'], mnist['target']
print(X.shape) # 70000 개의 행과 784 개의 열로 구성
print(y.shape) # 70000 개의 행과 1 개의 열로 구성


# 1개 데이터 가져오기
some_digit = X[0]
# 이차원 배열로 변환
some_digit_image = some_digit.reshape(28,28)
# some_digit_image = some_digit.reshape(28,-1) # 결과는 같음
print(some_digit_image.shape)

plt.imshow(some_digit_image, cmap=mpl.cm.binary)
plt.axis('off')
save_fig('some_digit_plot')
plt.show()

y[0]

y = y.astype(np.uint8)

# 훈련 세트 와 테스트 세트로 분리
X_train, X_test, y_train, y_test = X[:60000], X[60000:], y[:60000], y[60000:]

# 이진 분류를 위한 타겟 생성 - 5 와 그렇지 않은 데이터로 분리
y_train_5 = (y_train == 5)
y_test_5 = (y_test == 5)
# print(y_train_5)

from sklearn.linear_model import SGDClassifier
sgd_clf = SGDClassifier(max_iter=1000, tol=1e-3, random_state=42)
sgd_clf.fit(X_train, y_train_5)

sgd_clf.predict([some_digit])

# 3겹 교차 검증
from sklearn.model_selection import cross_val_score

cross_val_score(sgd_clf, X_train, y_train_5, cv=3, scoring='accuracy')

from sklearn.model_selection import cross_val_predict
from sklearn.metrics import confusion_matrix

# 예측 값을 생
y_train_pred = cross_val_predict(sgd_clf, X_train, y_train_5, cv=3)

# 오차 행렬을 생성
confusion_matrix(y_train_5, y_train_pred)

from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

# 실제 레이블과 예측한 레이블을 대입
print('정확도 : ', accuracy_score(y_train_5, y_train_pred))
print('정밀도 : ', precision_score(y_train_5, y_train_pred ))
print('재현율 : ', recall_score(y_train_5, y_train_pred))
print('f1-score : ', f1_score(y_train_5, y_train_pred))

y_score = sgd_clf.decision_function([some_digit])
print(y_score)
# 결정 점수 확인

# 임계값 확인
y_scores = cross_val_predict(sgd_clf, X_train, y_train_5, cv=3, method='decision_function')
print(y_scores)

from sklearn.metrics import precision_recall_curve
precisions, recalls, thresholds = precision_recall_curve(y_train_5, y_scores)

print(precisions)
print(recalls)
print(thresholds)

# 정밀도가 0.9 가 넘는 인덱스를 구래서 recall 값과  threshold 값 찾기
precision_90_precision = precisions[np.argmax(precisions >= 0.90)]
recall_90_precision = recalls[np.argmax(precisions >= 0.90)]
threshold_90_precision = thresholds[np.argmax(precisions >= 0.90)]

print(precision_90_precision)
print(recall_90_precision)
print(threshold_90_precision)

y_train_pred_90 = (y_scores >= threshold_90_precision)
# 이전에 임계값을 설정하지 않은 결과
print(y_train_pred)
# 임계값을 정밀도 0.9로 설정한 결과
print(y_train_pred_90)

''' 기본 예측의 정밀도 와 수정한 예측의 정밀도 및 재현율 '''
print(f' 기본 예측 : {precision_score(y_train_5,y_train_pred)}')
print(f'precision_score(y_train_5, y_train_pred_90) : {precision_score(y_train_5, y_train_pred_90)}')
print()

print(f' 기본 예측 : {recall_score(y_train_5,y_train_pred)}')
print(f'recall_score(y_train_5, y_train_pred_90) : {recall_score(y_train_5, y_train_pred_90)}')
print()



# ROC Score

from sklearn.metrics import roc_auc_score
print()
print("ROC SCORE ----------------------------")
print(f'roc_auc_score(y_train_5, y_scores) : {roc_auc_score(y_train_5, y_scores)}')



''' 다중 분류 ----------'''
from sklearn.svm import SVC

''' 분류기 생성 '''
svm_clf = SVC(gamma="auto", random_state=42)

''' 모델 훈련 '''
svm_clf.fit(X_train[:100], y_train[:100])

''' 예측 '''
svm_clf.predict([some_digit])

''' 다중 분류 훈련의 결정 점수 확인'''
some_digit_scores = svm_clf.decision_function([some_digit])
print(some_digit)
print()


''' 결과 확인 '''

''' 결정 점수가 가장 높은 인덱스 '''
print()
print(np.argmax(some_digit_scores))
print()

''' target 확인 '''
print(svm_clf.classes)
print()

''' index 에 해당하는 타겟 '''
print(svm_clf.classes(np.argmax(some_digit_scores)))
print()


from sklearn.multiclass import OneVsRestClassifier

''' 분류기 생성 '''
ovr_clf = OneVsRestClassifier(SVC(gamma="auto", random_state=42))

''' 모델 훈련 '''
ovr_clf.fit(X_train[:100], y_train[:100])

''' 예측  '''
ovr_clf.predict([some_digit])



''' SGD 분류기 사용  '''
''' 이전에 만들어 둔 분류기 사용 '''
sgd_clf.fit(X_train, y_train)

''' 예측 '''
print(f'sgd_clf.predict([some_digit]) : {sgd_clf.predict([some_digit])}')
print(f'sgd_clf.decision_function([some_digit]) : {sgd_clf.decision_function([some_digit])}')



'''교차 검증을 이용한 정확도 확인 '''
cross_val_score(sgd_clf, X_train, y_train, cv=3, scoring='accuracy')


''' Scaling 을 하고 교차 검증으로 확인 '''
from sklearn.preprocessing import StandardScaler

scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train.astype(np.float64))
cross_val_score(sgd_clf,X_train_scaled, y_train, cv= 3, scoring='accuracy')


''' 오차 행렬 '''
# 다중 분류 인 경우는 클래스 개수 x 클래스 개수로
# table 출력
y_train_pred = cross_val_score(sgd_clf, X_train_scaled, y_train, cv=3)
conf_matrix = confusion_matrix(y_train, y_train_pred)
print(f' conf_matrix : {conf_matrix}')

''' 오차 행렬 시각화 '''
plt.matshow(conf_matrix, cmap=plt.cm.gray)
plt.show()


''' 클래스 별 합계 구하기 '''
row_sums = conf_matrix.sum(axis=1, keepdims=True)
norm_conf_matrix = conf_matrix/row_sums

''' 주 대각선을 0 으로 채움 - 맞춘 영역을 0 으로 채움 '''
np.fill_diagonal(norm_conf_matrix, 0)


plt.matshow(norm_conf_matrix, cmap=plt.cm.gray)
plt.show()

''' 다중 레이블 생성 '''

''' 7 이상 인지 여부 레이블을 생성 '''
y_train_large = (y_train >= 7)

''' 홀수 여부 레이블을 생성 '''
y_train_odd = (y_train % 2 == 1)

''' 다중 레이블 생성 '''
y_multilabel = np.c_[y_train_large, y_train_odd]

''' 다중 레이블 분류를 지원하는 모델을 생성 '''
from sklearn.neighbors import KNeighborsClassifier

knn_clf = KNeighborsClassifier()
knn_clf.fit(X_train, y_multilabel)

'''예측'''
knn_clf.predict([some_digit])



''' 다중 레이블 평가 지표 '''
y_train_knn_pred = cross_val_predict(knn_clf, X_train, y_multilabel, cv=3)

'''

레이블에 동일한 가중치를 부여해서 평가 지표를 리턴 
average 에 weighted 를 설정하면 Sample에 비율만큼 가중치를 부여 
'''
f1_score(y_multilabel, y_train_knn_pred, average='macro')


loan300 = pd.read_csv('./data')