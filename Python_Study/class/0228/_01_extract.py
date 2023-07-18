import pandas as pd

''' csv 파일 읽어오기 '''

# sport_text.csv
data = pd.read_csv('../python_statistics-main/sport_test.csv')

# type 확인
print(" data. info ")
data.info()
print()
print(data.head())
print()

''' 하나의 컬럼 추출 '''

'''
이것의 차이를 구별해야한다 
'''

# 1 ---> 하나의 컬럼을 추출시 Series 로 추출
print(data['악력'])
print()
print(type(data['악력']))
print()

# 2 ----> 하나의 컬럼이더라도 결과를 DataFrame 으로 생성해서 return
''' 머신 러닝은 기본단위가 일반적으로 행렬이기 때문에 중요하다 '''
print(data[['악력']])
print()
print(type(data[['악력']]))
print()

''' 크기 확인 ---> shape '''
''' ( 행의 수 , 열의 수 )'''
print(data.shape)
print()
print(data)