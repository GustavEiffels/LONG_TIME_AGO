import pandas as pd

df = pd.read_csv('./item.csv')

# index 직접 설정
print("Setting Index version 2 : Making Name")
df.index = ['Apple', 'Watermelon', 'Oriental melon', 'Banana', 'Lemon', 'Mango']
print(df)
print()


# loc 는 index 를 iloc 는 행 번호를 설정
print("Selecting Rows")
print("== method of Loc ==")
print(df.loc['Apple'])
print()
print("== method of ILOC ==")
print(df.iloc[0])
print()

# 하나의 cell 을 선택
print('Choosing one Cell')
# 열이름을 먼저 대입하고 , 행이름 대입
print("== Using LOC == [column][row]")
print(df.loc['Apple']['price'])
print()

# 범위 선택
print("selecting range ")
print("== Using index ==")
print(df.loc['Apple':'Oriental melon'])
print()
print("== Not Using index ==")
# iloc 사용하면 2는 포함 되지 않음
print(df.iloc[0:2])
print()

# BoolIndexing
# price가 1000보다 큰 데이터만 추출
print("BoolIndexing : Selecting Data at price over the thousand")
print(df[df['price'] > 1000])
print()
# price 가 1000 이나 1500 인 데이터만 추출
print("BoolIndexing "
      ": selecting data at price columns "
      " whether price equals 1000 or 1500")
print(df[df['price'].isin([1000, 1500])])
print()




