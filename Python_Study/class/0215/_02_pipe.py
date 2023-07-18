import numpy as np
import pandas as pd
import seaborn as sns

# seaborn이 가지고 있는 titanic 이라는 데이터를 가져오기
# titanic 데이터는 회귀나 분류를 할 때 많이 사용
# 각 속성에 따른 생존 여부
titanic = sns.load_dataset('titanic')
# titanic.info()
# age와 fare 열만 추출
df = titanic[['age', 'fare']]


# print(df)
# 열을 추가 - 없는 열이름 속성에 데이터를 대입하면 된다.
# df['ten'] = 10
# print(df)
# apply 함수에 적용 할 함수 생성
def func1():
    print("매개변수가 없고 리턴이 없는 함수")


def func2(a):
    return a + 10


def func3(a, b):
    return a + b


# 매개변수가 없는 함수를 대입해서 매개변수의 최소 개수를 위반
# df.apply(func1) #argument 에러가 나면 변수개수나 자료형을 꼭 확인!!
# 매개변수의 최소개수와 리턴을 만족했기 때문에 정상 수행
# print(df.apply(func2))
# 매개변수가 남는 것은 가능한데 남는 매개변수에 값을 대입해야 한다.
# print(df.apply(func3,b=20))

# pipe 함수 - 함수에 함수를 적용하는 것이 가능
# 하나의 값을 받아서 결과를 리턴하는 함수
def missing_value(x):
    return x.isnull()


# 위 함수의 결과를 집계해서 개수를 리턴
def missing_count(x):
    return missing_value(x).sum()


# 위 함수의 결과를 이용해서 집계
def total_missing_count(x):
    return missing_count(x).sum()


print(df.pipe(missing_value))  # DataFrame이 리턴
# print(df.pipe(missing_count)) #Series리턴
# print(df.pipe(total_missing_count)) #1개의 값 리턴


