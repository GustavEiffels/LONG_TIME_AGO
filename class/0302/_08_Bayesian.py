import numpy as np
from pgmpy.factors.discrete import TabularCPD as ta

''' 베이지안 정리를 이용한 검사 시약 문제 // TabularCPD '''
# 병에 걸릴 확률 = 0.002
# 병에 걸렸을 때 , 시약이 양성으로 판정할 확률 : 0.99
# 병에 걸리지 않았는데 시약이 양성으로 판정될 확률 : 0.05
''' 시약이 양성으로 판정했을 때 병에 걸렸을 확률은 '''

# 병에 걸릴 확률과 걸리지 않을 확률
# 병에 걸리지 않을 확률, 병에 걸릴 확률
cpd_X = ta('X', 2, [[1-0.002], [0.002]])
print(cpd_X)
print()
''' 시약이 판정할 확률 '''
# 앞열의 확률은 병에 걸리지 않은 사람에게 검사했을 때
# 뒷열의 확률 = 병에 걸린 사람에게 검새했을 때의 확률
cpd_Y_on_X = ta('Y', 2, np.array([[0.95, 0.01], [0.05, 0.99]]),
                evidence=['X'], evidence_card=[2])
print(" 시약이 판정할 확률 ")
print(cpd_Y_on_X)

# 베이지안 객체를 생성
''' BayesianNetwork 으로 사용해야한다 .'''
from pgmpy.models import BayesianNetwork
model = BayesianNetwork([('X', 'Y')])

model.add_cpds(cpd_X, cpd_Y_on_X)

''' 제대로 만들어 졌는지 확인 '''
print(model.check_model())

''' Elimination 을 사용해서 제거한다 '''
from pgmpy.inference import VariableElimination as Va

inference = Va(model)
posterior = inference.query(['X'], evidence={'Y': 1})
print(posterior)
print()


