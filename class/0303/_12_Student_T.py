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


symbols = ['SP500', 'NASDAQCOM', 'DJCA', 'NIKKEI225']

data = pd.DataFrame()
for symbol in symbols:
    data[symbol] = web.DataReader(symbol, data_source='fred')[symbol]

# 나라마다 시장 거래일이 다르기 때문에
data = data.dropna()
print(data.head())

(data/data.iloc[0] * 100).plot()
plt.ylabel(' 주가 수익률 ')
plt.show()

''' 일간 수익률 '''
log_return = np.log(data / data.shift(1))
log_return.hist(bins=50)
plt.show()


''' Q-Q Plot '''
for i, symbol in enumerate(symbols):
    ax = plt.subplot(2, 2, i+1)
    sp.probplot(log_return[symbol].dropna(), plot=ax)
plt.show()

