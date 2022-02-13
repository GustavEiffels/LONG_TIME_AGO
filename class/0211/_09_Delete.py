import pandas as pd
from pandas import Series, DataFrame

mpg = pd.read_csv('./auto-mpg.csv')
print(" 이 경우 첫번째 데이터가 header 가 된다. ")
print()

print("Setting NoHeader ==")
mpg = pd.read_csv('./auto-mpg.csv', header=None)
print()

# 컬럼 이름을 직접 설정
print(" column name setting by myself")
mpg.columns = columns = ['mpg', 'cylinders', 'displacement',
                            'horsepower', 'wight', 'acceleration',
                            'model year', 'origin', 'name']
print(mpg)
print()

# 0 번행 삭제
print("0 번행 삭제 ======")
mpg.drop(0, inplace=True)
print(mpg)
print()

# 2 번행과 3번행 삭제
print("2 번행과 3번행 삭제 ===============")
mpg.drop([2, 3], inplace=True)
print(mpg)
print()

# 열 삭제
# name 열 삭제 - axis 축을 설정
print("name 열 삭제 - axis 축을 설정")
mpg.drop('name', axis=1, inplace=True)
print(mpg.head())
print()