# 시각화 package

# 1. import
import matplotlib.pyplot as plt


# 꺽은선 그래프

# 2. 그래프를 그릴 영역
plt.figure()

# 3. 옵션을 설정
plt.plot([100, 1000, 300])


# 4. 출력
plt.show()

# 옵션 변경

# 데이터 생성
s1 = [100000, 200002, 900000, 800000]
s2 = [999999, 888288, 787878, 10000000]

# 그래프 크기 설정
plt.figure(figsize=(10, 4))

# 그래프를 그리기
plt.plot(s1, label="0207")
plt.plot(s2, label="0208")
plt.grid()

plt.xlabel('인덱스')
plt.ylabel('테스트')

plt.title('my money')

plt.legend()

# 그래프를 화면에 출력
plt.show()