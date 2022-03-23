import sys

# Scikit_learn ≥0.20 필수
import sklearn

assert sklearn.__version__ >= "0.20"

# 공통 모듈 임포트
import numpy as np
import pandas as pd

import os
import platform

# 노트북 실행 결과를 동일하게 유지하기 위해
np.random.seed(42)

# 깔끔한 그래프 출력을 위해
import matplotlib as mpl
import matplotlib.pyplot as plt
from matplotlib import font_manager, rc

mpl.rc('axes', labelsize=14)
mpl.rc('xtick', labelsize=12)
mpl.rc('ytick', labelsize=12)

# 그림을 저장할 위치
PROJECT_ROOT_DIR = "."
CHAPTER_ID = "ensembles"
IMAGES_PATH = os.path.join(PROJECT_ROOT_DIR, "images", CHAPTER_ID)
os.makedirs(IMAGES_PATH, exist_ok=True)
# 한글 설정
# 매킨토시의 경우
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# 음수 표현을 위한 설정
mpl.rcParams['axes.unicode_minus'] = False


def save_fig(fig_id, tight_layout=True, fig_extension="png", resolution=300):
    path = os.path.join(IMAGES_PATH, fig_id + "." + fig_extension)
    print("그림 저장:", fig_id)
    if tight_layout:
        plt.tight_layout()
    plt.savefig(path, format=fig_extension, dpi=resolution)






''' 데이터 생성 '''
from sklearn.model_selection import train_test_split
from sklearn.datasets import make_moons

X, y = make_moons(n_samples=500, noise=0.30, random_state=42)
X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)



''' 모델 생성 및 훈련 '''
from sklearn.linear_model import LogisticRegression
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import VotingClassifier


''' 로지스틱 회귀 모델 생성 '''
log_clf = LogisticRegression(solver='lbfgs', random_state=42)

''' 선형 SVM 모델 생성 '''
svm_clf = SVC(gamma='scale', random_state=42)

''' Random Froest 모델 생성 '''
rnd_clf = RandomForestClassifier(n_estimators=100, random_state=42)

''' 투표 기반 분류기 '''
# 자신만의 모델은 없고
# 다른 모델을 받아서 사용
voting_clf = VotingClassifier(
    estimators=[('lr', log_clf), ('rf',rnd_clf), ('svc', svm_clf)],voting='hard')

''' 훈련 '''
voting_clf.fit(X_train, y_train)


''' 평가 지표 확인 '''
from sklearn.metrics import accuracy_score

for clf in (log_clf,svm_clf, rnd_clf, voting_clf):
    clf.fit(X_train, y_train)
    y_pred = clf.predict(X_test)
    print(clf.__class__.__name__, accuracy_score(y_test,y_pred))





''' 선형 SVM Model 만 변경 '''

''' 로지스틱 회귀 모델 생성 '''
log_clf = LogisticRegression(solver='lbfgs', random_state=42)

''' 선형 SVM 모델 생성 '''
svm_clf = SVC(gamma='scale', probability=True, random_state=42)

''' Random Froest 모델 생성 '''
rnd_clf = RandomForestClassifier(n_estimators=100, random_state=42)

''' 투표 기반 분류기 '''
# 자신만의 모델은 없고
# 다른 모델을 받아서 사용
voting_clf = VotingClassifier(
    estimators=[('lr', log_clf), ('rf',rnd_clf), ('svc', svm_clf)],voting='soft')

''' 훈련 '''
voting_clf.fit(X_train, y_train)



for clf in (log_clf,svm_clf, rnd_clf, voting_clf):
    clf.fit(X_train, y_train)
    y_pred = clf.predict(X_test)
    print(clf.__class__.__name__, accuracy_score(y_test,y_pred))




# Bagging Pasting

from sklearn.tree import DecisionTreeClassifier

tree_clf = DecisionTreeClassifier(random_state=42)
tree_clf.fit(X_train, y_train)
y_pred_tree = tree_clf.predict(X_test)

print(accuracy_score(y_test, y_pred_tree))


from sklearn.ensemble import BaggingClassifier

bag_clf = BaggingClassifier(DecisionTreeClassifier(), n_estimators=500, max_samples=100, bootstrap=True, random_state=42 )

bag_clf.fit(X_train, y_train)
y_pred_tree = bag_clf.predict(X_test)

print(accuracy_score(y_test, y_pred_tree))


bag_clf = BaggingClassifier(DecisionTreeClassifier(), n_estimators=500, max_samples=100, bootstrap=True, oob_score=True, random_state=42)

bag_clf.fit(X_train, y_train)

print(bag_clf.oob_score_)


bag_clf = BaggingClassifier(DecisionTreeClassifier(max_features='sqrt', max_leaf_nodes=16),
n_estimators=500, random_state=42)

bag_clf.fit(X_train, y_train)
y_pred = bag_clf.predict(X_test)


rnd_clf = RandomForestClassifier(max_leaf_nodes=16, n_estimators=500, random_state= 42 )

rnd_clf.fit(X_train, y_train)
y_pred_rf = rnd_clf.predict(X_test)

''' 결과 비교 '''

print(np.sum(y_pred == y_pred_rf)/ len(y_pred))


''' 특성 중요도 확인 '''
from sklearn.datasets import load_iris
iris = load_iris()

rnd_clf = RandomForestClassifier(n_estimators=500, random_state=42)

rnd_clf.fit(iris.data, iris.target)

for name,score in zip(iris.feature_names, rnd_clf.feature_importances_):
    print(name, " : ", score)