import pandas as pd
from pandas import Series, DataFrame

# item.csv = 한글이 없고 첫번째 줄이 열인 경우
items = pd.read_csv('./item.csv', header=None, names=['name', 'count', 'price'])
print(items)