import pandas as pd

import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

''' data Load '''
tips = sns.load_dataset('tips')
print(tips)
tips.info()


''' 산점도 '''
# sns.jointplot(x='total_bill', y='tip', data=tips, kind='scatter')
# plt.show()

''' 육각형 그래프 '''
# 데이터가 많으면 진한 색상으로 표시
# sns.jointplot(x='total_bill', y='tip', data=tips, kind='hex')
# plt.show()

''' 등고선 그래프 '''
# 데이터가 3개 정도 있으면 좋다
x = np.arange(-1, 1, 0.1)
y = np.arange(-1, 1, 0.1)

''' 2 차원 배열 2개로 이차원 배열을 생성 '''
X, Y = np.meshgrid(x, y)
print(X)
print()
print(Y)

Z = np.exp(-(X**2+Y**2))

''' 등고선 그래프 그리기 --> contour (X , Y , Z )'''
# plt.contour(X, Y, Z, levels=np.arange(-1, 1, 0.05), cmap='seismic')
# plt.colorbar()
# plt.show()

''' 범주형 데이터와 범주형 데이터의 시각화 '''
''' 분할표 이용 '''
lc_loans = pd.read_csv('../python_statistics-main/lc_loans.csv')
lc_loans.info()

''' pivot table 사용 '''
crosstable = lc_loans.pivot_table(index='grade',
                                  columns='status',
                                  aggfunc=lambda x:len(x),
                                  margins=True)
print(crosstable)
print()

''' 보기가 좋지 않다 '''
# 분할표의 데이터를 0 에서 1 사이의 실수로 변경
# 1. All 제거
df = crosstable.copy().loc['A':'G']
print(df)
print()

df.loc[:, 'Charged Off':'Late'] = \
    df.loc[:, 'Charged Off':'Late'].div(df['All'], axis=0)

df['All'] = df['All']/sum(df['All'])
print(df)
print()

''' 범주형 과 수치형 데이터의 시각화 '''
airline_stats = pd.read_csv('../python_statistics-main/airline_stats.csv')
print(airline_stats)

airline_stats.boxplot(by='airline', column='pct_carrier_delay')
plt.show()

''' violin 은 일반적으로 하나의 항목에 대해서 그림 '''
sns.violinplot(data=airline_stats['pct_carrier_delay'])
plt.show()

sns.violinplot(data=airline_stats, x='airline', y='pct_carrier_delay', color='red')
plt.show()