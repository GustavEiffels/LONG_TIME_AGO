import pandas as pd
import matplotlib.pyplot as plot
import scipy as sp
import seaborn as sns


''' 스피어만 상관계수를 확인하고자 데이터를 직접 생성 '''
s1 = pd.Series([1, 2, 3, 4, 5, 6])

# s1 과 선형 관계 : 3 을 곱함  s2 = s1 * 3
s2 = pd.Series([3, 6, 9, 12, 15, 18])

# s1 과 비선형 관계 : 제곱관계  s3 = s1^2
s3 = pd.Series([1, 4, 9, 16, 25, 36])


''' s1 과 s2 의 pearson 상관계수 '''
result1 = sp.stats.pearsonr(s1.values, s2.values)
print(" s1 과 s2 의 피어슨 상관 계수 : ", result1)

''' s1 과 s3 의 pearson 상관계수 '''
result2 = sp.stats.pearsonr(s1.values, s3.values)
print(" s1 과 s3 의 피어슨 상관 계수 : ", result2)
# 정확하게 1로 나누어 떨어지지 않는다

''' 이 경우 데이터의 개수가 늘어나면 상관 계수의 값은 계속 낮아짐'''

''' Spearman 상관 계수 '''
result3 = s1.corr(s2, method='spearman')
print('s1 과 s2 의 스피어만 상관계수 :', result3)

result4 = sp.stats.spearmanr(s1.values, s3.values)
print('s1 과 s3 의 스피어만 상관계수 :', result4)