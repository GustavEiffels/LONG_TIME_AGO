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

scores = list(map(int, scores))

# 영역을 생성
fig = plt.figure()

# Sub 영역을 생성
ax1 = fig.add_subplot(2, 2, 1)
ax2 = fig.add_subplot(2, 2, 2)
ax3 = fig.add_subplot(2, 2, 3)

# 현재 활성화된 영역에 산점도를 출력
plt.plot(scores, 'k--')

# ax1 에 히스토그램 출력
ax1.hist(scores, color='k')

# ax2 에 boxplot 출력
ax2.boxplot(scores)


# 현재 영역에 그려진 그래프를 그림 파일로 저장
fig.savefig('./test1.png')


plt.show()