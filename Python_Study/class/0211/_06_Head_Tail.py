# noheader_auto_mpg.csv
# 이 파일에는 한글이 없고 첫번째 행이 열이름이 아니다

import pandas as pd
mpg = pd.read_csv('./auto-mpg.csv')
print(mpg)
print(" 이 경우 첫번째 데이터가 header 가 된다. ")
print()

print("Setting NoHeader ==")
mpg = pd.read_csv('./auto-mpg.csv', header=None)
print(mpg)
print()

# 컬럼 이름을 직접 설정
print(" column name setting by myself")
mpg.columns = columns = ['mpg', 'cylinders', 'displacement',
                            'horsepower', 'wight', 'acceleration',
                            'model year', 'origin', 'name']
print(mpg)
print()

# 앞의 5개의 데이터만 추출
print("print front of 5 data ")
print(mpg.head())
print()

# 뒤의 5개의 데이터만 추출
print('print 5 data using tail')
print(mpg.tail())
print()

