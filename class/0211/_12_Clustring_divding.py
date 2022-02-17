from sklearn.cluster import KMeans
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

'''
데이터 생성 
'''

# 2차원 배열로 생성
sample = np.array([[13, 30], [30, 40], [67, 44], [36, 24], [64, 37], [24, 46]])
df = pd.DataFrame(sample, columns=['feature_1', 'feature_2'])
print(df)

'''
필요한 군집의 개수를 가지고 객체를 생성 
'''
# 42 는 machine Learning 하는 사람들이 선호하는 숫자
cluster = KMeans(3, random_state=42)

# 모델을 생성
cluster.fit(sample)

'''
예측 -- 예측 시에는 predict 
'''
# 새로운 이름 부여
df['group'] = cluster.predict(sample)
print(df)


plt.figure()
df.plot(kind='scatter', x='feature_1', y='feature_2')
plt.show()
