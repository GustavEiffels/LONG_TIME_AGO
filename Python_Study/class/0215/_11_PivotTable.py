import numpy as np
import pandas as pd
import seaborn as sns

titanic = sns.load_dataset('titanic')

df = titanic[['age', 'sex', 'class', 'fare', 'survived']]

pivot_1 = pd.pivot_table(
    df,
    index='class',
    columns='sex',
    values='age',
    aggfunc='mean'
)
print(pivot_1)