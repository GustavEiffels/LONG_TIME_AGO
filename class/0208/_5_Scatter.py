import matplotlib.pyplot as plt
import csv
from matplotlib import rc
rc('font', family='AppleGothic')
plt.rcParams['axes.unicode_minus'] = False

# 입력받은 도시의 성별, 나이별 인구 분포를 확인
f = open('./pop.csv', encoding='ms949')
data = csv.reader(f)

# 도시 이름 입력받기
city = input("Please insert a city")

# 남성 여성 따로 저장
male = []
female = []

for row in data:
    if city in row[0]:
        for i in range(24, 45, 1):
            # 남자 데이터만 정수로 변환해서 저장
            male.append(int(row[i].replace(',', '')))
            female.append(int(row[i+23].replace(',', '')))


# 산포 그리기
#  색상은 20가지를 쓸것이다
# marker 는 점 모양
plt.figure(figsize=(5, 12))
plt.scatter(x=male, y=female, marker='1', c=range(0, 21, 1),
           alpha=0.5, cmap='jet')

plt.colorbar()
plt.title(f"{city}의 인구분포!! 산포도 Version ")
plt.xlabel(f'{city} 의 남성')
plt.ylabel(f'{city} 의 여성')


plt.show()

