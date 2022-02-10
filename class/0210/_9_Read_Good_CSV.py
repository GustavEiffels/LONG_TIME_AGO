# 파일의 데이터를 2개씩 읽기
import pandas as pd
from pandas import Series, DataFrame

print("== 한번에 다 읽는 경우 ==")
items = pd.read_csv('./good.csv', header=None, names=['이름', '개수', '가격'])
print(items)
print()

print("== 두개씩 읽는  경우 ==")
items = pd.read_csv('./good.csv',
                    header=None,
                    names=['이름', '개수', '가격'],
                    chunksize=2)
for item in items:
    print(item)
print()