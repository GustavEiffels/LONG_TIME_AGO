import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import platform
from matplotlib import font_manager, rc
from sklearn import preprocessing
from sklearn.preprocessing import PolynomialFeatures

# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

'''
z - scores 를 이용하는 방법
z -score : 
        (데이터 - 평균) / 표준 편차 가 절대 값으로 3이 넘는 경우 이상 치 
'''
features = np.array([[1, 2, 3, 4, 5, 6, 7, 8, 9],
                     [11, 12, 13, 14, 15, 16, 17, 18, 999]])

'''
함수를 생성 
'''


def outliers_z_score(ys):
    threshold = 3

    # 평균 구하기
    mean_y = np.mean(ys)

    # 표준편차 구하기
    stdev_y = np.std(ys)

    # z_score 구하기
    z_scores = [(y - mean_y) / stdev_y for y in ys]

    return np.where(np.abs(z_scores) > threshold)


'''
이상치 구해보기 실행하고 결과 return
'''
print(outliers_z_score(features))
print()
'''
z- score를 보정 - MAD( 중위 절대 편차 )
'''


def outliers_MAD(ys):
    # 임계값 설정
    threshold = 3.5

    # 중앙값 구하기
    median_y = np.median(ys)

    # 편차값 구하기
    median_absoulte_deviation_y = np.median([np.abs(y - median_y) for y in ys])

    # 보정한  z_score 구하기
    median_z_score = [
        0.6745 * (y - median_y) / median_absoulte_deviation_y for y in ys
    ]

    # 이상치를 검출한 후 인덱스를 리턴
    return np.where(np.abs(median_z_score) > threshold)


featuresMAD = np.array([[1, 2, 3, 4, 5, 6, 7, 8, 9],
                        [11, 12, 13, 14, 15, 16, 17, 999, 18]])

print(outliers_MAD(featuresMAD))
print()

'''
IQR 이용 ( 3 사분위 수 - 1 사분위 수 )
1 사분위수에서 IQR * 1.5 한 값을 뺀 것보다 작거나 
3 사분위 수에서 IQR * 1.5 한 값을 더한 것 보다 큰 경우 
'''


def outliers_iqr(ys):
    # 1 사분위 수 와 3 사분위 수 구하기
    q1, q3 = np.percentile(ys, [25, 75])
    iqr = q3 - q1

    '''
    경계값 계산 
    '''
    lower_bound = q1 - (iqr * 1.5)
    upper_bound = q3 + (iqr * 1.5)
    print(f'Lower : \n {lower_bound}')
    print(f'Upper : \n {upper_bound}')

    return np.where((ys < lower_bound) | (ys > upper_bound))


featuresIQR = np.array([[1, 2, 3, 4, 5, 6, 7, 8, 9],
                        [11, 12, 13, 14, 15, 16, 999, 17, 18]])

print(outliers_iqr(featuresIQR))
