import numpy as np
import pandas as pd
import seaborn as sns
from scipy import stats as sp

# 시각화 패키지
import matplotlib.pyplot as plt

# 시각화에서 한글을 사용하기 위한 설정
import platform
from matplotlib import font_manager, rc

if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# 시각화에서 음수를 표현하기 위한 설정
import matplotlib

matplotlib.rcParams['axes.unicode_minus'] = False


import pandas_datareader.data as web
import datetime

# 날짜 생성
start = datetime.datetime(2021, 7, 20)
end = datetime.datetime(2022, 3, 3)

samsung = web.DataReader('005930.KS', "yahoo", start, end)
print(" -- Samsung 주가 -- ")
print(samsung)

symbols = {
        'SPASTT01USM661N',
        'SPASTT01JPM661N',
        'SPASTT01EZM661N',
        'SPASTT01KRM661N'
}

data = pd.DataFrame()
for sym in symbols:
    data[sym] = web.DataReader(
        sym, data_source="fred", start="2020-07-30", end="2022-03-03")[sym]

print(sym)

# data.columns =['US', 'JP', 'EZ', 'KR']
# data = data/data.iloc[0] * 100
# styles = ['b--', 'g--', 'c:', 'r-']
# data.plot(style=styles)
# plt.title('세계 주요국의 10년 주가')
# plt.show()


''' 나스닥 지수를 가져와서 시각화 하기 '''
symbol = "NASDAQCOM"


data = pd.DataFrame()

data[symbol] = web.DataReader(symbol,
                              data_source='fred',
                              start='2020-07-30',
                              end='2022-03-01')[symbol]
# data = data.dropna()
# print(data.head())
# data.plot(legend=False)
# plt.xlabel('날짜')
# plt.title(' 나스닥 지수 ')
# plt.show()


''' 일간 변화량 확인 '''
daily_return = data.pct_change().dropna()
print(daily_return.head())

# pct_change ---> 첫번째 날 Nan 출력
# drop 해주어야한다 .

'''——> 평균과 표준 편차 구하기'''
mean = daily_return.mean().values[0]
std = daily_return.std().values[0]

print(' 평균 ', mean)
print()
print(' 표준 편차 ', std)
print()

sns.distplot(daily_return, kde=False)
ymin, ymax = plt.ylim()
plt.vlines(x=mean, ymin=0, ymax=ymax, ls='--')
plt.ylim(0, ymax)
plt.title(' 나스닥 지수의 일간 수익률 분포 ')
plt.xlabel(' 일간 수익률 ')
plt.show()

