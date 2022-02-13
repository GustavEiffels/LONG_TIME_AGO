# item.csv 파일의 내용을 가져오기
# 한글이 없고 첫번째 행이 열의 이름이고 구분자는 ,

import pandas as pd

df = pd.read_csv('./item.csv')

# 요약 정보 확인
print("요약 정보 확인")
df.info()
print()

# 데이터 확인
print('confirm Data')
print(df)
print()

# index  직접 설정
print("Setting Index version 1 : Using Name")
df.index = df.name
print(df)
print()

# index 직접 설정
print("Setting Index version 2 : Making Name")
df.index = ['Apple', 'Watermelon', 'Oriental melon', 'Banana', 'Lemon', 'Mango']
print(df)
print()

# name 컬럼의 값을 전부 가져오기
print(df.name)
print()
print('== Bring all Name of Columns ==')
print(df['name'])
print()

# 하나의 컬럼을 가져왔을 때 자료형 --Series
print("Data Type of column when you bring one thing at columns")
print(type(df['name']))
print()

# 여러개의 열에 데이터를 가져오기
print("여러개의 열에 데이터를 가져오기")
print(df[['name', 'price']])
print()

# 여러개의 열을 가져오는 문법을 사용하면 DataFrame Return
print("If you bring some in columns that's DataType is DataFrame")
print(type(df[['name', 'price']]))
print()

# 하나의 열을 가져올 때도 DataFrame 을 만들기 위해서 list 형태로 설정
print("하나의 열을 가져올 때도 DataFrame 을 만들기 위해서 list 형태로 설정")
print(type(df[['name']]))
print()
