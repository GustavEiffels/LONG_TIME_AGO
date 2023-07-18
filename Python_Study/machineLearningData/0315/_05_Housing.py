# housing.csv 사용 할 예정
''' csv 를 읽기에 Pandas 가 좋다 '''
import pandas as pd
import numpy as np

housing = pd.read_csv('../data/housing.csv')
# 상위 데이터 5개 추출
print(housing.head())
print()

# 데이터의 대략적인 정보 확인
housing.info()
print()

# 문자열인 경우 범주형인지 확인
''' value_counts() 를 이용해서 값의 개수 확인 '''
print("housing['ocean_proximity']")
print(housing['ocean_proximity'].value_counts())
print()

# 숫자 데이터의 경우
'''describe 함수를 이용해서 계략적인 정보 확인'''
# 범위나 숫자의 크기 확인
print("범위나 숫자의 크기 확인")
print(housing.describe())
print()

# 히스토 그램 출력
import matplotlib.pyplot as plt

# housing.hist(bins=50, figsize=(20, 15))
# plt.show()

'''' 훈련 데이터와 테스트 데이터를 분리 시킨다 '''
np.random.seed(42)

''' 데이터와 테스트 데이터의 비율을 받아서 분할해서 리턴하는 함수 '''


def split_train_test(data, test_ratio):
    # data를 무작위로 섞기
    shuffled_indices = np.random.permutation(len(data))

    # 입력 받은 비율을 원래 데이터의 크기에 곱해서
    ''' 테스트 데이터의 크기 생성 '''
    test_set_size = int(len(data) * test_ratio)

    # random 하게 섞인 데이터에서 테스트 데이터의 크기 만큼
    # 인덱스를 추출
    test_indices = shuffled_indices[:test_set_size]

    ''' 
    랜덤하게 섞인 데이터에서 훈련 데이터의 크기만큼 
    인덱스를 추출 
    테스트 데이터를 제외하고 모두 가져온다 
    '''
    train_indices = shuffled_indices[test_set_size:]

    # 훈련 데이터와 테스트 데이터를 리턴
    return data.iloc[train_indices], data.iloc[test_indices]


train_set, test_set = split_train_test(housing, 0.2)
print(f'len(train_set), ":", len(test_set) = {len(train_set), ":", len(test_set)}')
print(f'len(train_set)/len(test_set) = {len(train_set) / len(test_set)}')
print()

''' 훈련 데이터 확인 '''
print(f'훈련 데이터 확인 : \t\n {train_set.head()}')

# median_income 을 범주화 해서 계층적 Sampling tngod

'''
값의 개수를 확인 
'''
print("값의 개수를 확인====================================")
print(housing['median_income'].value_counts())
print()

# 연속형 데이터와 범주화 - binding
housing['income_cat'] = pd.cut(housing['median_income'],
                               bins=[0, 1.5, 3.0, 4.5, 6, np.inf],
                               labels=[1, 2, 3, 4, 5])
print("housing['income_cat'].value_counts() ====================================")
print(housing['income_cat'].value_counts())


# 계층적 Sampling
from sklearn.model_selection import StratifiedShuffleSplit

''' 계층적 Sampling 을 위한 준비 '''
split = StratifiedShuffleSplit(n_splits=1, test_size=0.2, random_state=42)

''' housing 데이터를 나눌 때 income_cat 비율 대로 분할 '''
print("housing 데이터를 나눌 때 income_cat 비율 대로 분할")
for train_index, test_index in split.split(housing, housing['income_cat']):
    start_train_set = housing.loc[train_index]
    start_test_set = housing.loc[test_index]
print(start_train_set['income_cat'].value_counts()/len(start_train_set['income_cat']))


import numpy as np

import pandas as pd

import matplotlib.pyplot as plt


# numpy 와 호환되는 자료 구조 ( Series, DataFrame )
from matplotlib import font_manager, rc

# 운영 체제
import platform

# 시각화
import seaborn as sns

# 통계
import statsmodels.api as sm
import statsmodels.formula.api as swf
from statsmodels.stats import power

# 데이터 전처리 & 머신러닝
import sklearn

import matplotlib

path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)

matplotlib.rcParams['axes.unicode_minus'] = False

