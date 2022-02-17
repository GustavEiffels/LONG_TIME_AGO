# 예외가 발생하는 상황

def ten_div(x):
    return 10 / x


try:
    print(ten_div(2))

    print(ten_div(0))
# 해당 부분에서 Error 발생
# 0으로 나누면 Infinity

# 예외가 발생했을 때 message 출력
except Exception as e:
    print(e)
else:
    print("예외가 발생하지 않은 경우 수행 ")

finally:
    print("System Over")
# 프로그램이 정상적으로 실행이 되는 예외 처리


print("horizon--------------------")


# 예외 강제 발생
def test(x):
    if x > 10:
        raise Exception("숫자가 너무 크다")
    return 10 / x


# 기본적인 예외 처리 구문을 이용해서 예외가 발생하더라도 중지되지 않도록 한다
try:
    print(test(2))
    print(test(14))
    # 예외가 발생해서 프로그램이 중단

# 예외가 발생했을 때 예외 처리 message 출력
except Exception as e:
    print(e)

# print("horizon--------------------")
# score = 101
# assert score <= 100, "The Score have not Over 100"
# print(score)

print("horizon--------------------")
import time

print(time.time())  # float 으로 현재시간 리턴
print(time.localtime())  # struc_time 형식으로 현재 시간 리턴

time.sleep(1)

print(time.time())

print("horizon--------------------")
# datetiem 모듈
import datetime

# 현재 시간을 가지고 생성
dt = datetime.datetime.now()

# 날짜 추출
print(dt.date())

# 시간 추출
print(dt.time())

# 년도만 추출
print(dt.date().year)

# 시간만 추출
print(dt.time().hour)

# 문자열로 변환
s = dt.strftime('%Y년 %m월 %d일 %H시 %M분 %S초')
print(s)

# 문자열을 가지고 datetime 만들기
dt1 = datetime.datetime.strptime("1996-03-04 15:46", "%Y-%m-%d %H:%M")
print(dt1)

print("horizon--------------------")
# 날짜 간의 차이
import datetime

dt1 = datetime.datetime.now()
dt2 = datetime.datetime(1996, 3, 4, 16)

td = dt1 - dt2
print(td)

print(td.days, "일 지남 ")

print("horizon--------------------")
# fractions  모듈의 모든 내용을
# fractions 라는 이름으로 묶어서 가져오기
import fractions

result = fractions.Fraction(5, 7) + fractions.Fraction('6/7')
print(result)

# fractions  모듈의 모든 내용을
# fractions 라는 이름으로 묶어서 가져오기
# fractions 의 모듈의 Fraction을 현재 모듈에 포함시켜서 가져오기
# fractions 를 제외하고 사용해도 된다
from fractions import Fraction

result1 = Fraction(5, 7) + Fraction('5/7')
print(result1)

print("horizon--------------------")
# 실수 표현을 정확하게 해주는 모듈

from decimal import Decimal

result = 0.0
for i in range(0, 100, 1):
    result = result + 0.1
print(result)

# Decimal 로 변환해서  실행
k = Decimal('0.0')
for i in range(0, 100, 1):
    k = k + Decimal("0.1")

print(k)

print((1.0 - 0.8) == 0.2)
print((Decimal(1.0) - Decimal(0.8)))
print(Decimal('1.0') - Decimal('0.8'))
# compare 는 호출하는 데이터가 크면 양수(0) , 같으면 0 , 작으면 음수(-1)
print(Decimal('1.0') - Decimal('0.8').compare(Decimal('0.2')))

print("horizon--------------------")
# 랜덤 모듈 - Sampling 이나 game에 많이 사용
import random

# 0~ 1 사이 실수
print(random.random())

# 1 ~ 100 사이 숫자 무작위
print(random.randint(1, 100))

# seed 고정 - 정해진 숫자가 순서대로 return
# 여기서 42 의 의미는 아무것도 없다

print(random.randint(1, 100))

print("horizon--------------------")
li = ["project", "have to", "finish", "as soon as", "possible"]
# 비 복원 추출 이라서
# 동일한 데이터가 추출될 수 없다
print(random.sample(li, 5))
print("horizon--------------------")

# 복원 추출 - 동일한 데이터가 추출 될 수 있다.
for i in range(0, 5, 1):
    print(random.choice(li))

print("Horizon ---------------------")
# re 정규식 모듈
import re

