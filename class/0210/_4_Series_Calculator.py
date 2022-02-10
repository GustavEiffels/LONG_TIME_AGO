import pandas as pd

from pandas import Series

# Dictionary 생성 ---> 다른 Version
# 하나의 list(keys) ==== key
keys = ['a', 'b', 'c', 'd']
# 다른 하나의 list(values) ===== value 로 해서 dict 생성
values = [10, 20, 30, 40]

# dict 생성
dict1 = dict(zip(keys, values))
print('Create Dictionary Using by Lists ===========')
print(f' dictionary(dict1) : {dict1}')
print()

# 위 방법으로 dict 하나 더 생성
key1 = ['a', 'b', 'ㄷ', 'ㄹ']
values1 = [123, None, 7777, 4987]
dict2 = dict(zip(key1, values1))
print('Create dictionary One more')
print(f' dictionary(dict2) : {dict2}')


# Series로 만듬
print("Convert dictionary to Series")
s1 = Series(dict1)
s2 = Series(dict2)
print(s1)
print(s2)
print()

print("sum ----------")
# a
print(s1 + s2)
#  ㄷ, ㄹ, c, d 는 한쪽만 존재해서 NaN
#  ㄴ 의 데이터가 None 이라서 연산시 NaN 을 출력
print()

