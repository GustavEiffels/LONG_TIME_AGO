import pandas as pd
from pandas import Series, DataFrame

df = pd.read_json('https://raw.githubusercontent.com/chrisalbon/simulated_datasets/master/data.json')
print(df)
print("=============")
print(f'Type = {type(df)}')
print()



print("=============")
print(f'Info : {df.info()}')
print()



print("=============")
print(f'head : {df.head()}')
print()