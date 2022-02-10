import pandas as pd

from pandas import Series

# dict 를 사용해서 Series 를 생성
print('Using Dict for Create A Series ----------')
sourcedata = {'1': 'one', '2': 'two', '3': 'three'}

# dict를 이용해서 Series를 생성
# dict를 가지고 생성하면 key 가 인덱스가 되고 value 는 value 가 된다
data = Series(sourcedata)
print(data)
print()

# 데이터 1개를 가져오기
print("using index number ----------")
print(data[0])
print()
print("Using data key ---------")
print(data['1'])
print()

# 범위 설정하기
print("setting Boundary ----------")
print("using index number ---------- ")
# 0 에서 2 번 앞까지
print(data[0:2])
print()

# key name 1 에서 3 까지
print("using data key value")
print(data['1':'3'])
print()

# Fancy Indexing -- 복제가 발생
print("Fancy Indexing ----------")
print(data[['1', '3']])
print()

# Boolean Indexing - True 인 데이터만 필터링
print("Boolean Indexing -----------")
print(data[[False, True, False]])
print()
