import matplotlib.pyplot as plt
import numpy as np

import _00_ColumnTransfer as h1
import Housing as h0

''' Random Forest 적용 '''
from sklearn.ensemble import RandomForestRegressor
print()
print('Random Forest 적용 ----------------------------')
# 1. model 생성
forest_reg = RandomForestRegressor()

# 2. model 에 훈련 데이터를 대입해서 훈련 - 학습
forest_reg.fit(h1.housing_prepared, h0.housing_labels)

# # 3. 교차 검증
# froest_scores = h1.cross_val_score(forest_reg,
#                                 h1.housing_prepared,
#                                    h0.housing_labels,
#                                    scoring='neg_mean_squared_error',
#                                    cv=10)
# forest_rmse_scores = np.sqrt(-froest_scores)
# h1.display_scores(forest_rmse_scores)

''' 검증 곡선 '''

# 다항 회귀 모델을 위한 준비
from sklearn.preprocessing import PolynomialFeatures
from sklearn.pipeline import make_pipeline
from sklearn.linear_model import LinearRegression


# 다항 회귀 모델을 생성할 함수 -- degree 는 항의 차수
def PolymialRegression(degree=2, **kwargs):
    return make_pipeline(PolynomialFeatures(degree),
                         LinearRegression(**kwargs))

# 1. sample Data 생성
def make_data(N, err = 1.0, rseed=42):
    # seed 설정
    rng = np.random.RandomState(rseed)
    X = rng.rand(N, 1)**2
    y = 10 - 1 / (X.ravel() + 0.1)

    if err > 0:
        y += err * rng.randn(N)
    return X, y

X, y =make_data(40)
print(" 독립 변수 생성 - feature")
print(X)
print()
print(" 종속 변수 생성 - target")
print(y)
print()

X_test = np.linspace(-0.1, 1.1, 500)[:, None]
plt.scatter(X.ravel(), y, color='black')

# 1차 ,3차 5차 다항식을 생성해서 출력
axis = plt.axis()
for degree in [1, 3, 5]:
    y_test = PolymialRegression(degree).fit(X, y).predict(X_test)
    plt.plot(X_test.ravel(), y_test, label='degree={0}'.format(degree))
plt.legend()
plt.show()

''' 검증 곡선 만들기 '''
from sklearn.model_selection import validation_curve

degree = np.arange(0, 21)

train_score, val_score = validation_curve(PolymialRegression(),
                                          X,
                                          y,
                                          param_name=
                                          'polynomialfeatures__degree',
                                          param_range=degree,
                                          cv=7)
plt.plot(degree, np.median(train_score, 1), color='blue', label='train')
plt.plot(degree, np.median(val_score, 1), color='red', label='val')
plt.legend(loc='best')
plt.show()

''' 데이터를 추가 생성 '''
degree = np.arange(0, 21)

X2, y2 = make_data(200)

train_score2, val_score2 = validation_curve(PolymialRegression(),
                                          X2,
                                          y2,
                                          param_name='polynomialfeatures__degree',
                                          param_range=degree,
                                          cv=7)
plt.plot(degree, np.median(train_score, 1), color='blue', label='train_40')
plt.plot(degree, np.median(val_score, 1), color='red', label='val_40')
plt.plot(degree, np.median(train_score2, 1), color='blue', label='train_200', linestyle='dashed')
plt.plot(degree, np.median(val_score2, 1), color='red', label='val_200', linestyle='dashed')
plt.legend(loc='best')
plt.show()


''' Grid '''
from sklearn.model_selection import GridSearchCV

param_grid = [
    # 하이퍼파라미터 12(=3×4)개의 조합을 시도
    {'n_estimators': [3, 10, 30], 'max_features': [2, 4, 6, 8]},
    # bootstrap은 False로 하고 6(=2×3)개의 조합을 시도
    {'bootstrap': [False], 'n_estimators': [3, 10], 'max_features': [2, 3, 4]},
  ]

forest_reg = RandomForestRegressor(random_state=42)
# 다섯 폴드에서 훈련하면 총 (12+6)*5=90번의 훈련
grid_search = GridSearchCV(forest_reg, param_grid, cv=5, scoring='neg_mean_squared_error',
                           return_train_score=True, n_jobs=-1)
grid_search.fit(h0.housing_prepared, h1.housing_labels)

'''최적의 하이퍼 parameter 접근 '''
# 탐색 범위의 최대값
print(grid_search.best_params_)

''' 최적의 모델에 접근 '''
# 최적의 알고리즘에 접근
print(grid_search.best_estimator_)

''''''
#랜덤 탐색
from sklearn.model_selection import RandomizedSearchCV
from scipy.stats import randint

param_distribs = {
        'n_estimators': randint(low=1, high=200),
        'max_features': randint(low=1, high=8),
    }

forest_reg = RandomForestRegressor(random_state=42)
# n_jobs 는 사용하는 연산 장치, thread 의 수
rnd_search = RandomizedSearchCV(forest_reg, param_distributions=param_distribs,
                                n_iter=10, cv=5, scoring='neg_mean_squared_error',
                                random_state=42, n_jobs=-1)
rnd_search.fit(h1.housing_prepared, h0.housing_labels)

cvres = rnd_search.cv_results_
for mean_score, params in zip(cvres["mean_test_score"], cvres["params"]):
    print(np.sqrt(-mean_score), params)
