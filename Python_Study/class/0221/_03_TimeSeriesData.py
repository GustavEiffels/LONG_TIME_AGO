import pandas as pd

import _00_Common

# case 1 : 월별로 12 개 생성
ts_ms = pd.date_range(start='2021-01-01', end=None, periods=12, freq='MS')
print(ts_ms)
print()
'''
result
DatetimeIndex(['2021-01-01', '2021-02-01', '2021-03-01', '2021-04-01',
               '2021-05-01', '2021-06-01', '2021-07-01', '2021-08-01',
               '2021-09-01', '2021-10-01', '2021-11-01', '2021-12-01'],
              dtype='datetime64[ns]', freq='MS')
----> 12개 생성되는 것 확인 
'''


# case 2 : 분기별로 12 개 생성
ts_ms = pd.date_range(start='2021-01-01', end=None, periods=12, freq='Q')
print(ts_ms)
print()
'''
result 
DatetimeIndex(['2021-03-31', '2021-06-30', '2021-09-30', '2021-12-31',
               '2022-03-31', '2022-06-30', '2022-09-30', '2022-12-31',
               '2023-03-31', '2023-06-30', '2023-09-30', '2023-12-31'],
              dtype='datetime64[ns]', freq='Q-DEC')
'''