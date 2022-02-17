import pandas as pd
import numpy as np
from pandas import Series, DataFrame

mpg = pd.read_csv('./noheader_auto-mpg.csv', header=None)
print("mpg 의 첫번째 행이 데이터가 아니므로 컬럼 이름 만들기============================")
mpg.columns = ['mpg',
               'cylinders',
               'displacement',
               'horsepower',
               'weight',
               'acceleration',
               'model_year',
               'origin',
               'name']


# 데이터 프레임은 정렬할 때 by parameter 가 필수 --- 오름 차순
print("데이터 프레임은 정렬할 때 by parameter 가 필수 --- 오름 차순")
print(mpg.sort_values(by="mpg").head())
print()

# 데이터 프레임은 정렬할 때 by parameter 가 필수 --- 내림차순
print("데이터 프레임은 정렬할 때 by parameter 가 필수 --- 내림 차순")
print(mpg.sort_values(by="mpg", ascending=False).head())
print()


# 2개 이상을 적용해서 정렬
print("2개 이상을 적용해서 정렬")
print(mpg.sort_values(by=['mpg', 'acceleration'], ascending=[True, False]).head())
print()

