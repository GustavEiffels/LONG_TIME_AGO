import matplotlib.pyplot as plt
import csv
from matplotlib import rc
rc('font', family='AppleGothic')
plt.rcParams['axes.unicode_minus'] = False

f = open('./Covid19Vaccine01.csv')
data = csv.reader(f)
next(data)
# for row in data:
#     print(row)

# row[0]
day = []

# row[2]
location = []

# row[3]
firstcnt = []

city = input("Please insert a city :")

for row in data:
    date = row[0]
    x = date.split('_')
    if row[2] == city and x[0][0:4] == '2022':
        day.append(f'{x[0][4:6]} 월 {x[0][6:]} 일')
        location.append(row[2])
        firstcnt.append(int(row[4]))


for idx in range(0, len(day)):
    print(f'{day[idx]} : {location[idx]} : {firstcnt[idx]}')

plt.figure(figsize=(10, 4))
plt.plot(firstcnt, label="2022 년 이후 1일 백신 2차 접종자 수", color='black', marker='s', markersize=1)
for i in range(len(day)):
    y = firstcnt[i]
    plt.text(day[i], y+0.5, '%.0f' %y, ha='center', va='bottom', size=8)

plt.bar(day, firstcnt)
plt.xticks(range(len(day)), day, rotation='vertical')
plt.title(f"{city} 백신 현황 ")
plt.legend()
plt.show()