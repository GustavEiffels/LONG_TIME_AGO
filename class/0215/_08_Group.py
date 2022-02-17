import numpy as np
import pandas as pd
import seaborn as sns

titanic = sns.load_dataset('titanic')
titanic.info()

# age, sex, class, fare, survived 열만 추출
df = titanic[['age', 'sex', 'class', 'fare', 'survived']]
print(df.head())
print()

grouped = df.groupby(['class'])
print(grouped)
print('그룹화를 했을 경우 그룹 객체를 iterator를 이용해서 출력')

for key, group in grouped:
    print('key :', key)
    print('count :', len(group))
    print(group.head())
    print()


# 그룹별 평균
print("그룹별 평균==========")
average = grouped.mean()
print(average)
print()

# 그룹별 선택
print("============그룹별 선택============")
group1 = grouped.get_group('First')
print(group1.head())
print()

# 2개 항목으로 그룹화
print("========== 2개 항목으로 그룹화 ==========")
group_two = df.groupby(['class', 'sex'])
for key, group in group_two:
    print("key : ", key)
    print("Count : ", len(group))
    print(group.head())
    print()