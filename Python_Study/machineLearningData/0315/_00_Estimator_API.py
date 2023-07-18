''' Scikit_Learn 을 이용한 Machine Learning '''

# 추정 API 사용

''' 선형 회귀  
- 종속 변수를 가지고 독립 변수의 값을 예측  
'''

# 데이터를 생성
import matplotlib.pyplot as plt
import numpy as np

# seed  고정
rng = np.random.RandomState(42)

'''
대문자 X sms 특징 배열 (Features, 독립 변수들의 모임 )
'''
X = 10 * rng.rand(50)

'''
소문자 y 는 Target 배열 ( Target, 종속 변수 )
'''
y = 2 * X - 1 + rng.randn(50)
plt.scatter(X, y)
plt.show()

from sklearn. linear_model import LinearRegression

# 데이터 구조 변경
''' 1차원을 2차원으로 변경 '''
X = X[:, np.newaxis]

''' 독립 변수들은 모임으로 간주하기 때문에 
특별한 경우가 아니면 2차원 배열이여야 한다 .
'''
print(X.shape)
print()

model = LinearRegression(fit_intercept=True)

# 모델 훈련
model.fit(X, y)

''' 훈련 결과 확인 
1. 하이퍼 파라미터는 속성이 public 

2. 결과 parameter 는 속성 이름 뒤에_가 붙는다 

3. 선형 회귀의 결과는 기울기 ( coef ) 와 절편 (intercept_
'''
print(f' 기울기 = {model.coef_}')
print(f' 절편 = {model.intercept_}')


# 예측
xfit = np.linspace(-1, 11)
Xfit = xfit[:, np.newaxis]
''' 예측 시에는 predict function 사용 '''
yfit = model.predict(Xfit)

plt.scatter(X, y)
plt.plot(xfit, yfit)
plt.title("predict")
plt.show()