# dict 를 이용한 Data Frame  생성
import pandas as pd
from pandas import Series, DataFrame

items = {
    'a': ['apple', 'Americano', 'adult', 'Application'],
    'b': ['binary', 'banana', 'benz', 'Basket'],
    'c': ['cheese', 'chocolate', 'cake', 'coffee'],
    'd': ['dissert', 'dolce & Gabbana', 'Dutch', 'document']
}
df = DataFrame(items)
print("==== Create DataFrame Using By List ====")
print(df)
print()
print("Confirm Type ==========")
print(type(df))
print()

# 컬럼 이름 변경
print("== 컬럼 이름 변경== ")
print(f'before = \n{df}')
print()
df.columns = ['From A', 'From B', 'From C', 'From D']
print(f'after = \n{df}')
print()

# index 변경
print('== index change ==')
print(f'before = \n{df}')
print()
df.index = range(1, 5, 1)
print(f'after = \n{df}')
print()

#  앞으로 3개의 데이터 확인
print('== confirm Three data of Dataframe')
print(f'result =\n{df.head(3)}')
print()

# 데이터 개요 확인
print('== confirm data info ==')
print(df.info())
print()

