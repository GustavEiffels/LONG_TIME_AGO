import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import  accuracy_score
from sklearn.datasets import load_digits
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import confusion_matrix
from sklearn.decomposition import PCA
from sklearn.mixture import GaussianMixture
import pandas as pd
from sklearn.model_selection import StratifiedShuffleSplit
import matplotlib.image as mpimg
from pandas.plotting import scatter_matrix
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import OrdinalEncoder
from sklearn.preprocessing import OneHotEncoder
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
#산포도 출력
import platform
from matplotlib import font_manager, rc
import matplotlib
#매킨토시의 경우
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
#윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)
#우분투 리눅스의 경우
else:
    font_name = '/usr/share/fonts/truetype/nanum/NanumMyeongjo.ttf'
    rc('font', family=font_name)
#음수 출력
matplotlib.rcParams['axes.unicode_minus'] = False

# 파이썬 ≥3.5 필수
import sys
assert sys.version_info >= (3, 5)
# 공통 모듈 임포트
import numpy as np
import pandas as pd
import os
# 깔끔한 그래프 출력을 위해 %matplotlib inline
import matplotlib as mpl
import matplotlib.pyplot as plt
mpl.rc('axes', labelsize=14)
mpl.rc('xtick', labelsize=12)
mpl.rc('ytick', labelsize=12)

# 그림을 저장할 위치 - 현재 디렉토리에 images/classification
PROJECT_ROOT_DIR = "images"
CHAPTER_ID = "classification"
IMAGES_PATH = os.path.join(PROJECT_ROOT_DIR, "images/images", CHAPTER_ID)

os.makedirs(IMAGES_PATH, exist_ok=True)

# matplotlib.pyplot 으로 출력한 이미지를 저장하기 위한 함수
def save_fig(fig_id, tight_layout=True, fig_extension="png", resolution=300):
    path = os.path.join(IMAGES_PATH, fig_id + "." + fig_extension)
    print("그림 저장:", fig_id)
    if tight_layout:
        plt.tight_layout()
        plt.savefig(path, format=fig_extension, dpi=resolution)

# 그래프 한글 처리
from matplotlib import font_manager, rc
import platform
path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)
# 그래프 음수 처리
mpl.rcParams['axes.unicode_minus'] = False
# Jupyter Notebook의 출력을 소수점 이하 3자리로 제한
# %precision 3
# DataFrame의 출력을 소수점 이하 3자리로 제한
pd.set_option('display.precision', 3) # 'display.percision' 라고 작성해야 정상적으로 동작 함
import seaborn as sns
import scipy as sp
from scipy import stats

# 사이킷런 ≥0.20 필수
import sklearn
assert sklearn.__version__ >= "0.20"

# 노트북 실행 결과를 동일하게 유지하기 위해
np.random.seed(42)



''' 데이터 가져오기 '''
print(''' 데이터 가져오기 ''')
print()

from sklearn.datasets import load_boston
boston = load_boston()

print(f'boston.data.shape : \t\n {boston.data.shape}')
print()
print(f'boston.target.shape : \t\n {boston.target.shape}')
print()




''' 2. 전처리를 위해서 피처들을 DataFrame 으로 변경  '''
print(''' 2. 전처리를 위해서 피처들을 DataFrame 으로 변경  ''')
print()

''' 전처리를 위해서 feature 들을 DataFrame 으로 변경 '''
bostonDF = pd.DataFrame(boston.data, columns=boston.feature_names)

''' Target 생성 '''
bostonDF['PRICE'] = boston.target

bostonDF.info()


''' 상관 관계 파악 '''
print(''' 상관 관계 파악 ''')
print()
cols = ['RM', 'ZN', 'INDUS', 'NOX', 'AGE', 'PTRATIO', 'LSTAT', 'RAD','PRICE']

sns.pairplot(bostonDF[cols],height=2.5)
plt.show()

''' 상관 계수 출력 '''
# heatmap 을 사용하는 것이 유리할 수 있다.
print(''' 상관 계수 출력 ''')
print()

cm = np.corrcoef(bostonDF[cols].values.T)
plt.figure(figsize=(15, 7))
sns.set(font_scale=1.5)
hm = sns.heatmap(cm, cbar=True, annot=True, square=True,fmt='.2f', annot_kws={'size':15},yticklabels=cols, xticklabels=cols)
plt.show()

''' scipy 를 이용한 다변량 선형 회귀 '''
print(''' scipy 를 이용한 다변량 선형 회귀 ''')
print()

from scipy import stats

slope, intercept, r_value, p_value, stderr = stats.linregress( bostonDF['RM'], bostonDF['PRICE'])

print(f' 기울기 : {slope}')
print()
print(f' 절편 : {intercept}')
print()
print(f' 상관 계수 : {r_value}')
print()
print(f' 유의 확률( 불확실성 정도 - 우연히 발생하는 정도 ) : {p_value}')
print()



''' 예 측 '''
print(''' 예 측 ''')
print()

# 방이 4 개인 경우 집 값 예측
print('방이 4 개인 경우 집 값 예측')
print(4 * slope + intercept)
print()



''' Sklearn 의 LinearRegression 을 이용한 주택 가격 예측 '''
print(''' Sklearn 의 LinearRegression 을 이용한 주택 가격 예측 ''')
print()

ols = LinearRegression()

