import seaborn as sns
anscombe = sns.load_dataset("anscombe")
print(anscombe)
print()

print("각 데이터 그룹의 평균 , 포준편차, 상관 계수가 동일 ")
print("수치 데이터만 확인하게 되면 4개 데이터 그룹은 모두 동일한 분포를 가져야한다=================================")
print()
print("I")
print(anscombe[anscombe['dataset'] == 'I'].std())
print()
print("II")
print(anscombe[anscombe['dataset'] == 'II'].std())
print()
print("III")
print(anscombe[anscombe['dataset'] == 'III'].std())
print()
print("IV")
print(anscombe[anscombe['dataset'] == 'IV'].std())
print()

import matplotlib.pyplot as plt

group_1 = anscombe[anscombe['dataset'] == 'I']
group_2 = anscombe[anscombe['dataset'] == 'II']
group_3 = anscombe[anscombe['dataset'] == 'III']
group_4 = anscombe[anscombe['dataset'] == 'IV']

fig = plt.figure()

axes1 = fig.add_subplot(2, 2, 1)
axes2 = fig.add_subplot(2, 2, 2)
axes3 = fig.add_subplot(2, 2, 3)
axes4 = fig.add_subplot(2, 2, 4)

axes1.plot(group_1['x'], group_1['y'])
axes2.plot(group_2['x'], group_2['y'])
axes3.plot(group_3['x'], group_3['y'])
axes4.plot(group_4['x'], group_4['y'])

plt.show()