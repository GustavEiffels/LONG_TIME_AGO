# 배열과 선형대수 관련 package - machine Learning 과 Deep learning 에서 필수

import numpy as np

# Series 와 DataFrame , 데이터 전처리 및 시각화 package
# 머신러닝과 딥러닝에서 필수
# 딥러닝에서 이 자료구조를 직접 사용하지 못한다
# 딥러닝에서는 values  속성으로 ndarray 를 만들어서 사용
import pandas as pd
from pandas import Series, DataFrame

# 첫번째 배열과 두번째 배열을 맞춰줘야한다
# 행과 열이 같은 배열 정방행렬
df_source = DataFrame([['1', '2', '3', '4', '1d'],
                ['one', 'two', 'three', 'four', 'one']])

# 행과 열을 전치
df = df_source.T


# 컬럼 이름 설정
df.columns=['Numberic', 'En']

# 중복확인
# 중복된 데이터는 True
print("Checking Duplication===========")
print(df.duplicated())
print()
print(df)
print()
# 일반적으로 중복된 데이터는 제거
# 중복된 데이터는 치환하지 않고 삭제 ㄴ


# 모든 컬럼의 값이 동일한 데이터 삭제
# drop_duplicates
# drop_duplicates(inplace=True) ----> 데이터를 직접 삭젝
df.drop_duplicates(inplace=True)
print("Delete Duplicate Value")
print(df)



