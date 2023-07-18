class Student:
    def __init__(self, name="What is your name"):
        self.__name = name

    def getName(self):
        return self.__name

    def setName(self, name):
        self.__name = name

    name = property(getName, setName)

    def __add__(self, other):
        return self.name + other.name


# Instance 생성

st = Student()
print(st.name)
st.name = "심청이"
print(st.name)

st1 = Student()
st1.name = "콩쥐"
print(st1.name)
print(st + st1)


#  ----------

class BaseClase:
    def greeting(self):
        print("Super class ")


class DerivedClass(BaseClase):
    def study(self):
        super().greeting()
        print("Sub Class")


# Instance 생성
ins0 = DerivedClass()

# method 호출
ins0.study()
print("------------")
ins0.greeting()

# -----------
print("---------")

from abc import *


class Super(metaclass=ABCMeta):
    @abstractmethod
    def abstractmethod(self):
        pass


class Sub(Super):
    def abstractmethod(self):
        print("Overriding complete")


su = Sub()
su.abstractmethod()

# ----- \
#
# class Student:
#     def __getattr__(self, name):
#         print(name, "this method is not exists")
#         return getattr(1, name)
#
#     pass
#
#
# st = Student()
# st.getName()


# ----
# sys module 에 있는 path 라는 data 모임 속성을 접근해서 모든 요소를 출력
# import sys
# for attr in sys.path:
#     print(attr)


# matplotlib package 사용
# 확인 pip list --format=columns


# folium package 사용
import folium

m = folium.Map(location=[37.1233, 126.8732], zoom_start=15)
m.save("map.html")

sum = 0.0
for i in range(0, 1000, 1):
    sum = sum + 0.1
print(sum)

print((1.0 - 0.8) == 0.2)

t = 1.0 - 0.8
print(t)
print(t == 0.2)

msg = "BigInteger"
print(msg[3])
print(msg[2])
print(msg[0:3])
print(msg[1:])

msg1 = "Hello"
msg2 = "s"

print(msg1 < msg2)

print(msg1[0] == 'H')

print(" I wanna go a john")
print(" I wanna go \n a john")

print("이름은 {0:10s}이고 나이는 {1:5d} 이다".format("singsiuk", 27))

li = list(range(1, 11))

# 전체데이터 순회하기

# 방법 1 빠른 열거
for element in li:
    print(element)

print()
print("hoo")
print()

# 방법 2 len 사용
for i in range(0, len(li)):
    print(li[i])

# ------ - sort
li = [100, 200, 1, 0, 5, 27]

# sort
li.sort(reverse=True)
# reverse 가 TRUE 이면 Descending
print(li)

# sort 함수를 적용
li1 = ["1", "2", "3", "a", "b", "c", "J", "R", "C"]
li1.sort(reverse=True)
print(li1)
li1.sort(key=str.lower, reverse=True)
print(li1)


row = (1, "ring")
print(row)

row = 1, "song"
print(row)

print(row[0])
# row[0] = 2
# 값을 변경할 수 없다
# 분석하고 결과를 return 할 때 Tuple 을 자주 사용






hashset = {100, 200, 300, 100, 100, 100, 100}
print(hashset)


print("horizon--------------------------------------")

dictionary = {"name": "singsiuk", "age": 27, "hobby": "Programming"}
print(dictionary["name"])

# 없는 key를 사용하면 Error 가 뜬다
# print(dictionary["Nothing in Dictionary"])

# dict 는 빠른 열거를 이용하면 key를 순서대로 접근한다
for key in dictionary:
    print(key, ":", dictionary[key])

print("horizon-----------------------------")

dic1 = {"name": "sing", "age": 2}
dic2 = {"name": "si", "age": 7}
dic3 = {"name": "uk", "age": 27}

table =[dic1, dic2, dic3]
print(table)

# 행 단위 접근
for idx in range(0, len(table)):
    print(table[idx])



# 열 단위 접근
for row in table:
    print(row["name"])




print("horizon-----------------------------")

ar = range(1, 11, 1)

# 원래
map("함수", "데이터의 모임")

# 제곱을 이용해서 생성 가능
result = [i*i for i in ar]
print(result)

# 걸러 내는 것 ---> filter 사용
ar = {"하나", "둘", "셋", "넷", "다섯"}
result = [i for i in ar if len(i)==2]
print(result)


# 이중 데이터
li1 = [1, 2, 3]
li2 = [4, 5, 6]
result = [x+y for x in li1 for y in li2]
print(result)