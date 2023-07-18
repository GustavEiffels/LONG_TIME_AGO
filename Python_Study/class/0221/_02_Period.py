import numpy as np
import pandas as pd

import _00_Common

'''period 날짜를 인정한 간격 단위로 변환'''

'''문자열 데이터 생성 '''
date_string = np.array(['03-04-2005',
                        '03-04-1996',
                        '03-04-2015'])

dates =pd.to_datetime(date_string, format='%d-%m-%Y')

print(dates)



'''
result 
:
DatetimeIndex(['2005-04-03', 
                '1996-04-03', 
                '2015-04-03'], 
                dtype='datetime64[ns]', freq=None)
'''

'''
일정한 주기의 데이터로 벼노한 
'''
# case 1 D
print("case 1 D")
pr_days = dates.to_period(freq='D')
print(pr_days)
print()
'''
case 1 D
PeriodIndex(['2005-04-03', '1996-04-03', '2015-04-03'], dtype='period[D]')
'''


# case 2 M
print("case 2 M")
pr_days = dates.to_period(freq='M')
print(pr_days)
print()
'''
case 2 M
PeriodIndex(['2005-04', '1996-04', '2015-04'], dtype='period[M]')
'''

# case 3 A
print("case 3 A")
pr_days = dates.to_period(freq='A')
print(pr_days)
print()
'''
case 3 A
PeriodIndex(['2005', '1996', '2015'], dtype='period[A-DEC]')
'''