plt.rc("font", size=18)  # 그림의 폰트 크기를 18로 고정
gray = {"facecolor": "gray"}
black = {"facecolor": "black"}
red = {"facecolor": "red"}
green = {"facecolor": "green"}
blue = {"facecolor": "blue"}


''' 산포도를 생성 '''
# housing = start_train_set.copy()
# ax = housing.plot(kind='scatter', x='longitude', y='latitude')
# ax.set(xlabel='경도', ylabel='위도')
# plt.show()

''' 
alpha 로 조절해도 되지만 
색상으로 비교 하면 
시각적으로 효과가 훨씬 두드러 진다 
'''

# ax = housing.plot(kind='scatter',
#                   x='longitude',
#                   y='latitude',
#                   alpha=0.4,
#                   s=housing['population']/100,
#                   label='인구',
#                   figsize=(10, 7),
#                   c='median_house_value',
#                   cmap=plt.get_cmap('jet'),
#                   colorbar=True,
#                   sharex=False)
# ax.set(xlabel='경도', ylabel='위도')
# plt.legend()
# plt.show()

''' 위도와 경도 출력 시 지도와 함께 출력하는 것이 더 효과 적이다 '''
# 지도와 함께 출력

# 1. 지도가 준비가 되어야함
'''california.png'''
import matplotlib.image as mpimg

california_img = mpimg.imread('../data/images/california.png')

# ax = housing.plot(kind='scatter',
#                   x='longitude',
#                   y='latitude',
#                   alpha=0.4,
#                   s=housing['population']/100,
#                   label='인구',
#                   figsize=(10, 7),
#                   c='median_house_value',
#                   cmap=plt.get_cmap('jet'),
#                   colorbar=False)
#
# plt.imshow(california_img, extent=[-124.55,
#                                    -113.80,
#                                    32.45,
#                                    42.05])
# plt.xlabel('경도')
# plt.xlabel('위도')
# plt.legend()
# plt.show()

''' 지도오 함께 출력을 하게 되면 군집 같은 것을 할대 중심점의 개수나 
위치를 설정하는데 많은 도움이 된다. '''


''' 상관 계수 확인 '''
corr_matrix = housing.corr()
print(corr_matrix)
print()
'''
Name: income_cat, dtype: float64
                    longitude  latitude  ...  median_income  median_house_value
longitude            1.000000 -0.924478  ...      -0.019615           -0.047466
latitude            -0.924478  1.000000  ...      -0.075146           -0.142673
housing_median_age  -0.105823  0.005737  ...      -0.111315            0.114146
total_rooms          0.048909 -0.039245  ...       0.200133            0.135140
total_bedrooms       0.076686 -0.072550  ...      -0.009643            0.047781
population           0.108071 -0.115290  ...       0.002421           -0.026882
households           0.063146 -0.077765  ...       0.010869            0.064590
median_income       -0.019615 -0.075146  ...       1.000000            0.687151
median_house_value  -0.047466 -0.142673  ...       0.687151            1.000000

[9 rows x 9 columns]

'''
print()
print(corr_matrix['median_house_value'].sort_values(ascending=False))

'''
median_house_value    1.000000
median_income         0.687151
total_rooms           0.135140
housing_median_age    0.114146
households            0.064590
total_bedrooms        0.047781
population           -0.026882
longitude            -0.047466
latitude             -0.142673
Name: median_house_value, dtype: float64
'''

'''상관 계수의 시각화 '''
from pandas.plotting import scatter_matrix

# scatter_matrix(housing, figsize=(12, 8))
# plt.show()

''' 항목을 추려서 다시 시각화 '''
# scatter_matrix(housing[['median_house_value', 'median_income', 'total_rooms']],
#                figsize=(12, 8))
# plt.title(" 몇개만 !!")
# plt.show()


''' 새로운 특성 추가 '''
housing['room_per_household'] = housing['total_rooms']/housing['households']

housing['bedrooms_per_room'] = housing['total_bedrooms']/housing['total_rooms']

housing['population_household'] = housing['population']/housing['households']

corr_matrix = housing.corr()
print()
print(corr_matrix['median_house_value'].sort_values())
print()

''' 특성 배열 과 타겟 배열 생성 '''
housing = start_train_set.drop('median_house_value', axis=1)
housing_labels = start_train_set['median_house_value'].copy()


