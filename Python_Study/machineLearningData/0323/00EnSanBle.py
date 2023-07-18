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


'''================================================= MNIST  ================================================='''

''' MNIST 데이터 가져오기 '''
from sklearn.datasets import fetch_openml

mnist = fetch_openml('mnist_784', version=1)
mnist.target = mnist.target.astype(np.uint8)


''' 분류기 만들기 '''
from sklearn.ensemble import RandomForestClassifier

''' n_estimators : 분류기의 개수 '''
rnd_clf = RandomForestClassifier(n_estimators=100, random_state=42)
rnd_clf.fit(mnist.data, mnist.target)


''' 중요도를 위한 함수 생성 '''
def plot_digit(data):
    # 이미지 데이터를 출력하기 위해서 2차원으로 변환
    image = data.reshape(28, 28)
    plt.imshow(image, cmap=mpl.cm.hot, Interpolation='nearest')
    plt.axis('off')


''' 중요도 출력 '''
plot_digit(rnd_clf.feature_importances_)

''' Color bar 생성 '''
cbar = plt.colorbar(ticks= [rnd_clf.feature_importances_.min(),
                            rnd_clf.feature_importances_.max()])
cbar.ax.set_yticklabels(['중요하지 않음', '매우 중요'])
plt.show()


'''================================================= 7. 타이타닉 데이터를 이용한 Random Forest 생존자 예측  ================================================='''

### 1. 데이터 가져오기

# - pandas 와 seaborn 을 이용한 데이터틑 DataFrame --> info로 정보를 확인
#
# - numpy 나 sklearn 을 이용한 데이터는 ndarray 구조
#
# - sklearn 은 data, target , featurenmaes 속성에
# - 독립변수, 종속변수 ,  독립변수의 의미가 저장되어 있다


''' 데이터 가져오기 '''
import seaborn as sns
df = sns.load_dataset('titanic')
df.info()

### 2. 결측치 처리

''' Titanic Data 는 결측치가 많이 존재 
 결측치가 많은 deck 열 과 중복열인 embark_town 열을 제거 
 다른 데이터의 경우 상관 계수도 파악해서 상관 관계가 너무 높은 데이터가 존재한다면 지우는 것도 고려'''

rdf = df.drop(['deck', 'embark_town'], axis=1)

''' 결측치가 아주 많지 않은 age 데이터는 결측치를 삭제 '''
rdf = rdf.dropna(subset=['age'], how='any', axis=0)

''' 결측치 대체 - 가장 많이 등장한 값으로 대체 '''
most_freq = rdf['embarked'].value_counts(dropna = True).idxmax()
rdf['embarked'].fillna(most_freq, inplace=True)


### 2-1. 결측치 확인

rdf.info()


''' 3. 분석에 사용할 열만 추출 '''
ndf = rdf[['survived', 'pclass', 'sex', 'age', 'sibsp', 'parch', 'embarked']]

ndf.info()



''' 4. OneHotEncoding '''
onehot_sex = pd.get_dummies(ndf['sex'])
ndf = pd.concat([ndf, onehot_sex], axis = 1 )

''' 데이터 앞에 town 을 추가해서 OneHotEncoding '''
onehot_embarked = pd.get_dummies(ndf['embarked'], prefix='town')
ndf = pd.concat([ndf, onehot_embarked], axis= 1)

ndf.drop(['sex','embarked'],axis=1, inplace=True)

ndf.info()


''' 독립 변수 ( features, 설명 변수 ) 와 종속 변수 ( target ) 생성 '''
X = ndf[['pclass','age','sibsp','parch','female','male','town_C','town_Q','town_S']]
y = ndf['survived']


''' 6. 독립 변수들을 정규화 - Machine Learning 에서 정규화로 인한 성능 향상은 미비한 경우가 많다 '''
from sklearn import preprocessing

X =preprocessing.StandardScaler().fit(X).transform(X)


''' 7. 훈련 데이터와 테스트 데이터를 분리 '''
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

''' 8. Model 생성 '''

forest = RandomForestClassifier(criterion='entropy',
                                n_estimators=25,
                                random_state=42,
                                n_jobs= -1)
''' Model 훈련 '''
forest.fit(X_train, y_train)

''' 예측 '''
y_hat = forest.predict(X_test)

''' 교차 검증이나 Hyper Parameter Tuning'''
''' GridSearCh 나 RandomCV 를 이용해서 최적의 HyperParameter 찾기'''
''' 교차 검증을 이용해서 데이터 확인 '''
''' RandomForest 나 Boosting 알고리즘은 이 단계에서 많은 시간을 소모 '''


