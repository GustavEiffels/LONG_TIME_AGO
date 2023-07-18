import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import platform
from sklearn import preprocessing
from matplotlib import font_manager, rc
import seaborn as sns
from sklearn.preprocessing import PolynomialFeatures

# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

'''
행단위로 스케일링
'''
# 2차원 배열로 생성
features = np.array([[1, 2], [2, 3], [3, 4], [8, 8], [9, 9], [2, 8]])
print(features)
print()

'''
교차항 과 다항 특성을 만들어 줄 객체를 생성
degree 는 제곱하는 개수 
include_bias : 맨 첫번째 항에 1을 추가 할것 인지 여부  
'''
polynomial_interaction = preprocessing.PolynomialFeatures(degree=3, include_bias=False)

polynomial_interaction.fit_transform(features)
print(polynomial_interaction.fit_transform(features))

'''
특성 변환
'''

'''
 python 기본 문법 적용
'''

data = [100, 200, 300]

'''
전부 100을 더해주는 작업 
'''
print("전부 100을 더해주는 작업")
result = []

for i in data:
    result.append(i + 100)
print(result)
print()

'''
함수형 프로그래밍의 map 함수 이용 
'''
print("함수형 프로그래밍의 map 함수 이용")


def add_bund(x):
    return x + 100


result = map(add_bund, data)
for i in result:
    print(i, end='\n')
print()

'''
lamda 사용
: 함수 내용이 한줄이라서 람다를 사용하는 것을 권장 
'''
print("lamda 사용")
result = map(lambda x: x + 100, data)
for i in result:
    print(i, end='\n')
print()

'''
pandas 의 apply 함수를 이용한 함수 mapping 
'''


def add_bund1(x):
    return x + 100


print("pandas 의 apply 함수를 이용한 함수 mapping")
titanic = sns.load_dataset('titanic')
print(titanic['age'].apply(add_bund1).head())
print()

'''
Sklearn 의 FunctionTransFormer 이용
'''
print("Sklearn 의 FunctionTransFormer 이용")


# 먼저 만들어 줘야한다
def add_hund1(x):
    return x + 100


one_transformer = preprocessing.FunctionTransformer(add_hund1)
print(one_transformer.transform(titanic['age'].head()))
print()

'''
2 개의 컬럼에 다른 함수를 적용 
columnTransformer
'''
# age 열에는 add_hund1 함수 적용
# parch에는 람다 함수를 적용
print("2 개의 컬럼에 다른 함수를 적용 columnTransformer")
from sklearn import compose
column_transformer = compose.ColumnTransformer(
    [('add_hund1', preprocessing.FunctionTransformer(add_hund1),
      ['age']),
     ('two_hund', preprocessing.FunctionTransformer(lambda x:x+1),
      ['parch'])])
print(column_transformer.fit_transform(titanic))