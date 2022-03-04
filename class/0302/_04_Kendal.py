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


result1 = s1.corr(s2, method='kendall')
print('s1 과 s2 의 kendal 상관 계수 : ', result1)

result2 = sp.stats.kendalltau(s1.values, s3.values)
print('s1 과 s3 의 kendal 상관 계수 : ', result2)

''' python은 static 이나 class method 가 아니더라도 클래스를 이용해서 호출가능  '''
# ---> unbound 호출 이라고 한다
# 첫번째 parameter 가 객체가 되어야 한다
result3 = pd.Series.corr(s1, s3, method='kendall')
print('unbound call : ', result3)
