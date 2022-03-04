import random
import numpy as np
import pandas as pd
li = [10, 20, 30, 40, 50]

''' 비복원 추출 '''
# 여기서 k 는  li 의 크기보다 크면 안 된다.
print(random.sample(li, k=5))

''' 복원 추출 '''
for i in range(5):
    print(li[random.randint(0, 4)], end=' ')

print()


''' numpy를 이용한 가중치를 부여한 표본 추출 '''
print(" numpy를 이용한 가중치를 부여한 표본 추출 ")
li = [10, 20, 30, 40, 50]
result = np.random.choice(li, 10, p=[0.5, 0.1, 0.1, 0.1, 0.2])
print(result)


from pandas import DataFrame
ar = [[0, 1, 2], [3, 4, 5], [6, 7, 8], [9, 10, 11]]
df = DataFrame(ar)
print(df)
print()

print('df.sample() ------------------------------')
print(df.sample())
print()


print('df.sample(n=3) ------------------------------')
print(df.sample(n=3))
print()

print('df.sample(n=3, replace=True, weights=[0.7, 0.2, 0.1, 0.0]) ------------------------------')
print(df.sample(n=3, replace=True, weights=[0.7, 0.2, 0.1, 0.0]))
print()


''' Sklearn 을 이용한 표본 추출 '''
from sklearn.model_selection import train_test_split

''' 데이터 생성 --- 독립 변수 '''
print('데이터 생성 --- 독립 변수')
X = np.arange(20).reshape(10, 2)
print(X)
print()

''' 데이터 생성 --- 종속 변수 '''
print('데이터 생성 --- 종속 변수')
Y = np.arange(10)
print(Y)
print()

''' 순차적으로 훈련 데이터와 테스트 데이터를 6:4의 비율로 추출 '''
# X 는 독립변수 picture , y 는 소문자 -- target
X_trian, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.4,
                                                    shuffle=False,
                                                    random_state=42)
print('X_trian -----')
print(X_trian)
print()
print('X_test -----')
print(X_test)

'''랜덤하게 훈련데이터와 테스트 데이터를 6:4 의 비율로 추출'''
# X 는 독립변수 picture , y 는 소문자 -- target
X_trian, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.4,
                                                    shuffle=True,
                                                    random_state=42)
print('X_trian -----')
print(X_trian)
print()
print('X_test -----')
print(X_test)