# Seaborn 의 titanic 데이터를 가져와서 class 별로 그룹화
import numpy as np
import pandas as pd
import seaborn as sns

titanic = sns.load_dataset('titanic')
grouped = titanic.groupby(['class'])

'''
for key, group in grouped:
    print(key)
    print(group.head())
'''

# 모든 열의 표준편차를 구해서 출력
print(grouped.std())

print("특정 열의 표준 편차를 구해서 출력")
# 특정 열의 표준 편차를 구해서 출력
print(grouped['fare'].std())
print()

# 사용자 정의 함수를 적용  - 최대값에서 최소값을 밴 값을 리턴
def min_max(x):
    return x.max()-x.min()

print(grouped.agg(min_max))

# 열별로 다른 함수
print(grouped.agg({'fare': {'min', 'max', 'sum'},'age': 'mean'}))

# 변환
# z -score 를 구해주는 함수
# 평균을 뺀 값을 표준편차로 나누어서 리턴하는 함수
def z_score(x):
    return(x-x.mea()/x.std())