match = re.match('[0-9]', '1234')
print(match)
# 정규식에 해당하는 문자가 있어서 Instance 를 리턴


# 정규식에 해당하는 문자가 없기 때문에 None 을 리턴
match = re.match('[0-9]', 'ABCD')
print(match)

print("Horizon------------------")
import re

# 정규식 객체 생성
# 대소문자 구분하기 때문에 'the' 만 출력
p = re.compile('the')
print(p.findall('The Hello the cat'))

# 대소문자 구분하지 않고 검색하기 때문에
# 'The' 'the' 출력
p = re.compile('the', re.I)
print(p.findall("The Hello the cat"))

# 주민등록번호 패턴 검색
p = re.compile("(₩d{6})-?(₩d{7})")
num = '123456-1234567'
if p.search(num) != None:
    print("올바른 주민등록번호 형식입니다.")

else:
    print("올바른 주민등록번호 형식이 아닙니다")

# 불용어 제거
result = re.sub("-", "", num)
print(result)

print("Horizon-------------------------------------------------------")

import os.path
import time

print("최종 수정 시간 : ", os.path.getmtime(
    '/Users/mac/Desktop/image'
))
print("최종 수정 시간 : ", time.gmtime(os.path.getmtime(
    '/Users/mac/Desktop/image'
)))

print("Horizon-------------------------------------------------------")
import glob

# 현재 디렉토리에 존재하는 py 확장자를 가진 모든 파일이름을 가져온다
print(glob.glob("./*.py"))

print("Horizon-------------------------------------------------------")
# 운영체제 관련 모듈
import os
import sys

# 다른곳에서 파이선 실행시 가정 먼저 확인하는 정보

# 현재 작업 디렉토리 확인
print("현재 작업 디렉토리 확인")
print(os.getcwd())
print()

# 현재 Encoding 방식
print("현재 Encoding 방식")
print(sys.getdefaultencoding())
print()

# 현재 참조하는 모듈의 순서
print("현재 참조하는 모듈의 순서")
print(sys.path)
print()

print("Horizon-------------------------------------------------------")
print("COPY")

# Scala 데이터 참조
a = 1
print(id(a))
print()

a = 2
print(id(a))
print()

# a에 저장된 데이터가 다르기 때문에 다른 id 값을 가진다

# 저장해 둔 1 을 가리키는 것이기 때문에 a가 1 일때 id 값과 동일하다
b = 1
print(id(b))
print()

# 지금 이 code 에서
a = 10
# b 는 10 이되고
b = a

# a 는 20 을 가리키기 때문에
a = 20

c = 10
print(id(a))
print(id(b))
print(id(c))

# 가리키는 위치가 다르다
print(id(a) == id(b))

# c또한 가리키는 곳이 10 이고 , b 도 10을 가리키기 때문에 id 값이 일치한다.
print(id(c) == id(b))

print("Horizon-------------------------------------------------------")
print("Python 의 Vector ")
print()

ar = [100, 200, 300]
br = ar
print()

# 세부 데이터를 변경하였음으로 br 에 영향을 준다
print(ar)
print(br)
print()
ar[0] = 300
print(ar)
print(br)
print()

# ar 의 참조 자체가 변경된 것이므로 ar 과 br 은 아무런 관계가 없다
print(ar)
print(br)
print()
ar = [300, 200, 100]
ar[0] = 700
print(ar)
print(br)

print("Horizon-------------------------------------------------------")
print("COPY Module")
import copy

ar = [100, 200, 300]

# 얕은 복제를 이용 - 새로운 곳에 복사를 해서 그 곳의 id를 리턴
br = copy.copy(ar)
print(ar)
print(br)

print('middle line----')
# 새로 복제를 했기 때문에 ar 을 변형시켜도 br 에는 영향이 없음
ar[0] = 800
print(ar)
print(br)

# 문제가 되는 경우
print('middle line 2 ----')
ar = [[1, 2, 3], [4, 5, 6]]

# list안에 다른 list가 존재하는 경우 copy로 복제를 해도
# 내부 데이터를 변경하면 복제된 데이터에 영향을 준다
br = copy.copy(ar)
print(ar)
print(br)
ar[0][0] = 8920
print("result -----")
print(ar)
print(br)