''' 9. 결과 확인 '''
print(y_hat[:10])
print(y_test.values[:10])

''' 평가 지표 : 오차 행렬 '''
from sklearn import metrics
tree_metrix = metrics.confusion_matrix(y_test, y_hat)
print(tree_metrix)

''' 10-1.Report  '''
tree_report = metrics.classification_report(y_test, y_hat)
print(tree_report)

''' 11. feature 의 중요도 확인 '''
# 트리 모델들은 feature 의 중요도를 확인하는 것이 가능하다

n_features = X.data.shape[1]
plt.barh(np.arange(n_features), forest.feature_importances_, align='center')
plt.yticks(np.arange(n_features),['pclass', 'age','sibsp', 'parch',
                    'female','male','town_C','town_Q','town_S'])
plt.show()


''' ------------------------ Random Forest를 이용한 집 값 예측 — 회귀 ------------------------ '''

''' 데이터 가지고 오기 '''
from sklearn.datasets import load_boston

boston = load_boston()
''' feature 데이터의 항목 이름 확인 '''
print(boston.feature_names)



### 2. 데이터 확인 및 전처리 설명


'''2. 데이터 확인 및 전처리 설명'''
bostonDF = pd.DataFrame(boston.data, columns=boston.feature_names)
bostonDF['PRICE'] = boston.target

bostonDF.info()


''' 3. Feature 데이터와 target 데이터 생성  '''
X_feature = bostonDF['RM'].values.reshape(-1, 1)
y_target = bostonDF['PRICE'].values.reshape(-1, 1)

''' 4. 회귀 모델 생성  '''
from sklearn.ensemble import RandomForestRegressor

rf = RandomForestRegressor(n_estimators=1000, random_state=42)


''' 5. 교차 검증 '''
from sklearn.model_selection import cross_val_score

''' 5 겹 교차 검증 '''
neg_mse_scores = cross_val_score(rf, X_feature, y_target,
                                scoring='neg_mean_squared_error',
                                cv=5)
print(f'교차 검증 점수 : {neg_mse_scores}')



''' 가시성 좋게 만들기 '''
''' 잔차의 제곱의 합의 제곱근 - rmse  '''
rmse_scores = np.sqrt(-1*neg_mse_scores)
print(f"RMSE : {neg_mse_scores}")



''' 위와 같이 생성하면 가시적으로 구분하기 어렵기 때문에'''
# 교차 검증시 RMSE 의 평균 값을 구한다

''' RMSE 의 평균 값 '''
avg_rmse = np.mean(rmse_scores)
print(f'RMSE 평균 : {avg_rmse}')


''' ----------------------- AdaBoost 실습 : Make moons ------------------------ '''
''' 1. 비선형 데이터 생성  '''
from sklearn.datasets import make_moons

X, y = make_moons(n_samples=500, noise=0.30, random_state=42)


''' 2. 모델 생성 및 훈랸 '''
from sklearn.ensemble import AdaBoostClassifier
from sklearn.tree import DecisionTreeClassifier

''' HyperParameter 삽입 '''
ada_clf = AdaBoostClassifier(DecisionTreeClassifier(max_depth=2),
                            n_estimators=200,
                            algorithm="SAMME.R",
                            learning_rate=0.5,
                            random_state=42)
ada_clf.fit(X,y)

''' GradientBoosting =================================== '''

'''  1. moons 데이터에 GBM 적용 '''
from sklearn.datasets import make_moons

X, y = make_moons(n_samples=500, noise=0.30, random_state=42)

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

'''모델 생성 '''
from sklearn.ensemble import GradientBoostingClassifier
gb_clf = GradientBoostingClassifier(random_state=42)

import time
''' Model 이 훈련하기 전의 시간 저장 '''
start_time = time.time()

''' 훈련 '''
gb_clf.fit(X_train, y_train)

''' Model 이 훈련을 종료한 시간 저장 '''
end_time =time.time()

''' 예측 '''
gb_pred = gb_clf.predict(X_test)

''' 정확도 확인 '''
print(f'정확도 : {metrics.accuracy_score(y_test, gb_pred)}')

''' 훈련 시간 확인 '''
print(f'훈련 시간 : {end_time - start_time}')

# 하이퍼 parameter 튜닝 - 최적의 HyperParameter 찾기

''' HyperParameter 조합을 생성 '''
from sklearn.model_selection import GridSearchCV

''' 확인할 HyperParameter 조합을 생성 '''
params = {
    'n_estimators' : [100, 50],
    'learning_rate' : [0.05, 1]
}

