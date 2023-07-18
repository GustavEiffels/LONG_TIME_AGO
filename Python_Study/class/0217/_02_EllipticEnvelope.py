import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import platform
from matplotlib import font_manager, rc
from sklearn import preprocessing
from sklearn.preprocessing import PolynomialFeatures
'''
일정 비율을 이상치로 설정 
'''
from sklearn.covariance import EllipticEnvelope

# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)



'''
10 % 를 이상치로 간주하는 객체 생성 
'''
outlier_detector = EllipticEnvelope(contamination=0.1)


'''
sample Data 생성 
'''
from sklearn.datasets import make_blobs
features, _ = make_blobs(n_samples=10,
                         n_features=2,
                         centers=1,
                         random_state=42)

print("befroe")
print(features)
print()
# 1 번 행의 데이터를 이상치로 변경
features[1, 0] = 2000
features[1, 1] = 1500
print("after")
print(features)
print()



'''
훈련 
'''
outlier_detector.fit(features)

'''
예측
'''
outlier_detector.predict(features)
print(outlier_detector.predict(features))
print()






