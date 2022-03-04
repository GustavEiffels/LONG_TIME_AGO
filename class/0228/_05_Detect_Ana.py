import numpy as np
import pandas as pd
import wquantiles

''' 데이터를 읽어오고 구조 확인 '''
# 구분자로 탬을 설정
chipo = pd.read_csv('../python_statistics-main/chipotle.tsv', sep='\t')
print(chipo.shape)
print()
''' 행의 개수 4622개 열은 5개가 존재하는 것을 알 수 있음 '''

chipo.info()
print()

print(" before ==============")
print(chipo.describe())

# order_id 는 숫자일 필요가 없고 , item_price 는 숫자여야 한다
''' 자료형 변환을 해야한다 .'''
# 1. order_id 를 문자열 형태로 변환
chipo['order_id'] = chipo['order_id'].astype(str)
print(" After ==============")
print(chipo.describe())
print()



# 2. 많이 주문한 item_name 을 찾는 작업// 가장 많이 주문한 10 개 찾기
item_count = chipo['item_name'].value_counts()[:10]
print(item_count)
print()

# 3. item_name 별 주문 개수 확인
order_count = chipo.groupby('item_name')['order_id'].count()
print(order_count.head())
print()
print(order_count.tail())
print()

# 4. item_name 별 주문 총량
order_allCount = chipo.groupby('item_name')['quantity'].sum()

print(order_allCount)
print()
print(type(order_allCount))
print()
print()

# 5. item_price 데이터를 실수로 변경
print(chipo['item_price'].unique())
print()

''' astype 으로는 변환이 안됨 // 함수를 적용해서 float()으로 사용 가능'''
# lamda 적용
chipo['item_price'] = chipo['item_price'].apply(lambda x: float(x[1:]))
print(chipo['item_price'].unique())
print()

''' 주문당 item_price 합계 '''
print(chipo.groupby('order_id')['item_price'].sum())
print()
print(chipo.groupby('order_id')['item_price'].sum().mean())
print()
''' describe 적용 '''
print(chipo.groupby('order_id')['item_price'].sum().describe())


