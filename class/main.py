# import numpy as np
# li = [100, 300, 200]
# ar = np.array([100, 300, 200])
# print(li)
# print(ar)
# print(ar + 100)
#[100, 300, 200]
# [100 300 200]
# [200 400 300]

# Vector 와 정수가 곱셈을 하면 반복
print([1,2,3]*3)
#[1, 2, 3, 1, 2, 3, 1, 2, 3]

print("hi! \n"*5)
# hi!
# hi!
# hi!
# hi!
# hi!


# 왼쪽으로 2 번 --> 10 * 2^2
print(10 << 2)
40

# 오른쪽으로 2번 --> 10* 2^-2
# --- 나머지는 버림
print(10>>2)
2


# 정수 사이의 산술비트 연산
x = 21  # 이진수    10101
y = 19  # 이진수    10011
print(x & y)
# 10 --> 17  binary 10001

print(x | y)
# 10 --> 23 binary 10111

print(x ^ y)
# 10 --> 6 binary 00110

#print([True ,True ,False] & [True , True ,False])
# 파이선에서는 &, | , ^ 를 정수 데이터 사이의 연산에만 사용
# ----- python에서는 numpy Library가 엄청 중요하다
import numpy as np
x = np.array([True, True, False])
y = np.array([True, True, False])
print(x & y)


# 데이터 분석 라이브러리에서는 vector 자료형에  & 이나 | , ^  를 사용할 수 있음
# 이를 이용해서 조건에 맞는 데이터를 추출하는데 사용

x = np.array([True, True, False])
z = np.array([100, 151, 200])
print(x > 5)
# true 는 1 false 는 0 이기 때문에

print(z[z > 150])
# z 요소 중에 150 초과인 수만 출력



#singsiuk 이라는 문자열에 name 이라는 이름 붙이기
name = "singsiuk"
print(name)

# print(abs(-3))
# abs = 7
# print(abs(-3))
#
# #키워드인 and 의 기능을 변경하려고 해서 Error 발생
# and = 1
# print(and)

# Operator(연산자)

print(10+30)
# 40
print([100, 200, 300]+[1000, 1000, 1000])
#[100, 200, 300, 1000, 1000, 1000]

# print([100, 200, 300]+10)
#error
#TypeError: can only concatenate list (not "int") to list



