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
from fancyimpute import KNN

'''
데이터를 생성 
앞 데이터 * 2 = 뒤 데이터 
'''
features = np.array([
    [100, 200], [200, 400], [150, 300], [190, 380], [200, np.nan], [1, 2], [2, 4], [3, 6]
])

print(KNN(k=100, verbose=0).fit_transform(features))