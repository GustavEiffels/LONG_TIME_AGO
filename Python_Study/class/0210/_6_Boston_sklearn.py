import pandas as pd
from pandas import Series, DataFrame

# Sklearn 의 데이터 import
from sklearn import datasets

boston = datasets.load_boston()

# dict 형으로 구성되어있는 것을 확인할 수 있다
# print(boston)
# print(f'Data Type of boston : {type(boston)}')
# type 은 <class 'sklearn.utils.Bunch'>

# key 의 이름 확인
print(f"confirm boston's key : \n{boston.keys()}")
print()

# data 가 ndarray 타입의 데이터고 feature_name이 열이름
print('--------')
print(f"Boston's columns is feature_names : \n{boston.feature_names}")
print()
print('== Type of Boston Data ==')
data = boston.data
print(type(data))
print(data)

