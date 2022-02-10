import pandas as pd

from pandas import Series, DataFrame

# 첫번째 열을 인덱스로 사용
df = pd.read_excel('./excel.xlsx', index_col=0)
print(df)
print()

# 현재 데이터를 파일로 저장
writer = pd.ExcelWriter('./0210t.xlsx')
df.to_excel(writer, sheet_name="Sheet1")
writer.save()

