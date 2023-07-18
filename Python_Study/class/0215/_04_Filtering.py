import numpy as np
import pandas as pd
import seaborn as sns

titanic = sns.load_dataset('titanic')

print("나이가 30 이상인 data 만 추출 ======================================")
print(titanic.loc[titanic['age'] >= 30].head())
print()

print("나이가 30 에서 39 살 사인 데이터 ======================================")
print(titanic.loc[(titanic['age'] >= 30) & (titanic['age'] <= 39)].head())
print()
