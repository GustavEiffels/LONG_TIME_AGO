import numpy as np
import pandas as pd

import _00_Common


'''문자열 데이터 생성 '''
date_string = np.array(['03-04-2005 11:35 PM',
                        '03-04-1996 11:35 PM',
                        '03-04-2015 11:35 TM'])


'''
errors='ignore'
---> error  가 발생한 경우 무시한 것 이기 때문에 , 문자열을 기대로 리턴

1. 이 경우 python 의 list 일 경우 그대로 이지만 
2. numpy 나 pandas 의 자료구조로 변경한다면 
3. 나머지도 전부 문자열로 변환이된다.

* numpy 의 ndarray 
* pandas 의 DataFrame
* python 의 list 
에 대한 차이에 대해서 잘 알아야 한다 
'''

# case 1
print([pd.to_datetime(date, format='%d-%m-%Y %I:%M %p',
                      errors='ignore')
       for date in date_string])

# result : ['03-04-2005 11:35 PM', '03-04-1996 11:35 PM', '03-04-2015 11:35 TM']

print()

'''
변경이 되지 않는 값을 없는 값의 구조인 NaT로 변환 
---> 그냥 없는 걸로 하자 
'''
# case 2 // coerce : 강요하다
print([pd.to_datetime(date, format='%d-%m-%Y %I:%M %p',
                      errors='coerce')
       for date in date_string])

'''
예외를 강제로 발생 
'''
# 예외를 발생 시킴
print([pd.to_datetime(date, format='%d-%m-%Y %I:%M %p',
                      errors='raise')
       for date in date_string])
