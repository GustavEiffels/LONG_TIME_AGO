import numpy as np

# 1 차원 배열 생성
ar = np.array([1, 2, 3, 4, 5, 6])

# Matrix 라고 부를 때 ---> array 행 과 열의 개수가 같다

# 1차원 배열을 생성
matrix = np.array(range(1, 21))
print("before ----------- ")
print(matrix)
print()

# matrix를 4행 5 열의 2차원 배열로 변환
matrix = matrix.reshape(4, 5)
print("After ----------- ")
print(matrix)


# Indexing
print("--------------------Indexing ")
# 1 차원 배열에서 하나의 요소를 추출
print("1 차원 배열에서 하나의 요소를 추출")
print(ar[1])
print()

# 2 차원 배열에서 하나의 요소를 추출
print("2 차원 배열에서 하나의 요소를 추출")
print(matrix[1, 2])
print(matrix[1][2])
print()


print("----------------------------------")
print("2 차원 배열에서 하나의 Index를 대입해서 추출 , 1행 전체")
print(matrix[1])
print()

# 특정위치 출력
print("특정위치 출력----------------------")
print(f"ar : {ar}")
print()

# 특정 위치 부터 끝까지
print("특정 위치 부터 끝까지")
print(ar[3:])
print()

# 시작 위치 부터 특정 위치 까지
print("시작 위치 부터 특정 위치 까지")
print(ar[:3])
print()

# 일반적인 인덱싱 == 데이터의 view
print("일반적인 인덱싱 == 데이터의 view")
print(f"ar : {ar}")
print()

# ar 의 0 번째 데이터를 변경
print("ar 의 0 번째 데이터를 변경")
ar[0] =10000
print(ar)
print(f"ar : {ar}")
print()

# xr = ar 로 지정
print("xr = ar 로 지정")
xr = ar[0:3]
xr[0] = 999999
print(f"ar : {ar}")
print()

# 복제를 하고자 하면 인덱싱한후 copy() 호출
print("복제를 하고자 하면 인덱싱한후 copy() 호출")
br = ar[0:3].copy()
br[0] = 111111
print(f"ar : {ar}")
print(f"br : {br}")
print()

