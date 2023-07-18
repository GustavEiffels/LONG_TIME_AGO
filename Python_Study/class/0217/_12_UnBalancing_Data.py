import numpy as np
from sklearn.ensemble import RandomForestClassifier
'''
가중치를 설정해서 모델을 생성
'''
weights = {0: 0.9, 1: 0.1}

'''
모델 생성시 설정

0 번에다 0.9 가중치를 
1 번에 0.1 가중치를 줘서 생성  
'''
rfc = RandomForestClassifier(class_weight=weights)
print(rfc)

'''
다운 샘플링과 업 샘플링
0 번은 10 개 1 번은 100개  
'''
li1 = []

for i in range(0, 10, 1):
    li1.append(0)
print()

li2 = []
for i in range(0, 100, 1):
    li2.append(1)

target = np.array(li1+ li2)
print(target)
print()

'''
0 번과 1 번의 인덱스를 추출 
'''
print("0 번과 1 번의 인덱스를 추출")
i_class0 = np.where(target == 0)[0]
i_class1 = np.where(target == 1)[0]
print(i_class0)
print()
print(i_class1)
'''
데이터의 개수 확인 
'''
print("데이터의 개수 확인")
n_class0 = len(i_class0)
n_class1 = len(i_class1)

'''
Down Sampling // Random 하게 추출 
-- 순서대로 뽑으면 순서때문에 왜곡이 발생할 수도 있다.
'''
print("Down Sampling // Random 하게 추출")
i_class1_downsampled = np.random.choice(i_class1, size=n_class0, replace=False)
print("Result")
print()
print(i_class1_downsampled)
print()

print("=========")
np.hstack((target[i_class0], target[i_class1_downsampled]))
print(np.hstack((target[i_class0], target[i_class1_downsampled])))

print("============UpSampling")
i_class0_upsampled = np.random.choice(i_class0, size=n_class1, replace=True)
np.hstack((target[i_class0_upsampled], target[i_class1]))
print(np.hstack((target[i_class0_upsampled], target[i_class1])))
