# pandas 라는 모듈을 사용할 때 pd 라는 이름으로 가져온다
import pandas as pd

# pandas package Series 모듈의 현재 application의 memory 로 가져와서 사용
# Series 앞에는 pandas. 을 붙일 필요가 없다
from pandas import Series

# list 를 사용해서 Series 를 생성
# index를 설정하지 않아서 index 는 0 부터 시작하는 Sequence
price = Series([10000, 20000, 50000, 100000])
print(price)
print()

print("속성확인---------- ")
print()
# index 확인
print("Index confirm -----------")
print(price.index)
print()
# 데이터를 확인
print("Data Confirm ----------")
print(price.values)
print()
# Confirm data type
print("Confirm data type ----------")
print(type(price.values))
print()
# Index 변경
print('Convert Index------')
print(f' Before : \n{price}')
print('---------')
price.index = ['type 1', 'type 2', 'type 3', 'type 4']
print(f' After : \n{price}')
print()

