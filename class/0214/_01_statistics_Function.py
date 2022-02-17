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

print(" 정보 확인 ")
print(" 상위 다섯개의 데이터를 출력 ")
print(mpg.head())
print()
print("Horizon===================================================")
print("해당 csv 파일의 정보를 확인 ")
mpg.info()
print()

print(" 기술 통계 함수 적용 ---------------------- 데이터 탐색 ")
print()
print(" Mean = 평균 ")
print(f"Mean of MPG  =  \n {mpg.mean()}")
print()

print("특정 열의 평균을 계산")
print()
print(f' Mean of MPG = \n{mpg["mpg"].mean()}')
print()

print("특정 열들의 평균을 계산")
print()
print(f'Mean of mpg, weight = \n{mpg[["mpg","weight"]].mean()}')

print("상관계수 --- 절대 하나의 열가지고 할 수 없다 ==============================")
print()
print(" 모든 상관계수 확인 ")
print(mpg.corr())
print()

#2개 열의 상관계수 확인
print("2개 열의 상관계수 확인 ==================")
print()
print(mpg[['mpg', 'weight']].corr())
print()