# list 안에 다른 list가 존재하는 경우 deepcopy 로 복제하면
# 재귀적으로 복제를 하므로 위와 같은 현상이 없어진다
br = copy.deepcopy(ar)
print(ar)
print(br)
ar[0][0] = 989999999
print("result -----")
print(ar)
print(br)

print("Horizon-------------------------------------------------------")
print("Memory 정리")

# class Temp:
#     # Instance 가 memory에서 소멸될 때 호출되는 함수 --> 소멸자
#     def __del__(self):
#         print("Temp 클래스의 인스턴스가 파괴 된다.")


# 인스턴스가 생성 reference count 가 1
# obj = Temp()
#
# # 인스턴스를 다른 변수가 가리키도록 하는 것
# # obj1 = obj
# # reference count 가 1 증가한다
#
# obj = 1
# 이전에 만들어진 인스턴스 대신에 새로 만들어진 인스턴스를 가리킨다
# 이전에 만들어진 인스턴스의 reference count 는 1 감소한다 .

# print("middle-------------")
# #참조 횟수가 1
# obj = Temp()
#
# # 참조를 복사했음으로 참조횟수 2
# obj1 = obj
#
# # 다른 인스턴스를 참조했으므로 참조횟수가 1 줄어들어 1이 된다.
# obj = Temp()
#
# print("middle-------------")
#
# import weakref
# # 참조 횟수가 1
# obj = Temp()
#
# # 참조 횟수를 변경시키지 않고 참조를 복사
# obj1 = weakref.ref(obj)
#
# # 다른인스턴스를 참조했으므로 참조횟수가 1 줄어들어서 1이 된다 .
# obj = Temp()


print("------------------ Horizon")
print("Queue 모듈 ")
import queue

li = ['a', 'b', 'c', 'd']

# Que를 생성해서 데이터를 추가
alphabet = queue.Queue()

for x in li:
    alphabet.put(x)

print(alphabet)

# que 에서 꺼낼때는 get() 을 사용
print(alphabet.get(0))
print(alphabet.get(0))
print(alphabet.get(0))
print(alphabet.get(0))

print('------------- PriorityQueue = 우선 순위 큐 ')
li1 = ['다', '하', '사', '가']

hangle = queue.PriorityQueue()
for x in li1:
    hangle.put(x)
print(hangle.get(0))
print(hangle.get(0))
print(hangle.get(0))
print(hangle.get(0))

print('-------------LifoQueue = 스택 ')
li2 = ['다', '하', '사', '가']

hangle = queue.LifoQueue()
for x in li2:
    hangle.put(x)
print(hangle.get(0))
print(hangle.get(0))
print(hangle.get(0))
print(hangle.get(0))

print("------------------ Collection 모듈의 Counter class 를 이용한 집계 ")
from collections import Counter

portfolio = [
    ("one", 1, "일"),
    ("two", 2, "이"),
    ("three", 3, "삼"),
    ("two", 2, "이"),
    ("three", 3, "삼"),
    ("two", 2, "이"),
    ("three", 3, "삼"),
    ("two", 2, "이"),
    ("three", 3, "삼"),
    ("two", 2, "이"),
    ("three", 3, "삼"),
]

total_shares = Counter()

# # 데이터 순회
# for alpha, fig, han in portfolio:
#     total_shares[alpha] += 1
#
# print(total_shares)
#
# print("Thread ----------------------------------")
# # 일반 함수 호출
# import threading, time
#
#
# def threadEx(id):
#     for k in range(0, 10, 1):
#         print('id={0}--->{1}'.format(id, k))
#         time.sleep(1)
#
#
# for j in range(0, 2, 1):
#     threadEx("{0}번 thread".format(j))
#
# print("CallBack 을 이용한 함수를 지정한 thread  생성 및 시작 호출 ")
# import threading, time
# def threadEx(id):
#     for k in range(0, 10, 1):
#         print('id={0}--->{1}'.format(id, k))
#         time.sleep(1)
#
# for i in range(2):
#     arg = "{0}번 thread".format(i)
#     th = threading.Thread(target=threadEx, args=(arg, ))
#     th.start()

print("상속을 이용한 thread 생성 및 시작 ")
import threading, time

class ThreadEx(threading.Thread):
    def run(self):
        for i in range(0, 5, 1):
            print('id={0}--->{1}'.format(self.getName(), i))
            time.sleep(1)

for i in range(0, 2, 1):
    th = ThreadEx()
    th.start()