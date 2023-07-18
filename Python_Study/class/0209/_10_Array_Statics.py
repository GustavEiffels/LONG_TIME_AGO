import numpy as np

# 1차원 배열 생성
print(" 1차원 배열 생성 ")
ar = np.array([1, 2, 3, 4, 5, 6, 7, 8])
print()

# 최대값 구하기
print("최대값 구하기")
print(f"Max : {np.max(ar)}")
print(f"최대값 위치 : {np.argmax(ar)}")
print()

# 배열에 None 이 있을 때
print("배열에 None 이 있을 때")
br = np.array([1, 2, 3, 4, 5, 6, 7, None])
print(br.dtype)
print()

# 누적합
print("누적합 : array 0 부터 계속해서 합산하여 출력")
print(f"누적합 : {np.cumsum(ar)}")
print()

# 원본 데이터 개수 보다 차분을 할때 마다 1 개씩 결과가 줄어든다
print("차분 구하기 ==========")
print(f"1차 차분 : {np.diff(ar)}")
print(f"n차 차분 (n= 2) :{np.diff(ar, n=2)}")
print(f"n차 차분 (n= 3) :{np.diff(ar, n=3)}")
print(f"n차 차분 (n= 4) :{np.diff(ar, n=4)}")