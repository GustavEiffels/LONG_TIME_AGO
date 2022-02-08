import matplotlib.pyplot as plt


data0 = [100, 200, 300, 400, 800]
# labels  과 함께 출력 // 데이터 개수와 맞아야함
labels = ['a', 'b', 'c', 'd', 'f']

plt.figure(figsize=(5, 3))

# 파이그래프
# % 를 부여
# 추출 구현
plt.pie(data0, labels=labels, autopct='%.1f%%', explode=(1.0, 0, 0, 0, 0),
        colors=["pink", "orange", "yellow", "gray", "green"])

# 파이그래프 출력
plt.show()
