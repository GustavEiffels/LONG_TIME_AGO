import numpy as np

import Housing as h

from sklearn.compose import ColumnTransformer
print("----------------------------------------------")
print()

''' 변환할 열 이름을 list 로 생성 '''
num_attribs= list(h.housing_num)
print(num_attribs)
print()


cat_attribs = ['ocean_proximity']

# num_attribs -> num_pipeline 변환기를 적용
# cat_attribs -> OneHotEncoder 변환기를 적용

full_pipeline = ColumnTransformer([
    ('num', h.num_pipeline, num_attribs),
    ('cat', h.OneHotEncoder(categories='auto'), cat_attribs)
])

housing_prepared = full_pipeline.fit_transform(h.housing)
print(f'housing_prepared : {housing_prepared}')
print()

# 자료형 확인
print(type(housing_prepared))
print()

'''
scikit_learn 에서는 DataFrame 은 직접 사용이 안된다 
항상 데이터는 numpy의 배열 형태이어야 합니다 .
'''



'''Machine Learning Model 적용 - 선형 회귀 ( LinearRegression ) 모델 적용 '''
print("Machine Learning Model 적용 - 선형 회귀 ( LinearRegression ) 모델 적용")
print()

# 1. 특징 배열 과 타겟 배열을 확인

''' 타겟 배열 '''
print(f'h.housing_labels.shape : \t\n {h.housing_labels.shape}')
print()




# 2. 특징 배열 변경
''' 특징 배열  : target 을 oneHotEncoding 한 것은 제거 '''
# housing_prepared.shape 에서 9 개만 추출
housing_prepared = housing_prepared[:, 0:9]
print(f'housing_prepared.shape[:,0:9] : \t\n {housing_prepared.shape}')
print()

# 3. 모델을 생성해서 훈련 실시
from sklearn.linear_model import LinearRegression

''' 모델 생성 '''
lin_reg = LinearRegression()

''' 모델에 훈련 데이터를 대입해서 훈련 ---> 학습이라고도 한다 '''
lin_reg.fit(housing_prepared, h.housing_labels)

# 4. 훈련에 사용한 데이터를 가지고 예측
some_data = housing_prepared[:5]
some_labels = h.housing_labels.iloc[5]

''' 예측 '''
print(f'예측 : \t\n {lin_reg.predict(some_data)}')
print()
print(f'관측 : \t\n {some_labels}')
print()







''' 모델 평가 지표 확인 '''
print(''' 모델 평가 지표 확인 ''')
from sklearn.metrics import mean_squared_error, mean_absolute_error

# 1. 모든 데이터를 예측
housing_predictions = lin_reg.predict(housing_prepared)

# 2. 평균 제곱 오차
lin_mse = mean_squared_error(h.housing_labels, housing_predictions)
print(f'np.sqrt(lin_mse) : {np.sqrt(lin_mse)}')
print()

# 3 평균 절대값 오차
lin_mae = mean_absolute_error(h.housing_labels, housing_predictions)
print(f'lin_mae : {lin_mae}')
print()


'''Decision Tree 적용 '''
print("Decision Tree 적용")
from sklearn.tree import DecisionTreeRegressor

# 1. model 생성
tree_reg = DecisionTreeRegressor(random_state=42)

# 2. 모델에 훈련 데이터를 대입해서 훈련 ---> 학습
tree_reg.fit(housing_prepared, h.housing_labels)
housing_predictions = tree_reg.predict(housing_prepared)

# 3. 평균 제곱 오차
tree_mse = mean_squared_error(h.housing_labels, housing_predictions)
print(f'np.sqrt(tree_mse) : {np.sqrt(tree_mse)}')


# 교차 검증 수행
print(' 교차 검증 수행 ----------------------------------------- ')
from sklearn.model_selection import cross_val_score

'''
검증을 할 때 scikit_learn 에서는 
높은 점수가 좋은 모델이라고 평가하기를 권장 
mean_squared_error 대신 neg_mean_squared_error 사용 
cv 는 나눔 데이터의 개수
'''
scroes = cross_val_score(tree_reg,
                         housing_prepared,
                         h.housing_labels,
                         scoring='neg_mean_squared_error',
                         cv=10)
# 앞의 모델과 비교하기 위해서 동일한 값으로 변환
tree_rmse_scores = np.sqrt(-scroes)

# 함수를 생성
def display_scores(scores):
    print(f'Scores : \t\n {scores}')
    print()
    print(f'Mean :  \t\n {scores.mean()}')
    print()
    print(f'Standard Deviation : \t\n {scores.std()}')

# 의사 결정 나무의 결과
display_scores(tree_rmse_scores)

# 선형 모델의 교차 검증
lin_scores = cross_val_score(lin_reg,
                             housing_prepared,
                             h.housing_labels,
                             scoring='neg_mean_squared_error',
                             cv=10)
lin_rmse_scores = np.sqrt(-lin_scores)
display_scores(lin_rmse_scores)



