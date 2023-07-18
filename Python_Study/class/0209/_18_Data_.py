# 데이터 저장과 일기 --> 중요!! , 확장자 확인

import numpy as np

# 배열을 생성
ar = np.arange(1, 5)
br = np.arange(4, 17, 4)
# 배열 확인
print(f'ar = {ar}')
print(f'br = {br}')
print()

# 하나의 데이터 저장 시
print("하나의 데이터 저장 --------- ")
np.save('./ar1.npy', ar)
print()

# 읽어오기
print("저장된 npy 데이터 읽어오기 ")
xr = np.load('./ar1.npy')
print(xr)
print()

# 데이터 여러개 저장하기
print("데이터 여러개 저장하기=====")
np.savez('./arar.npy', a=ar, b=br)
print()

# 배열을 여러개 저장한 파일 읽어오기
print("배열을 여러개 저장한 파일 읽어오기 ----- 1")
xr = np.load('./arar.npy.npz')
# 여러개 저장시 압축되어 있어서 데이터를 바로 읽을 수가 없음
print()

print("배열을 여러개 저장한 파일 읽어오기 ----- 2")
# 여러 개를 저장한 경우에는 이름이 인덱스가 된다.
print(xr['b'])






