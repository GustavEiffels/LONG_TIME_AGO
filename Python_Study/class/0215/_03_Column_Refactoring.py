import numpy as np
import pandas as pd
import seaborn as sns

stock = pd.read_excel('./주가데이터.xlsx')
print("잘 불러왔는지 확인")
print(stock.head())
print()

print("-----> stock.info()를 사용해서 '연월일' data type 확인 ")
print(stock.info())
print()

print("----- 연월일을 문자열로 변환 ")
stock['연월일'] = stock['연월일'].astype('str')
stock.info()
print()

print(" - 로 분할해서 list로 만들어서 리턴 ")
dates = stock['연월일'].str.split('-')
print(dates.head())
print()

print("년월일 컬럼 생성 ")
stock['year'] = dates.str.get(0)
stock['month'] = dates.str.get(1)
stock['day'] = dates.str.get(2)

print(stock.head())