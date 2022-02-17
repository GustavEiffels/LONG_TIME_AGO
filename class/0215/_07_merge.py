# Merge
import pandas as pd
price = pd.read_excel('./stock price.xlsx')
price.info()
valuation = pd.read_excel('./stock valuation.xlsx')
valuation.info()


# 양쪽 모두에 존재하는 데이터만 결합 -- id라는 공통 열을 가지고 join
print("양쪽 모두에 존재하는 데이터만 결합 -- id라는 공통 열을 가지고 join")
merge_inner = pd.merge(price, valuation)
print(merge_inner)
print()

# 모든 데이터 결합 - id 라는 공통열을 가지고 결합
print("모든 데이터 결합 - id 라는 공통열을 가지고 결합")
merge_outer = pd.merge(price, valuation, how='outer', on='id')
print(merge_outer)

# 서로 다른 열 이름을 가지고 조인
print("서로 다른 열 이름을 가지고 조인")
merge_outer = pd.merge(price, valuation, how='outer',
                       left_on='stock_name', right_on='name')
print(merge_outer)