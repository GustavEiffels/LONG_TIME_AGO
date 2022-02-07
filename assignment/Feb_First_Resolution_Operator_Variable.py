'''
1. 평균 구하기
국어, 영어, 수학, 과학 점수가 입력받아서 평균 점수를 출력하는 프로그램을 만드세요(input 에서 안내 문자열은 과목 이름을 출력)
평균 점수를 출력할 때는 소수점 이하 자리는 버림(정수로 출력).
'''

국어, 영어, 수학, 과학 = map(int, input(f"국어 영어 수학 과학 점수를 차례대로 입력하세요").split())

li = [국어, 영어, 수학, 과학]

for i in range(0, len(li), 1):
    param = li[i]
    if not 0 < param < 101:
        raise Exception("It is not adaptable figure")


def makingAverage(국어, 영어, 수학, 과학):
    return print((국어 + 영어 + 수학 + 과학) // 4)


try:

    makingAverage(국어, 영어, 수학, 과학)

except Exception as e:
    print(e)

# None 이 출력되는 이유 !!
# print(makingAverage(국어, 영어, 수학, 과학))


'''
2. 소음이 가장 심한 층수 출력하기
국립환경과학원에서는 아파트에서 소음이 가장 심한 층수를 구하는 계산식을 발표했는데 소음이 가장 심한 층은 0.2467 * 도로와의 거리(m) + 4.159
도로 거리를 입력받아서 소음이 가장 심한 층수가 출력되게 만드세요.
층수를 출력할 때는 소수점 이하 자리는 버림(정수로 출력)
'''


class noise:
    def maxNoise(self, distance):
        story = int(0.2467 * distance + 4.159)
        print(f"{story}층 입니다")


hometoRoad = int(input())

noise1 = noise()
noise1.maxNoise(hometoRoad)

'''
3. 스킬 공격력 출력
L이라는 게임에서 “왜곡”이라는 스킬이 AP * 0.6 + 225의 피해를 줍니다. 참고로 이 게임에서 AP(Ability Power, 주문력)는 마법 능력치를 뜻합니다. 
AP를 입력받아서 스킬의 피해량이 출력되게 만드세요.
'''


class distortion:
    def skill(self, abilityPower):
        damage = int(abilityPower) * 0.6 + 255
        print(damage)


attack = int(input())

distor = distortion()
distor.skill(attack)

'''controller'''
'''
1. 1부터 100까지 짝수의 합 구하기
'''
print("1부터 100까지 짝수의 합 구하기 ========================================")
sum = 0
for i in range(1, 101, 1):
    sum = sum + i

print(f'sum of one to hundred is {sum}')

'''
2. 교통 카드 잔액 출력
표준 입력으로 인원 수가  입력됨
1인당 요금은 1,350원으로 교통카드를 사용할 때마다 요금을 차감하고 각 줄에 잔액을 출력하는 프로그램을 작성
최초 금액은 10,000 원으로 설정하고 출력하지 않음
잔액은 음수가 될 수 없으며 잔액이 부족하면 출력을 종료
'''
ride = int(input())

balance = 10000


class publicTransportCard:
    def payment(self):
        for d in range(1, ride+1, 1):
            global balance
            if balance >= 1350:
                result = balance - 1350
                balance = result
                print(f"잔액은 : {result} 원 입니다")

            elif balance < 1350:
                print(f"잔액이 부족합니다. 잔액은 {result}")
                break


ridebus = publicTransportCard()
ridebus.payment()

'''
3. 2부터 1000까지 Prime(소수) 의 개수 구하기
    Prime은 1과 자기 자신으로만 나누어지는 수 
        2부터 자신의 절반이 되는 숫자까지 나누어 떨어지지 않으면 Prime
'''

print("2부터 1000까지 Prime(소수) 의 개수 구하기")
sumresult = 0
li = []


def sum1():
    global sumresult
    for p in range(2, 1001):
        if p == 2 or p == 3:
            sumresult = sumresult + 1
            print(p, end=" ")
        for pn in range(2, int(p / 2) + 2):
            if p % pn == 0:
                break
            elif pn == int(p / 2):
                sumresult = sumresult + 1
                print(p, end=" ")
    print()
    print(sumresult)


sum1()

'''
4. 2부터 1000까지 완전수 출력 하기
완전수는 자기 자신을 제외한 약수의 합이 자기 자신과 동일한 수
6 : 1 + 2 + 3
'''
print("2부터 1000까지 완전수 출력 하기")
def perfectNumber():
    for p1 in range(2, 1001):
        temp = 0
        for p2 in range(1, p1):
            if p1 % p2 == 0:
                temp = temp + p2
        if temp == p1:
            print(p1, end=" ")

perfectNumber()

'''
5. n번째 피보나치 수열의 값 출력하기 - 재귀를 사용하지 않고 반복문을 사용
피보나치 수열은 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144 ..
첫번째 와 두번째 숫자는 1
세번째 숫자부터는 이전 2개의 항의 합
n은 입력받아서 처리 - 15번째 숫자를 출력해보세요
'''
print("n번째 피보나치 수열의 값 출력하기================")

a1 = 1
a2 = 1
Number = int(input())
for i in range(3, Number+1):
    pibo = a1 + a2
    a1 = a2
    a2 = pibo

print(f"피보나치 수열의 {Number}째의 값은 {pibo}이다")

'''
 n번째 피보나치 수열의 값을 출력하는 재귀 함수를 구현
'''
print()
print("n번째 피보나치 수열의 값을 출력하는 재귀 함수를 구현")

# 함수 정의

def fibonacci(x):
    if x == 1 or x == 2:
        return 1
    else:
        return fibonacci(x - 1) + fibonacci(x - 2)

fibotest = int(input())
print(f"{fibotest} 번째 fibo 나치 수열의 값은 {fibonacci(fibotest)}")