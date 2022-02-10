import numpy as np
help(np.sort)

# array 생성
ar = np.array([99, 2, 13, 1000, 9, 28932])

# 정렬 전 = ar
print(" before ======")
print(ar)
print()

# 정렬 하고 나서 --> br = np.sort(ar)
br = np.sort(ar)
print(" after ======")
print(br)
print()

# 내림차순
# 역순으로 접근하고자 하는 경우 list의 경우 reverse 함수를 이용하지만
# ndarray는 인덱싱을 이용
print(" Descending ======")
br = np.sort(ar)[::-1]
print(br)
print()


# 정렬 알고리즘 설정
# 결과는 같지만 알고리즘이 mergesort로 바뀜
# 정렬 공부 시 selection, bubble , insertion, quick, merge, heap 정렬을 해두는 것이 좋다
print("MergeSort ========")
br = np.sort(ar, kind="mergesort")[::-1]
print(br)
print()