''' Hyper Parameter Tuning 을 시작 '''
grid_cv = GridSearchCV(gb_clf, param_grid=params, cv=2, verbose=1)
grid_cv.fit(X_train, y_train)

''' 최적의 HyperParameter 값이 최소값이나 최대 값인 경우 '''
''' 값을 조금 더 올리거나 , 내려서 수행해야한다.'''
print(f' 최적의 HyperParameter : {grid_cv.best_estimator_}')
print(f' 최적의 정확도 : {grid_cv.best_score_}')



'''XGBoost ======================================'''
''' 데이터 가져오기 '''
from sklearn.datasets import load_breast_cancer

dataset = load_breast_cancer()


X_feature = dataset.data
y_target = dataset.target

cancer_df = pd.DataFrame(data=X_feature, columns=dataset.feature_names)
cancer_df['target'] = y_target
cancer_df.info()


''' 2. target 이름 확인 '''
print(dataset.target_names)
''' maligant : 악성  '''
''' benign : 악성이 아닌  '''

''' 3. SampleClass 의 비율확인 '''
print(cancer_df['target'].value_counts)


'''4. 학습 데이터와 훈련 데이터 분할'''
X_train, X_test, y_train, y_test = train_test_split(X_feature, y_target, test_size=0.2, random_state=42)

print(X_train.shape)
print(X_test.shape)

''' 5. XGB 를 위한 데이터 생성  '''
import xgboost as xgb

''' 내부적으로 교차 검증을 수행하기 때문에 데이터 구조가 다름 '''
dtrain = xgb.DMatrix(data=X_train, label=y_train)
dtest = xgb.DMatrix(data=X_test, label=y_test)

''' 6. XGB를 위한 HyperParameter 생성 '''
params = {'max_depth': 3, 'eta':0.1, 'objective':'binary:logistic','eval_metrix':'logloss'}
num_rounds = 400

''' 7. 분류기 생성 '''
wlist = [(dtrain, 'train'), (dtest,'eval')]

''' 훈련 '''
xgb_model = xgb.train(params, dtrain=dtrain,
                    num_boost_round=num_rounds,
                    early_stopping_rounds=100,
                    evals=wlist)

''' 8. 예측  '''
pred_probs = xgb_model.predict(dtest)
''' 확률을 출력 '''
print(np.round(pred_probs[:10],3))

''' 9. 확률을 0.5 기준으로 예측 값을 결정 '''
preds = [1 if x>0.5 else 0 for x in pred_probs]
print(preds[:10])
print(y_test[:10])

''' 10 평가 지표 확인 '''
from sklearn.metrics import confusion_matrix, accuracy_score
from sklearn.metrics import precision_score, recall_score
from sklearn.metrics import f1_score, roc_auc_score


print(f'오차 행렬 : \t\n {confusion_matrix(y_test, preds)}')
print()
print(f'정확도 : {accuracy_score(y_test, preds)}')
print()
print(f'정밀도 :  {precision_score(y_test, preds)}')
print()
print(f'재현율 :  {recall_score(y_test, preds)}')
print()
print(f'f1_score :  {f1_score(y_test, preds)}')
print()
print(f'roc_auc_score :  {roc_auc_score(y_test, preds)}')
print()

'''LightBGM (LGBM ) 를 유방암 데이터 분류 ======================='''

''' 모델 생성 '''
from lightgbm import LGBMClassifier
lgbm_wrapper = LGBMClassifier(n_estimators=400)


'''2. 평가 데이터를 설정하고 모델 훈련 '''

evals = [(X_test, y_test)]

lgbm_wrapper.fit(X_train, y_train, early_stopping_rounds=100,
                 eval_metric='logloss', eval_set=evals, verbose=True)
'''3. 예측'''


''' 3. 예측 '''
preds = lgbm_wrapper.predict(X_test)
preds_proba = lgbm_wrapper.predict_proba(X_test)[:, 1]
print(preds[:10])
print()
print(preds_proba[:10])
print()
print(y_test[:10])


''' 4. 평가 지표 확인 '''
print(f'오차 행렬 : \t\n {confusion_matrix(y_test, preds)}')
print()
print(f'정확도 : {accuracy_score(y_test, preds)}')
print()
print(f'정밀도 :  {precision_score(y_test, preds)}')
print()
print(f'재현율 :  {recall_score(y_test, preds)}')
print()
print(f'f1_score :  {f1_score(y_test, preds)}')
print()
print(f'roc_auc_score :  {roc_auc_score(y_test, preds)}')
print()