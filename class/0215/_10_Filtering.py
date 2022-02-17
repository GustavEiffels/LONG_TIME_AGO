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


# 필터링 할 때는 parameter 가 1 개이고 bool을 리턴하는 함수를 이용

# 데이터의 개수가 200이상인 그룹만 추출
def over_200(x):
    return len(x) >= 200

filtering_group = grouped.filter(over_200)
print(filtering_group.head())

# 람다 이용 람다가 한줄자리 이름없는 함수