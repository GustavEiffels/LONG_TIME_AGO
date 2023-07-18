import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import platform

import sklearn.preprocessing
from matplotlib import font_manager, rc
from sklearn import preprocessing
from sklearn.preprocessing import PolynomialFeatures
from sklearn.covariance import EllipticEnvelope


# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)



'''
순서가 있는 경우의 변환 
'''
print("==================== 데이터 생성 ")
df = pd.DataFrame(
    {"점수": ["저조", "보통", "우수", "보통", "저조"]}
)
print(df)
print()

print("====================== 의미를 직접 부여")
scale_mapper = {"저조": 1, "보통": 2, "우수": 3 }
df['encoder'] = df['점수'].replace(scale_mapper)
print(df)
print()


'''
문자열이나 숫자 ( 문자로 취급 )를 정렬해서 Encoding
'''
print("문자열이나 숫자 ( 문자로 취급 )를 정렬해서 Encoding")
features = np.array([["Low", 10],
                     ["High", 3],
                     ["Medium", 27]])

ordinal_encoder = preprocessing.OrdinalEncoder()
print(ordinal_encoder.fit_transform(features))