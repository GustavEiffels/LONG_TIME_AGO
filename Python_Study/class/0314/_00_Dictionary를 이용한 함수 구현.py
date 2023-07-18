'''
입력이 1, 2, 3, 4, 5 이고
출력은 곱하기 2를 해서 만들어지는 경우
'''
dict_fun = {1: 2, 2: 4, 3: 6, 4: 8, 5: 10}
print(f' dict_fun[2] : {dict_fun[2]}')
print()

'''result 
dict_fun[2] : 4
'''

''' 함수의 정의 '''


def double_num(x):
    return 2 * x
x = 10
y = double_num(x)
print(f' y = {y}')
print()
'''
result 
y = 20
'''

''' 
최근에 선호하는 방식 
자료형의 힌트를 이용한 정의 
'''
def double_num_hint(x: int) -> int:
    return 2 * x

y = double_num_hint(10)
print(f' y = {y}')
print()

'''
 result
 y = 20
'''