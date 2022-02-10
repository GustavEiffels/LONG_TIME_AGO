import pandas as pd
from pandas import Series, DataFrame

df = pd.read_fwf('./data_fwf.txt', width=(10, 2, 5), names=('date', 'daum', 'money'))
print(df)