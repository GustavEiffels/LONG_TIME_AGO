import matplotlib.pyplot as plt
import csv

# korea.csv 사용

# 이름과 점수를 저장할 list
names = []
scores = []

# korea.csv 파일 열기
f = open('./korea.csv', encoding='ms949')
data = csv.reader(f)


next(data)

for row in data:
    names.append(row[0])
    scores.append(row[1])

for i in range(0, len(scores), 1):
    scores[i] = int(scores[i])

print(type(scores[0]))

plt.figure()
plt.boxplot(scores)
plt.show()
