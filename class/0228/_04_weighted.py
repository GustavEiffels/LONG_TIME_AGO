import matplotlib.pyplot as plt
import wquantiles
import pandas as pd
import numpy as np
state = pd.read_csv('../python_statistics-main/state.csv')
state.info()
print()

''' 산술 평균 '''
print(f" 산술 평균 : {state['Murder.Rate'].mean()}")
''' Population 비율에 다른 가중 평균 구하기 '''
print(f" 가중 평균 : {np.average(state['Murder.Rate'], weights=state['Population'])}")
''' Population 비율에 따른 가중 중앙값 구하기 '''
print(f" 가중 중앙값 : {wquantiles.median(state['Murder.Rate'], weights=state['Population'])}")
print()


''' 최빈값 '''
print(f' 최빈값 : {state["Murder.Rate"].mode()}')

''' 편차 '''
print(f" 표준 편차 : {state['Population'].std()}")
print(f" IQR : {state['Population'].quantile(0.75) - state['Population'].quantile(0.25)}")

''' 중위 절대 편차 '''
from statsmodels import robust
print(f" 중위 절대® 편차 :  {robust.scale.mad(state['Population'])}")
print()

''' 백분위 수 출력 '''
percentage = [0.05, 0.25, 0.5, 0.75, 0.95]
print(state["Murder.Rate"].quantile(percentage))

''' box plot '''
plt.figure()
state['Population'].plot.box()
plt.show()

''' 도수 분포 표 '''
binnedPopulation = pd.cut(state['Population'], 10)
print(binnedPopulation.value_counts())
print()

''' 구간별로 그룹화해서 빈도수와 약자(Abbreviation)을 같이 출력 '''
# 데이터에 이름 붙이기
binnedPopulation.name = 'binnedPopulation'

# 나누어진 구간 과 state 를 가지고 데이터 프래임 생성
df = pd.concat([state, binnedPopulation], axis=1)
print(df.head())
print()

# 또는
df['binnedPopulation'] = binnedPopulation
print(df.head())
print()

# Population 을 가지고 정렬
df = df.sort_values(by='Population')
print(df.head())
print()

''' 데이터 그룹화 '''
# 그룹화를 저장할 list
groups = []

print("---------------------------------------")

for group, subset in df.groupby(by='binnedPopulation'):
    groups.append({
        '범위': group,
        '개수': len(subset),
        '주이름': ','.join(subset.Abbreviation)
    })
print(pd.DataFrame(groups))

''' 밀도 추정 '''
# 1 . 일반적인 histogram
plt.figure()
state['Murder.Rate'].plot.hist(density=True,
                               xlim=[0, 12],
                               bins=range(1, 12),
                               figsize=(4, 4))

# 2. 밀도 추정
state['Murder.Rate'].plot.density()
plt.show()


