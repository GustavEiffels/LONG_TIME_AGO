import numpy as np
from pgmpy.factors.discrete import JointProbabilityDistribution as JPD

# 남자 12 명 여자 8 명인 경우 독립 확률
px = JPD(['x'], [2], np.array([12, 8])/20)
print(f'남자 12 명 여자 8 명인 경우 독립 확률  : \n{px}')
print()

''' 결합 확률 '''
# 남자 12 명 긴머리 : 3 / 짧은머리 : 8
# 여자 8 명  긴머리 : 7 / 짧은머리 : 1
pxy = JPD(['x', 'y'], [2, 2], np.array([3, 9, 7, 1])/20)
print(f'# 남자 12 명 긴머리 : 3 / 짧은머리 : 9'
      f'8 # 여자 8 명  긴머리 : 7 / 짧은머리 : 1'
      f'\n{pxy}')
print()


''' 조건부 확률 '''
# False 를 주면 return 을 해줌
# 위 조건에서 남자일 확률
print("marginal_distribution 사용")
pmx = pxy.marginal_distribution(['x'], inplace=False)
print(pmx)
print()

print("marginalize 사용")
pmx = pxy.marginalize(['y'], inplace=False)
print(pmx)
print()

''' 머리카락에 대한 주변 확률 '''
# 긴머리일 확률 짧은 머리일 확률
pmy = pxy.marginal_distribution(['y'], inplace=False)
print(pmy)
print()

''' 조건부 확률 '''
# 남자 긴머리 일 확률과 짧은 머리일 확률
py_on_x0 = pxy.conditional_distribution([('x', 0)], inplace=False)
print("남자 긴머리 일 확률과 짧은 머리일 확률")
print(py_on_x0)
print()

# 조건부 확률
py_on_x1 = pxy.conditional_distribution([('x', 1)], inplace=False)
print("여자 긴머리 일 확률과 짧은 머리일 확률")
print(py_on_x1)
print()

''' 2개의 사건이 독립적인지 확인 '''
print("2개의 사건이 독립적인지 확인")
print(pxy.check_independence(['x'], ['y']))
