import pandas as pd
import numpy as np
from pandas import Series, DataFrame

item = pd.read_csv('./item.csv')
print(item)
print()

print(" 순위 출력 ================== 동일한 순위가 나오면 순위의 평균 출력 ")
print(item.rank())
print()

# 내림차순으로 순위를 만들고 동일한 값인 경우 낮은 순위를 설정
print("내림차순으로 순위를 만들고 동일한 값인 경우 낮은 순위를 설정")
print(item.rank(ascending=False, method='min'))
print()