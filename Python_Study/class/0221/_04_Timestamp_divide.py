import pandas as pd

import _00_Common

'''
stock-data.csv 파일을 읽어오기 

Date 의 열의 데이터를 날짜로 변경
---> 이후 새로운 열로 추가 
'''

# 읽어 오기 ---> 읽어 온다음 info() 확인하는 것 중요
df = pd.read_csv('stock-data.csv')
df.info()
print()

'''
<class 'pandas.core.frame.DataFrame'>
RangeIndex: 20 entries, 0 to 19
Data columns (total 6 columns):
 #   Column  Non-Null Count  Dtype 
---  ------  --------------  ----- 
 0   Date    20 non-null     object
 1   Close   20 non-null     int64 
 2   Start   20 non-null     int64 
 3   High    20 non-null     int64 
 4   Low     20 non-null     int64 
 5   Volume  20 non-null     int64 
dtypes: int64(5), object(1)
memory usage: 1.1+ KB

-----> Date 가 object 로 나오는 것을 확인할 수 있다.
'''


# Date 의 열의 데이터를 날짜로 변경해서 새로운 열로 추가
df['newDate'] = pd.to_datetime(df['Date'])
df.info()
print()
'''
<class 'pandas.core.frame.DataFrame'>
RangeIndex: 20 entries, 0 to 19
Data columns (total 7 columns):
 #   Column   Non-Null Count  Dtype         
---  ------   --------------  -----         
 0   Date     20 non-null     object        
 1   Close    20 non-null     int64         
 2   Start    20 non-null     int64         
 3   High     20 non-null     int64         
 4   Low      20 non-null     int64         
 5   Volume   20 non-null     int64         
 6   newDate  20 non-null     datetime64[ns]
dtypes: datetime64[ns](1), int64(5), object(1)
memory usage: 1.2+ KB
-----> newDate column 이 생성되고 type 이 datetime64 인 것을 확인 
'''

# 년도 와 요일 분리
df['Year'] = df['newDate'].dt.year
df['WeekNum'] = df['newDate'].dt.weekday
df['weekName'] = df['newDate'].dt.day_name()
print(df.head())
print()
'''
         Date  Close  Start   High  ...    newDate  Year   WeekNum   weekName
0  2018-07-02  10100  10850  10900  ... 2018-07-02  2018       0     Monday
1  2018-06-29  10700  10550  10900  ... 2018-06-29  2018       4     Friday
2  2018-06-28  10400  10900  10950  ... 2018-06-28  2018       3   Thursday
3  2018-06-27  10900  10800  11050  ... 2018-06-27  2018       2  Wednesday
4  2018-06-26  10800  10900  11000  ... 2018-06-26  2018       1    Tuesday
'''