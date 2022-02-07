import random

print(True and False)
# 정수 데이터도 논리연산이 가능하다
print(1 and 1)

# 복합 할당 연산자
x = 10

x += 20

print(x)

# <자료형 확인>
x = "Hello"
print(type(x))
# <class 'str'> : 문자열

y = (10, 30)
print(type(y))
# <class 'tuple'> : 튜플형식

# < 데이터의 저장 구분자 확인 >
print(id(x))
# 140533613072944


help(print)

print("Hello")
# 출력을 한 후 줄 바꿈
print("hi")

# 여러 개의 데이터 출력

print("Hi", "-mickey")

# 여러 개의 데이터 사이에 구분자를 설정
print("hi", "my Name is", "Angel", sep='-')

# 출력한 후 줄바 꿈 대신 다른 내용을 출력
print('python is', end='--')
print("is an amazing Program Language")

print("파이는 %.2f" % (3.141592))

print('{0} is a {1} '.format('mickey', 'mouse'))
# mickey is a mouse

first = "Hamberger"
second = "food"

print(f"{first} is a {second}")

# if
var = True
if var:
    print("bool 데이터를 가지고 수행 ")

var = 11
if var:
    print("숫자를 사용해서 수행 ")  # 0 이 아닌 숫자는 True

var = [10, 20, 3]
if var:
    print("데이터 목록을 가지고 수행")  # 데이터가 존재하면 True

var = []
if var:
    print("데이터 목록을 가지고 수행")

time = random.randint(1, 20)
if time > 10:
    print(time)
    print("A")
else:
    print(time)
    print("B")

# 조건문을 이용한 변수의 값 할당
result = "success"
score = random.randint(1, 100)

if score >= 51:
    print(score)
    print(result)
else:
    print(score)
    result = "Try again"
    print(result)

result = "success" if score >= 51 else "Try Again"
print(score)
print(result)

# elif

# 하나의 숫자를 입력 받아서
score = random.randint(1, 100)

# 90-100 --> A
# 70-90 --> B
# 50-70 --> C
# 나머지 ----> D

if 90 <= score:
    print(score)
    print("A")
elif 70 <= score < 90:
    print(score)
    print("B")
elif 50 <= score < 70:
    print(score)
    print("C")
else:
    print(score)
    print("D")

# while 1 부터 10 까지 출력
i = 1
while i <= 10:
    print(i)
    i = i + 1

# https://www.donga.com/news/search?p=데이터의 시작번호&query= 검색어

# 1 page = 1 번에서 시작
# 2 page = 16 번에서 시작
# 3 page = 31 번에서 시작
# 검색어를 입력받아서 1 page 부터 10 page 까지 크롤링 할 수 있는 URL 출력
query = input("insert what you want ");
idx = 1
while idx < 11:
    print(f"https://www.donga.com/news/search?p={1 + 15 * (idx - 1)}" + f"&query={query}")
    idx += 1

i = 0
while i < 5:
    print(i)
    i = i + 1
    if i > 3:
        break
# while 다음의 else 는 반복문이 전부 수행된 경우
# --> 중간에 종료되지 않는 경우에만 수행
else:
    print("반복문 모두 실행 후 종료")

s = "Test String"

for ch in s:
    print(ch)

pl = ["java", "python", "Sql"]
print((dir(pl)))
for language in pl:
    print(language)

dic = {"1": "one", "2": "two", "3": "three"}
print(dir(dic))
for imsi in dic:
    print(imsi)

# Range
print(type(range(0, 0, 1)))
print(dir(range))

for idx in range(0, 10, 2):
    print(idx)

# 제어문 중첩

i = 0
j = 0
k = "*"
for i in range(1, 6, 1):  # 5줄
    for j in range(1, i + 1, 1):  # 각줄은 1 번부터 줄 번혹까지
        print('*', end='')  # * 을 출력하고 줄바꿈을 하지 않도록
    print()

for i in range(1, 6, 1):
    if i <= 3:
        for j in range(1, i + 1, 1):
            print("*", end='')
    else:
        for j in range(1, 7 - i, 1):
            print('*', end='')
    print()

for i in range(1, 6, 1):
    j = i + 1 if i <= 3 else 7 - i
    for j in range(1, j, 1):
        print('*', end='')
    print()

# Continue  와 Break


# 3 까지 출력하고 멈춤
for i in range(1, 10):

    if i % 3 == 0:
        print(i)
        break
print()

# 3의 배수를 건너뛰고 출력
for i in range(1, 10):
    if i % 3 == 0:
        print(i)
        continue
print()

# 피보나치수열
# 1, 1, 2, 3, 5, 8, 13, 21 ,34, 55 ,89
# 첫번째와 두번째는 1
# 세번째 부터 앞의 2개의 항의 합
# 15항의 피보나치 수열의값을 재귀를 쓰지않고 출력

n1 = 1
n2 = 1
fibo = 1
for i in range(3, 16, 1):
    fibo = n1 + n2
    # n2 = n1
    # n1 = fibo
    # 위처럼 해도 되지만 파이썬은 동시에 값을 넣는 것도 가능함
    n2, n1 = n1, fibo
    # 위와 같이 파이썬은 여러개의 변수에 여러값을 한꺼번에 대입하는 것이 가능
print(fibo)

print(dir(__builtins__))

# 최대값을 구해주는 함수
print(max([100, 200, 3000]))

print(help(max))


# 함수의 정의와 호출
def func():
    ment = 'No Parameter No Return Value'
    print(ment)


#
# func()
# func()
#
#
# # 매개변수를 이용한 데이터 수정
#
# def callByValue(n):
#     n = n+1
#     print(f"함수 내부 : {n}")
#
# def callByReference(ar):
#     ar[0] = ar[0] + 1
#     print(f"함수 내부 : {ar}")
#
#
# x = 100
# callByValue(x)
# print(f"x : {x}")
#
# xr = [100, 200, 300]
# callByReference(xr)
# print(f"xr : {xr}")
#

# Parameter 의 기본값과 이름을 이용한 대입

print(help(max))


# ----> 업로드 할꺼면 반드시 해줘야함


# 2개의 데이터를 대입하면 덧셈을 해서 결과를 리턴하는 함수
def func1(x, y=0):
    return x + y


print(func1(1, 3))  # 2개의 데이터를 모두 대입했으므로 1+3
print(func1(1))  # 하나의 데이터만 대입했으므로 두번째 데이터는 기본값 0 -> 1+0
print(func1(y=2, x=30))
# Parmater의 이름과 함께 대입,
# 대입순서를 변경해서 대입해도 출력이 된다.


# 합계 구해주는 함수는 sum
help(sum)

print(sum([100, 300, 200], start=400))


#

def func3(first1, second2):
    print(f"first number parameter : {first1}")
    print(f"Second Number Parameter : {second2}")


# 원래 사용하는 방식
func3("하나", "둘")
func3("아메", "리카노")

# 순서를 바꾸고 싶다면
func3(second2="바꾸자", first1="순서를")

# 매개변수의 unpacking
func3(*["Coffee", "Bean"])

# ERROR
# func3(["Coffee", "Bean"])

#Dict 의 경우
func3(*{"second2": "hi", "first1": "bye"})
func3(**{"second2": "hi", "first1": "bye"})