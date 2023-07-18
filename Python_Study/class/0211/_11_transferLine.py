import pandas as pd
from pandas import Series, DataFrame
df = pd.read_csv('./item.csv')
print("item.csv 사용")
print(df)
print()

print(" Transper ====")
print(df.T)
print()

item = {'1': {'price': 1500},
        '2': {'price': 3500},
        '3': {'price': 2500}}

df = DataFrame(item)

print(df)

print(df.T)