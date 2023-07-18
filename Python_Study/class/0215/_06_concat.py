import numpy as np
import pandas as pd
import seaborn as sns

df1 = pd.DataFrame({'a': ['1', '2', '3', '4'],
                    'b': ['a', 'b', 'c', 'd']})

df2 = pd.DataFrame({'a': ['11', '12', '13', '14'],
                    'b': ['aa', 'ab', 'ac', 'ad']},
                   index=[2, 3, 4, 5])

print("위 아래로 결합 ")
print(pd.concat([df1, df2]))
print()

print(" 좌 우로 결합")
print(pd.concat([df1, df2], axis=1))
print()

print("좌 우로 결합 --- inner")
print(pd.concat([df1, df2], axis=1, join='inner'))
print()