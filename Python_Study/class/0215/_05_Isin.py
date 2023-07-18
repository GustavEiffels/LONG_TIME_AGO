import numpy as np
import pandas as pd
import seaborn as sns

titanic = sns.load_dataset('titanic')

# titanic 에서 sibsp 가 3, 4, 5인 데이터를 추출
print("titanic 에서 sibsp 가 3, 4, 5인 데이터를 추출")
titanic.loc[(titanic['sibsp'] == 3) | (titanic['sibsp'] == 4) | (titanic['sibsp'] == 5)]
print(titanic.loc[(titanic['sibsp'] == 3) | (titanic['sibsp'] == 4) | (titanic['sibsp'] == 5)].head())

# isin
print("isin")
titanic.loc[titanic['sibsp'].isin([3, 4, 5])]
print(titanic.loc[titanic['sibsp'].isin([3, 4, 5])].head())