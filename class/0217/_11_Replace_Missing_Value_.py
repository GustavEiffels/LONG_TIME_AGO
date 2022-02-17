"""
데이터 생성
보통 대문자 X 는 훈련된 데이터를 의미
"""
import numpy as np

X = np.array([[0., 2.10, 1.45],
              [1., 1.18, 1.33],
              [0, 1.22, 1.27],
              [1, -0.22, -1.19]])

Y = np.array([[np.nan, 0.87, 1.31],
              [np.nan, 0.67, 0.22]])

print(X)
print()
print(Y)
print()

'''
Machine Learning 으로 대체

from sklearn.neighbors import KNeighborsClassifier---> 분류할 때 사용  
'''
from sklearn.neighbors import KNeighborsClassifier

clf = KNeighborsClassifier(3, weights='distance')

# 1 번 열 이후의 데이터를 가지고 0 번 열을 예측하는 모델을 생성
train_model = clf.fit(X[:, 1:], X[:, 0])

# 예측
print("========== 예측 ==========")
imputed_values = train_model.predict(Y[:, 1:])
print(imputed_values)
print()


# 대체
print("========== 대체 ==========")
y_with_imputed = np.hstack(
    (imputed_values.reshape(-1, 1), Y[:, 1:])
)
print(y_with_imputed)
print()
print("===== X 값 =====")
print(X)
print()
print("===== Y 값 =====")
print(Y)
print()
'''
대체된 데이터와 X 를 위아래로 합치기 
'''
print("대체된 데이터와 X 를 위아래로 합치기")
np.vstack((X, y_with_imputed))
print(np.vstack((X, y_with_imputed)))
print()


'''
가장 많이 등장하는 데이터로 교체 
'''
print("가장 많이 등장하는 데이터로 교체")
X_complete = np.vstack((X, Y))
print(X_complete)
print()

from sklearn.impute import SimpleImputer
imputer = SimpleImputer(strategy='most_frequent')
imputer.fit_transform(X_complete)
print("Result =====")
print(imputer.fit_transform(X_complete))
