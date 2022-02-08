import matplotlib.pyplot as plt
import csv

# korea.csv 사용

# 이름과 점수를 저장할 list
names = []
scores = []

# korea.csv 파일 열기
f = open('./korea.csv', encoding='ms949')
data = csv.reader(f)

# 이상 없는지 확인
# for i in data:
#     print(i)

# 첫번째 행이 열의 제목 : 넘어가기 next()

next(data)

# 각 줄을 읽어서 첫번째 데이터는 names
# 두번째 데이터는 scores 에 추가
for row in data:
    names.append(row[0])
    scores.append(row[1])

# 기본적으로 읽으면 문자열
print(type(scores[0]))
# reuslt -----> <class 'str'>

# scores 의 모든 데이터를 정수로 변환
# 시각화를 해야하기 때문에
for i in range(0, len(scores), 1):
    scores[i] = int(scores[i])

print(type(scores[0]))
# result -----> <class 'int'>

# histogram 그리기
plt.figure()

# 히스토그램을 그릴 때는 (빈도수 파악) 범주형 데이터가 아닌 경우
# 구간화(binding)을 하는 것이 좋다
plt.hist(scores, bins=3)


plt.xticks(range(0, 100, 25), labels=['', 'Bottom', 'Middle', 'Top'])

# Option
plt.title("Histgram TEST")
plt.xlabel("score")
plt.ylabel("frequency")

plt.show()

