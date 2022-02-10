import numpy as np

ar1 = np.array([1, 10, 3])
ar2 = np.array([2, 3, 4])

# 각 요소를 가지고 연산을 해서 결과를 배열로 리턴
print("각 요소를 가지고 연산을 해서 결과를 배열로 리턴")
print(ar1 > ar2)
print()

# 함수를 이용
print("함수를 이용")
print(np.greater(ar1, ar2))
print()

# broadcast 연산을 이용해서 특정 조건에 맞는 데이터만 추출
print("broadcast 연산을 이용해서 특정 조건에 맞는 데이터만 추출")
print(f'ar1 : {ar1}')
print(f'ar2 : {ar2}')
print()
print("ar2[ar2 > 3]")
print(ar2[ar2 > 3])
print()
print("ar2[ar1 > 3]")
print(ar2[ar1 > 3])
print()

# ----> 이 원리를 데이터 전처리에 많이 사용
# outlier(이상치) 추출에 많이 사용

# 1 보다 크고 10 보다 작은 데이터 추출
print("보다 크고 10 보다 작은 데이터 추출")
print(ar1[(ar1 > 1) & (ar1 < 10)])