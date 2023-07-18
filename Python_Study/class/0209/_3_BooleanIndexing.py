import numpy as np

ar = np.array([1, 2, 3, 4, 5, 6])

# True 위치의 데이터만 골라서 복제를 해서 리턴
print("True 위치의 데이터만 골라서 복제를 해서 리턴")
xr = ar[[True, False, True, False, True, False]]
print(xr)
print()

# xr[0] 의 데이터를 변경해도 ar 에 영향이 없다
xr[0] = 1000
print(f'ar : {ar}')
print(f'xr : {xr}')

print("And 나 Or 연산자 =================================")
cas1 = [True, False, True, False, True, False]
cas2 = [True, False, True, False, False, True]

br = ar[cas1 and cas2]
# 이거 뒤에오는 cas2 값을 덮어 씌우는 것 같음
print(f'br : {br}')