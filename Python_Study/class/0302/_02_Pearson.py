import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import scipy as sp

mpg = pd.read_csv('../python_statistics-main/auto-mpg.csv', header=None)
mpg.columns = ['mpg', 'cylinders', 'displacement', 'horsepower', 'weight',
               'acceleration', 'model year', 'origin', 'name']

print(mpg)
''' 데이터 가져오기 '''
''' 데이터에 대해서 알아야하는 작업 '''


''' 산점도 그리기 '''
# seaborn 의 pairplot 사용


# sns.pairplot(mpg)
# plt.show()


''' 상관계수 확인 '''
print(mpg.corr())

''' mpg 와 weight 의 상관관계를 확인 '''
mpg.plot(kind='scatter', x='weight', y='mpg', c='blue', s=10)
plt.show()

''' regplot 을 사용해도 좋다 , 회귀 선을 만들어준다 '''
sns.regplot(x='weight', y='mpg', data=mpg)
plt.show()

''' scipy 를 이용한 pearson 상관 계수 '''
result = sp.stats.pearsonr(mpg['mpg'].values, mpg['weight'].values)

''' 첫번째 결과는 피어슨 상관계수 '''
''' 두번째 결과는 유의 확률 (P-value ) '''
print(result)
''' result = (-0.831740933244335, 2.9727995640500577e-103) '''
''' 유이확률이 엄청 적게 나옴 .. 유연히 나올 확률이 저정도: 상관관계가 아주 깊다'''

''' 산포도를 꼭 확인 해봐야한다 . 앤스콤 데이터 확인 '''