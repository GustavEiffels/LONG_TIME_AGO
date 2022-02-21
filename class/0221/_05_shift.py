import numpy as np
import pandas as pd

import _00_Common

# 파이썬에서 제공하는 날짜 데이터 모듈
from datetime import datetime

# python 의 날짜 형식으로 데이터 생성
dates = [datetime(2021, 1, 1),
         datetime(2021, 1, 4),
         datetime(2021, 1, 15),
         datetime(2021, 1, 31)]

#
print("--- 날짜 데이터를 인덱스로 해서 4 개의 데이터를 랜덤하게 생성  ---")
ts = pd.Series(np.random.randn(4), index=dates)
print(ts)
print()
'''
result ----- 
--- 날짜 데이터를 인덱스로 해서 4 개의 데이터를 랜덤하게 생성  ---
2021-01-01   -0.473419
2021-01-04    0.584464
2021-01-15   -0.470386
2021-01-31    1.592848
dtype: float64
'''

# shift 사용 case
print("--- 데이터가 하나씩 밀려서 데이터가 대입된다. ---")
print(ts.shift(1))
print()
'''
result ----- 
 데이터가 하나씩 밀려서 데이터가 대입된다. ---
2021-01-01         NaN
2021-01-04   -0.473419
2021-01-15    0.584464
2021-01-31   -0.470386
dtype: float64
'''

# shift 사용 case
print("--- 데이터가 하나씩 당겨서 데이터가 대입된다. ---")
print(ts.shift(-1))
print()
'''
result ----- 
--- 데이터가 하나씩 당겨서 데이터가 대입된다. ---
2021-01-01    0.584464
2021-01-04   -0.470386
2021-01-15    1.592848
2021-01-31         NaN
dtype: float64
'''

