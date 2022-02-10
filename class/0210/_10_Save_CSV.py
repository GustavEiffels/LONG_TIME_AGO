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

# 저장
print("Save Data without Index")
df.to_csv('./withoutIndex.csv', index=False)