''' feature 와 Target 생성 '''
X = bostonDF[['RM']].values
y = bostonDF['PRICE'].values

ols.fit(X, y)

print(f' 기울기 : {ols.coef_}')
print()
print(f' 절편 : {ols.intercept_}')
print()

print(ols.predict(np.array([[4]])))



''' 다변량 선형 회귀 '''
print(''' 다변량 선형 회귀 ''')
print()

y_target = bostonDF['PRICE']
X_data = bostonDF.drop(['PRICE'], axis=1)

'''훈련 데이터와 test data 분할 '''
from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X_data, y_target, test_size=0.2, random_state=42)


''' 모델 생성 및 훈련 '''
ols = LinearRegression()
ols.fit(X_train, y_train)


''' 예측 '''
print(''' 예측 ''')
print()
y_preds = ols.predict(X_test)

''' 평가 지표 확인 '''
print(''' 평가 지표 확인 ''')
from sklearn.metrics import mean_squared_error, r2_score

''' 평균 제곱 오차 '''
mse = mean_squared_error(y_test, y_preds)

''' 평균 제곱근 오차 '''
rmse = np.sqrt(mse)

''' r2 Score- 결정계수'''
r2_score = r2_score(y_test, y_preds)


print(f'MSE : {mse}')
print()
print(f'RMSE : {rmse}')
print()
print(f'R2 : {r2_score}')
print()



''' 절편 과 회귀 계수 '''
print(''' 절편 과 회귀 계수 ''')
print()

print(f'절 편 : {ols.intercept_}')
print(f'기울기 : \t\n {ols.coef_}')



df = pd.read_csv('score.csv')

df.info()

''' 다변량 회귀 모델 만들기 '''
print(''' 다변량 회귀 모델 만들기 ''')
print()

import statsmodels.formula.api as sm

result = sm.ols(formula='score~iq + academy + game + tv', data=df).fit()

'''학생의 예측 점수 '''
print(f'예측 점수 : {result.predict()}')
print()

''' 절편과 기울기 '''
print(f' 절편 :  {result.params.Intercept}')
print(f' iq 기울기 : {result.params.iq}')
print(f' academy 기울기 : {result.params.academy}')
print(f' tv 기울기 : {result.params.tv}')
print(f' game 기울기 : {result.params.game}')


''' 결정 계수 확인 '''
print(''' 결정 계수 확인 ''')
print(f' 결정 계수 확인 : {result.rsquared}')



''' 유의 확률 '''
print(f'유의 확률 : {result.pvalues}')

''' 상관 계수 확인 '''
print(f'상관 계수 : {df.corr()}')

''' VIF( 분산 팽창 요인 ) 확인 '''
from statsmodels.stats.outliers_influence import variance_inflation_factor
X = df.drop(['score','name'], axis=1)


''' VIF( 분산 팽창 요인 ) 확인 '''

''' 10 이 넘으면 다중 공선성 문제가 발생할 가능성이 매우 높음 '''
from statsmodels.stats.outliers_influence import variance_inflation_factor
X = df.drop(['score','name'], axis=1)
vif = pd.DataFrame()
vif['VIF Factor'] = [variance_inflation_factor(X.values, i) for i in range(X.shape[1])]

vif['features'] = X.columns
print(vif)




''' Academy를 제거하고 확인 '''
print(''' Academy를 제거하고 확인 ''')
print()

result = sm.ols(formula='score ~ iq + game + tv',data=df).fit()

''' 결정 계수 확인 : 1에 가까워서 설명력이 높음 '''
print(f'결정계수 : {result.rsquared}')

''' 유의 확률이 이전보다 낮아져서 다중 공선성 문제가 발생할 가능성이 낮음 '''
print(f'유의 확률 : {result.pvalues}')


''' 다항 회귀 '''
from sklearn.preprocessing import PolynomialFeatures

''' 일차항 데이터 '''
X = np.arange(4).reshape(2, 2)
print(X)

''' 이차항 데이터 '''
poly = PolynomialFeatures(degree=2)
poly.fit(X)
poly_ftr = poly.transform(X)
print(poly_ftr)


''' 3차항 데이터 '''
poly = PolynomialFeatures(degree=3)
poly.fit(X)

# 값의 순서는 첫번째는 상수항으로 1
# 두번째와 세번째는 원본 그대로 첫번째의 제곱 두수의 곱 두번째의 제곱
poly_ftr = poly.transform(X)
print(poly_ftr)



''' 다항 회귀 적용 '''
print(''' 다항 회귀 적용 ''')
print()

''' 데이터 생성 '''
X = 6 * np.random.rand(100, 1) -3
y = 0.5* X **2 + X + 2 + np.random.randn(100, 1)

plt.plot(X, y,'b.')
plt.show()

from sklearn.preprocessing import PolynomialFeatures

''' 2차항을 생성 , 상수항은 포함시키지 않음 '''
poly_features = PolynomialFeatures(degree=2, include_bias=False)

''' 2차항을 추가한 데이터 '''
X_poly = poly_features.fit_transform(X)

print(X[0])
print(X_poly[0])

''' 다항 회귀 훈련 및 회귀 계수 와 절편을 출력 '''
from sklearn.linear_model import LinearRegression
lin_reg = LinearRegression()
lin_reg.fit(X_poly, y)
print(lin_reg.intercept_)
print(lin_reg.coef_)