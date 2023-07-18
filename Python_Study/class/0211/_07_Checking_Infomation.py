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

# 정보 확인
print("Confirm Information -------------------------------")
print(mpg.info())
print()

# 행과 열의 파악 - 차원 파악
# 1 차원 데이터라면 (숫자 , )
# 튜플에 데이터가 1개인 경우 (데이터, )로 작성해야한다
# (데이터) --> 튜플이 아니고 데이터이다
print(mpg.shape)
print()

# 각 열의 자료형을 리턴
print("각 열의 자료형을 리턴")
print(mpg.dtypes)
print()

# 기술 통계 정보 확인 - 숫자데이터 정보만 리턴
print("기술 통계 정보 확인 - 숫자데이터 정보만 리턴")
print(mpg.describe())
print()

# 기술 통계 정보 확인  - 모든 열 확인
print(mpg.describe(include='all'))
