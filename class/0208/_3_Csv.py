import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='AppleGothic')
plt.rcParams['axes.unicode_minus'] = False

# 음수 출력
plt.rcParams['axes.unicode_minus'] = False

import csv

# 데이터 읽기
f = open('./mokpo.csv', encoding='ms949')
data = csv.reader(f)

# 한글 나오는지 확인용
# for row in data:
#     print(row)


# 첫번째 줄은 열의 제목이므로 제외
next(data)

print("==============================================")

# 필요한 데이터 추출


# 매년 3월 4일 마다 최고 최저 기온을 저장

# 년도와 최고 기온과 최저기온을 저장할 list
year = []
high = []
low = []

for row in data:
    # 첫번째 열에 해당하는 데이터 가져오기 - 날짜
    data = row[0]
    # .을 기준으로 분할 x[0] 는 연도, x[1]은 월 , x[2]은 일
    x = data.split('.')
    if x[1] == '3' and x[2] == '4':
        # 년도를 저장
        year.append(int(x[0]))

        # 최고 기온을 저장
        high.append(float(row[-1]))

        # 최저 기온을 저장
        low.append(float(row[-2]))

# 제대로 저장되었는지 샘플을 출력
for idx in range(0, 20, 1):
    print(year[idx], ":", high[idx], ":", low[idx])


# 그래프 영역 설정
plt.figure(figsize=(10, 4))

# 그래프 생성
plt.plot(high, label="최고 기온",  color="r", linestyle='-', marker='s')
plt.plot(low, label="최저 기온",  color="b", linestyle='-', marker='s')

# 그래프 옵션 설정

# 숫자의 범위 조절
plt.ylim(-20, 20)

# 격자 생성
plt.grid()

# 범례 설정
plt.legend()

# x 축 눈금 설정
plt.xticks(range(0, len(year), 1), year, rotation="vertical")

# 그래프 출력
plt.show()