''' 결측치 처리 '''
print("결측치 처리 ")
sample_incomplete_rows = housing[housing.isnull().any(axis=1)].head()
print(sample_incomplete_rows)
print()
print("total_bedrooms")
print(sample_incomplete_rows['total_bedrooms'])
print()

''' total_bedrooms 가 결측치인 데이터를 제거 '''
print("total_bedrooms 가 결측치인 데이터를 제거")
print(housing.dropna(subset=['total_bedrooms']))
print()

''' 열을 제거 '''
sample_incomplete_rows.drop('total_bedrooms', axis=1)


''' 특정 값으로 대체 - 중간값 대체 '''
median = housing['total_bedrooms'].median()
housing['total_bedrooms'].fillna(median)
print(housing['total_bedrooms'].fillna(median)[1606])


''' sklearn 의 impute SimpleImputer 를 이용한 결측치 대체 '''
from sklearn.impute import SimpleImputer

''' 중간값으로 None 을 대체하는 객체 생성 '''
imputer = SimpleImputer(strategy='median')


''' 숫자가 아닌 컬럼 제거 '''
housing_num = housing.drop('ocean_proximity', axis=1)

''' 추정 '''
imputer.fit(housing_num)

''' 추정 값 '''
print()
print(imputer.statistics_)
print()
print(f' 중간 값 : \t\n {housing_num.median().values}')
print()

''' 데이터 변환 '''
X = imputer.transform(housing_num)
housing_tr = pd.DataFrame(X, columns=housing_num.columns,
                          index=list(housing.index.values))
print(housing_tr)
print()

housing_tr.info()
print()


''' 범주형 데이터 처리 '''
housing_cat = housing['ocean_proximity']
print(" 범주형 데이터 처리 ----------------------- ")
print(housing_cat.head())
print()
'''
범주형 데이터 처리 ----------------------- 
12655        INLAND
15502    NEAR OCEAN
2908         INLAND
14053    NEAR OCEAN
20496     <1H OCEAN
Name: ocean_proximity, dtype: object

Process finished with exit code 0
'''


# 1 . factorize() 사용
housing_cat_encoded, housing_categories = housing_cat.factorize()
print("factorize() 사용-------")
print(housing_cat_encoded)
print()




''' housing_categories 를 사용하면 
각각의 수가 무엇을 뜻하는지 알 수 있다 
'''
print("housing_categories 를 사용---------")
print(housing_categories)
print()

#  2. sklearn OrdinalEncoder
from sklearn.preprocessing import OrdinalEncoder

housing_cat = housing[['ocean_proximity']]

ordinal_encoder = OrdinalEncoder()
housing_cat_encoded = ordinal_encoder.fit_transform(housing_cat)
print('sklearn OrdinalEncoder ---- factorize 와 값은 다르다')
print(housing_cat_encoded[:10])
print()


print('카테고리가 지칭하는게 무엇인지 알고 싶다면 ')
print(ordinal_encoder.categories_)
print()


''' One Hot Encoding '''
print("One Hot Encoding----")
from sklearn.preprocessing import OneHotEncoder

encoder = OneHotEncoder(categories='auto')

housing_cat_1hot = \
    encoder.fit_transform(housing_cat_encoded.reshape(-1, 1))

print(f'sparse matrix : \t\n {housing_cat_1hot[:10]}')

''' result ----> 희소 행렬을 확인하게 됨  
One Hot Encoding
  (0, 1)	1.0
  (1, 4)	1.0
  (2, 1)	1.0
  (3, 4)	1.0
  (4, 0)	1.0
  (5, 3)	1.0
  (6, 0)	1.0
  (7, 0)	1.0
  (8, 0)	1.0
  (9, 0)	1.0

Process finished with exit code 0
'''
print()

# 밀집행렬을 확인
print(f' Dense Matix : \t\n {housing_cat_1hot.toarray()[:5]}')
print()


''' Pipe Line '''
# 결측치를 중간 값으로 대체하고 표준화를 수행
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler

num_pipeline = Pipeline([
    ('imputer', SimpleImputer(strategy='median')),
    ('std_scaler', StandardScaler())
])

housing_num_tr = num_pipeline.fit_transform(housing_num)
print(f'housing_num_tr : \t\n {housing_num_tr}')
print()