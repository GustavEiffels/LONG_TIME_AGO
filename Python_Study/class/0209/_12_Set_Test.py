import numpy as np

# 배열 생성하기
ar = np.arange(4)
br = np.array([21, 2, 3, 3, 3, 3, 2])

# 배열이 잘 생성되었는지 확인
print("배열이 잘 생성되었는지 확인====================================")
print(ar)
print(br)
print()

# 중복제거 Unique
print(f" Unique( 중복 제거 ) :{np.unique(br)} ")
print()

# 차집합
print(f" setdiff1d(차집합--> ar 에서 교집합을 제거 ) : {np.setdiff1d(ar, br)} ")
print(f" setdiff1d(차집합--> br 에서 교집합을 제거 ) : {np.setdiff1d(br, ar)} ")
print()

# ar, br 중 한쪽에만 존재하는 data
print(f" setxor1d : {np.setxor1d(ar, br)}")
print()