import pandas as pd
from pandas import Series, DataFrame

df = pd.read_csv('./item.csv')
print(df)

# 컬럼 이름 변경
# Inplace= True 옵션이 없으면 새로은 DataFrame 만들어서 리턴
# print("rename column")
# df.rename(columns={'code': '코드',
#                    'manufacture': '원산지',
#                    'name': '이름',
#                    'price': '가격'}, inplace=True)
# print(df)


# 함수를 적용
print("Apply Function=====")
print("-- before apply --")
print(df)
print()
print("-- after apply --")
df.rename(str.upper, axis='columns', inplace=True)
print(df)
print()

# index 재구성
print("Index Modify=============")
print("Before ============")
# 수정하기 전 index 는 0 부터 시작하는 일련번
print(df)
print("After =============")
# 기존 컬럼으로 index 설정 ----- code 컬럼은 data에서 제거 , index 로 설정
df.set_index('CODE', inplace=True)
print(df)
# 0 부터 시작하는 일련번호
print()

# index 를 수정 --> 존재하는 인덱스는 그대로 남지만 없는 인덱스는 추가
# 존재하지 않는 데이터는 제거
print("index 를 수정 --> 존재하는 인덱스는 그대로 남지만 없는 인덱스는 추가")
df_reindex = df.reindex(['1', '2', '3', '6', '7', '8'])
print(df_reindex)
print()

# index 초기화 -- 0 부터 시작하는 숫자로 인덱스가 변경됨
print("index 초기화")
print(df_reindex.reset_index())


