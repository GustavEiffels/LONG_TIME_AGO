print("0127")


# 가변 parameter
# 함수의 parameter 에
# * 이 붙어 있으면
# 가변 parameter 이다
# 즉, parameter 의 개수가 정해 지지 않았음 을 의미

def varargs1(*arglist):
    for arg in arglist:
        print(arg)


# 함수에 호출
varargs1("hi", "today is january 27th", "Thursday")
print()
varargs1("hi today is january 27th", "Thursday")
print()
varargs1("hi today is january 27th Thursday")


def varargs21(arg, *arglist1):
    print(f"일반 parameter :{arg}")
    print()
    for arg in arglist1:
        print(arg)


varargs21("hi", "my", "name", "is", "mimi")


# 첫번째 parameter 는 arg 에 대입 되고
# 나머지는 arglist1 에 대입


# # 가변 parameter 앞의 데이터에는 이름을 사용할 수 없다
# varargs21(arg="hi", "my", "name", "is", "mimi")


def varargs3(*arglist3, arg):
    print(f"일반 parameter:{arg}")
    for arg in arglist3:
        print(arg)


# 가변 parameter 뒤의 parameter 는 반드시 이름과 함께 대입
varargs3("one", "two", "three", arg="n?umber")


# 가변 parameter 뒤의 parameter 는 이름과 함께 입력되지 않으면 Error
# varargs3("1", "2", "3", "4")


# ** 이 붙으면 dict
# ** parameter 는 가장 마지막에 위치해야한다 .
def userURLBuilder(server, port, **param):
    uri = f"https://{server}:{port}/"
    # dict 는 순회를 하면 key 값이 return
    queryString = ""
    for attr in param:
        queryString += f"{attr}={param[attr]}&"
    uri += queryString
    print(uri)


# ** 에 값을 대입할 때는 이름과 값을 같이 준다
# 이름은 개발자가 정한 이름을 사용해야한다
userURLBuilder("localhost", "8080", name="sinsiuk", nickname="singsi")


# ** 다음에 새로운 parameter를 만들면 error
# def userURLBuilder0(**param, server1, port1):
#    print("success")


# 재귀함수
# 피보나치 수열의 값을 리턴하는 함수

def fibonacci(x):
    if x == 1 or x == 2:
        return 1
    else:
        return fibonacci(x - 1) + fibonacci(x - 2)


print(fibonacci(10))


# 함수는 일급 객체
# 함수를 변수에 대입할 수 있다 .

def plus(a, b):
    return a + b


def minus(a, b):
    return a - b


# 함수를 변수로 지정
x = plus
y = minus

# 변수를 통해서 함수를 호출
print(x(1, 1))
print(y(200, 100))


# 이 함수는 함수를 parameter 로 받아서 함수가 수행되고
# return
def click(func, n1, n2):
    return func(n1, n2)


print(click(x, 200, 50))


# Closure
def outer():
    '''

    이 함수는 클로저를 알아보기 위해서 작성하였습니다
    '''
    # 함수 안의 내용이 잇다고 가정
    print(x)

    def inner():
        print(x)

    return inner


def testhl():
    print("hello")


f = outer()  # outer 가 return 한 inner 함수를 f 에 대입
print()
f()

testhl.__doc__ = '''
이 내용은 help를 했을 때 보여지는 내용입니다. 
'''

help(outer)
help(testhl)


# 람다 사용
# 2개의 정수를 parameter 로 받아서 더한 결과를 리턴하는 함수
def plus(a, b):
    return a + b


# 람다로 만들기
f = lambda a, b: a + b

# plus 함수와 위의 람다는 같다

print(f(100, 200))


# -10 부터 9 까지 1씩 증가하면서 func 에 대입해서나온 결과를
# list 를 생성하여 return 하는 함수

def g(func):
    return [func(x) for x in range(-10, 10, 1)]


# 이때 parameter 로 lambda를 많이 사용

# print(g(lambda x : x*x))


# map 함수 -- 데이터를 변환해 주는 함수

import datetime

# 0 ~ 9999 까지 list 를 생성
li = [i for i in range(10000)]

# 현재 시간 구하기

# 1. 일반 반복문을 사용
# 시작 시간
s1 = datetime.datetime.now()
for i in li:
    print(i * i, end=" ")

# 종료 시간
s2 = datetime.datetime.now()
print()
print("일반 반복문 처리 시작 시간 : ", s1)
print("일반 반복문 처리 종료 시간 : ", s2)
print("일반 반복문 처리 소요 시간 : ", s2 - s1)


def func00(x):
    return x * x


# 2. 함수를 이용한 처리
# 시작 시간
s3 = datetime.datetime.now()

for case in li:
    print(func00(case), end=" ")

# 종료 시간
s4 = datetime.datetime.now()
print()
print("함수를 이용한 처리 시작 시간 : ", s3)
print("함수를 이용한 처리 종료 시간 : ", s4)
print("함수를 이용한 처리 소요 시간 : ", s4 - s3)

# 3. map과 lambda를 이용하는 경우

# 시작 시간
s5 = datetime.datetime.now()

print(map(lambda x1: x1 * x1, li))

