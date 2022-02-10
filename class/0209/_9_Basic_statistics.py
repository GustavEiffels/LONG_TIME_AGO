import numpy as np

# 1 차원 배열 생성
print("1 차원 배열 생성 ===========")
# 데이터 개수 8 개
ar = np.array([1, 2, 3, 4, 5, 6, 7, 8])
print()

print("1 차원 배열인 ar 을  2차원으로 변경 ")
br = ar.reshape(4, 2)
print()

print(f" 1차원 배열인 ar : {ar}")
print()
print(f" 2차원 배열인 br : {br}")
print()

print("===============")
# 합계
print(f" ar 의 합계 : {np.sum(ar)}")
print()

# 데이터 개수로 나눈 값 : 수학에서 사용
print('데이터 개수로 나눈 값 : 수학에서 사용 ================+++============')
print(f"표준 편차( 자유도 적용 안함 ) : {np.std(ar)}")
print()
# (데이터 개수 -1) 로 나눈 값 : 통계학에서 많이 사용
print('(데이터 개수 -1) 로 나눈 값 : 통계학에서 많이 사용 ================+++============')
print(f"표준 편차( 자유도 1 적용 ) : {np.std(ar, ddof=1)}")
print()

# 3 사분위 수 구하기
print("3 사분위 수 구하기 ================+++============ ")
print(f"3 사분위 수 구하기 : {np.percentile(ar, 75)}")
print()

# 2차원 배열
print("2차원배열 ======================")
print(f"최대 값 : {np.max(br)}")
print()
# 행과 열 단위로 최대 값 구하기
print(f"행 단위 (axis = 0)최대 값 : {np.max(br, axis=0)}")
print(f"열 단위 (axis = 1)최대 값 : {np.max(br, axis=1)}")

# 결과를 원래 데이터의 차원인 2 차원으로 만들어서 리턴
print("결과를 원래 데이터의 차원인 2 차원으로 만들어서 리턴 ====================== ")
print(f"최대 값 : \n{np.max(br, axis=1, keepdims=True)}")

