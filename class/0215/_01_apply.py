import pandas as pd
import numpy as pd
import seaborn as sns

# seaborn 이 가지고 있는 titanic 이라는 데이터를 가져오기
# titanic 데이터는 회귀나 분류 할 때 많이 사용
# 각 속성에 따른 생존 여부
titanic = sns.load_dataset('titanic')
titanic.info()

# age 와 열만 추출
print(" age 와 열만 추출 ")
df = titanic[['age', 'fare']]
print(df)
print()

# # 열 추가 ----
# # 없는 열이름 속성에 데이터를 대입
# print("없는 열이름 속성에 데이터를 대입 =========================")
# df['ten'] = 10
# print(df)


# apply 함수에 적용할 함수 생성
def func1():
    print("NO Parameter and return ")

def func2(a):
    print("One Parameter and return")
    return a + 10

def func3(a, b):
    print("Two Parameter and return")
    return a+b


# parameter 가 없는 함수를 대입해서 parameter 의 최소 개수를 위반
# print("parameter 가 없는 함수를 대입해서 parameter 의 최소 개수를 위반")
# df.apply(func1)
# print()

# parameter 의 최소 개수 와 리턴을 만족했기 때문에 정상 수행
print("parameter 의 최소 개수 와 리턴을 만족했기 때문에 정상 수행")
print(df.apply(func2))
print()

# parameter 가 남는 것은 가능한데 남는 parameter에 값을 대입
print("parameter 가 남는 것은 가능한데 남는 parameter에 값을 대입")
print(df.apply(func3, b=20))
print()




