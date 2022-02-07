'''
1.이름 과 국어, 영어, 수학 점수를 저장할 수 있는 클래스를 생성하시오.
- 이름 과 국어, 영어, 수학 점수를 저장할 변수를 초기화 메소드를 생성해서 기본값은 이름의 기본값은 "noname", 국어, 영어, 수학 점수는 0으로 설정하시오
- 이름 및 국어, 영어, 수학 점수는 인스턴스가 직접 접근할 수 없도록 생성하시오.
- 이름 및 국어, 영어, 수학 점수에 접근할 수 있는 getter 와 setter 메소드를 생성하시오.
- 국어, 영어, 수학 점수의 평균을 구해서 리턴하는 메소드를 생성하시오.

'''
import random


class recordingScore:
    def __init__(self, name="noname", korean=0, english=0, math=0):
        self.name = name
        self.korean = korean
        self.english = english
        self.math = math

    def getName(self):
        return self.__name
    def setName(self, name):
        self.__name = name

    def getKorean(self):
        return self.__korean

    def setKorean(self, korean):
        self.__korean = korean

    def getEnglish(self):
        return self.__english

    def setEnglish(self, english):
        self.__english = english

    def getMath(self):
        return self.__math

    def setMath(self, math):
        self.__math = math

    def average(self, korean, english, math):
        return (int(korean)+int(english)+int(math))/3



이름, 국어, 영어, 수학 = input().split(" ")

re = recordingScore()
re.setName(이름)
re.setKorean(국어)
re.setEnglish(영어)
re.setMath(수학)

print(f"{re.getName()} 의 평균 점수는 "
      f"{re.average(re.getKorean(), re.getEnglish(), re.getMath())}")

'''
2.call by value 와 call by reference 에 대해서 작성하세요
'''

# call by value : 값에 의한 호출을 정의한다.
'''
매개변수 호출 방식 중 하나 
함수 호출 시 변수의 값을 전달 
method 내에서 variable 을 변경하더라도  원본데이터에는 영향이 없다 
자바에서 자료형이 call by value 이다 . 
'''

# call by reference : 참조에 의한 호출을 정의한다. 그냥 호출하면 주소값을 말해줌
'''
매개변수 호출 방식중 하나로서 
method 에서 variable 변경하면 변경한 내용이 영향을 미친다. 
'''

'''
3.str 클래스를 상속받는 클래스를 작성하세요
- 내용은 없어도 되므로 pass 로 설정
'''

class DrivedClass(str):
    def aboutstr(self):
        return help(str)

dr = DrivedClass()

dr.aboutstr()

'''
4. Overloading 과 Overriding 의 개념을 작성하시오.
'''

'''
Overloading 은 같은 class 내에 
매개변수가 다른 method 를 사용하는 것 

'''
'''
Override
상속받은 method 를 재정의 해서 사용하는 것을 말함 
return 타입이 과 parameter이 같아야한다 
Interface 의 method 는 상속받는 하위 클래스에서 무조건
Override 해줘야한다 .
'''

'''
5.추상 클래스의 개념을 작성하세요
- 만드는 목적도 작성하세요
'''

'''
추상 클래스의 개념 : 공통적으로 사용하는 filed 나 method 를 사용하기 위함 
인스턴스를 생성할 수 없고 
상속을 통해서 완성된다 . 

이를통해 유지보수성을 높이고 통일성을 유지할 수 있다. 
'''


'''
6.1~45 사이의 서로 다른 정수 6개를 입력받아서 list에 저장하는 코드를 작성하세요
- 정수 이외의 데이터가 들어오면 다시 입력받도록 작성하세요
- 1 ~ 45 사이의 숫자 이외의 데이터가 들어오면 다시 입력받도록 작성하세요
- 중복된 데이터가 들어오면 다시 입력받도록 작성하세요
- 숫자를 오름차순 정렬해서 출력하세요
'''
li = []
for i in range(1, 7):
    r = random.randint(1, 45)
    a = len(li)
    for j in range(0, a):
        if r == li[j]:
            j = j-1
            continue
    li.append(r)
li.sort()
print(li)

'''
7. 1번에서 만든 클래스의 인스턴스를 3개 만들어서 list에 저장하세요
'''
rli = []
re1 = recordingScore()

'''
8. 6번에서 만든 내용을 set을 이용하도록 변경하세요
'''

litoset = set(li)
print(litoset)

'''
9. 7번의 내용을 1번에서 만든 클래스를 이용하지 않고 dict를 이용해서 작성하시요.
'''



'''
10. 다음 list에 저장된 내용에서 각 단어가 등장한 횟수를 dict에 저장하는 코드를 작성하시오.

words = ['python', 'java', 'python', 'R', 'python', 'python', 'sql', 'javascript', 'javascript', 'c++', 'c++', 'c++']
'''
from collections import Counter

words = ['python', 'java', 'python', 'R', 'python', 'python', 'sql', 'javascript', 'javascript', 'c++', 'c++', 'c++']



count1 = Counter()

for i in words:
    count1[i] +=1

counttodic = dict(count1)
print(counttodic)