# 종료 시간
s6 = datetime.datetime.now()
print()
print("map 과 lombda 를 이용한 처리 시작 시간 : ", s5)
print("map 과 lombda 를 처리 종료 시간 : ", s6)
print("map 과 lombda 를 처리 소요 시간 : ", s6 - s5)

print("일반 반복문 처리 소요 시간 : ", s2 - s1)
print("함수를 이용한 처리 소요 시간 : ", s4 - s3)
print("map 과 lombda 를 처리 소요 시간 : ", s6 - s5)

print()

li1 = [1, 2, 3, 4, 5, 6, 7]

# 짝수 데이터만 골라내는 작업 실행하기
data = []
for temp in li1:
    if temp % 2 == 0:
        data.append(temp)
print(data)

# filter 를 사용해서 출력하기
print()
print([filter(lambda x2: x2 % 2 == 0, li1)])
print()
print(list(filter(lambda x2: x2 % 2 == 0, li1)))

from functools import reduce

result = reduce(lambda x3, y3: x3 + y3, [100, 200, 300, 700])
print(result)

print("scope 변수의 유효 범위")

y = 1996


# scope 변수의 유효 범위
def outer():
    x = 10
    print(x)
    # 이제부터 y 는 함수 외부에 있는 y
    global y

    def inner():
        # 이제부터 x는 포함한 함수에 있는 x
        # 선언 해줌으로서 전역화시킴
        nonlocal x
        x = 20
        # inner 의 x 를 출력
        # inner 에 x 가 없다면 바깥에 있는 x를 출력
        print(x)

    inner()
    # 여기서는 outer 의 x인 10이 출력
    print(x)


outer()


# OOP // 클래스와 Instance 생성 및 Instance method 호출

# 클래스 생성
class Student:
    # Instance method 생성을 python 에서 할 경우
    # parameter 가 최소 1개는 있어야 한다
    # 첫번째 parameter는 Instance 자신이어야 한다
    def method1(self):
        print("method")

    def method2(self, name):
        print(f"name : {name}")


# Instance 생성
student = Student()

# 1. instance 를 이용한 method 호출 : 바운드 호출
student.method1()

# 2. 클래스를 이용한 instance method 호출 : 언바운드 호출
Student.method1(student)

# parameter 가 두개지만
# 첫번째 parameter 는 자기 자신을 가리키기 때문에
# 첫번째 paraemter 를 제외한 parameter 값을 넣어 줘야함
student.method2("가오나시")

print()


class Student:
    # 클래스의 속성이 된다
    # 클래스를 통해서 읽을수도 있고
    # Instance를 통해서도 읽을 수 있다
    name = "default"


# 클래스를 이용해서 클래스 속성에 접근
print(Student.name)

# Instance 생성
# instance 생성 , () 를 열고 닫기 !
instance = Student()

# Instance 를 이용해서 클래스 속성에 접근
print(instance.name)

# Caution
# 클래스를 이용해서 클래스 속성을 수정
Student.name = "Class Attribute !"
print("클래스를 이용해서 클래스 속성을 수정")
print(Student.name)
print(instance.name)

# Instance 를 사용 해서 수정 했을 때
# Instance 에 name 속성을 만들어서 대입 -- > class 속성은 아무런 변화가 없다.
instance.name = "Instance Attritbute"
print("Instance 를 사용 해서 수정")
print(Student.name)
print(instance.name)

print()


# name 과 score 를 속성으로 갖는 instance 들의 class
# class Student:
#     # 초기화 method self 이외의 parameter 가 없는 초기화 method
#     # 속성의 값을 기본값으로 만들기 위한 경우
#
#     def __init__(self, name="이름", score=0):
#         self.name = name
#         self.score = score
#
#     def getName(self):
#         return self.name
#
#     def setName(self, name):
#         self.name = name
#
#     def getScore(self):
#         return self.score
#
#     def setScore(self, score):
#         self.score = score
#
#     def __del__(self):
#         print("Instance will delete soon")
#
#
# # 인스턴스 생성
# nameScoreInstance = Student()
# print(nameScoreInstance.getName(), ":", nameScoreInstance.getScore())
#
# # ----> 새로운 객체를 만들게 되고 이전 객체는 가리키는 변수가 없으므로 소멸
#
# nameScoreInstance = Student(name="ice", score=20)
# print(nameScoreInstance.getName(), ":", nameScoreInstance.getScore())


# setter 를 지우고 실행
# error  -- 지정된 값이 없어서

# 어떤 Instance 를 소멸시키고자 하는 경우
# 1. Instance를 가리키는 변수에 다른 Instance 를 대입하거나
# 2. None 을 대입해주면 된다


# 일억개

class Coffee:
    # Instance 생성 없이 클래스가 호출가능한 method 가 된다.
    @staticmethod
    def staticmethod():
        print("static method")

    # Instance 생성 없이 클래스가 호출가능한 method 가 된다
    # 이 코드가 있을 때, parameter를 하나 만들어야하는데 , 이 parameter 는
    # class 에 대한 정보를 가지고 있다.

    @classmethod
    def classmethod(cls):
        print("class method")


Coffee.staticmethod()
Coffee.classmethod()
