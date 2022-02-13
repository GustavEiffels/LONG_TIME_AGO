import pandas as pd
from pandas import  Series, DataFrame
df = pd.read_csv('./item.csv')
print("item.csv 사용")
print(df)

# 열의 모든 값이 대한민국으로 설정
print("열의 모든 값이 대한민국으로 설정")
df['manufacture'] = '대한민국'
print(df)
print()

# list로 대입
print(" Insert by List========= ")
df['code'] = [100, 200, 300, 400, 500, 600]
print(df)
print()

# dict 로 대입 - key 가 순서대로 대입
print(" Insert by Dict =============")
df['manufacture'] = {0: '대한민국',
                     2: '미국',
                     3: '중국',
                     4: '러시아',
                     5: '뉴질랜드',
                     1: '호주'}
print(df)
print()


# Series로 대입 -- index 와 매칭해서 대입
print("Series로 대입 -- index 와 매칭해서 대입")
df['manufacture'] = Series(['대한민국',
                            '미국',
                            '중국',
                            '러시아',
                            '뉴질랜드',
                            '호주'], index=[1, 3, 5, 0, 2, 4])
print(df)
print()

# 없는 컬럼 이름을 사용하면 컬럼이 추가
print("없는 컬럼 이름을 사용하면 컬럼이 추가")
df['cout'] = [1, 2, 3, 4, 5, 6]
print()

# 행의 데이터를 변경하고자 할 때는 loc를 이용
print("행의 데이터를 변경하고자 할 때는 loc를 이용")
df.loc[0, 'manufacture'] = 'Soviet Union'
print(df)
