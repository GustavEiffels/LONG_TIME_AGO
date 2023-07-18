import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import platform
from sklearn import preprocessing
from matplotlib import font_manager, rc

# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

'''
행단위로 스케일링
'''
# 2차원 배열로 생성
features = np.array([[1, 2], [2, 3], [3, 4], [8, 8], [9, 9], [2, 8]])
print(features)
print()

'''
변환기 객체를 생성 
'''
normalizer = preprocessing.Normalizer(norm='l1')
# 두개의 숫자 합으로 나눔
print(normalizer.transform(features))

'''
이렇게 정규화를 해서 거리를 계산하여 유사도를 판단할 때 사용한다
1. 추천시스템 만들 때
2. 텍스트 들의 유사성을 판단할때 
거리를 계산 
---- 자연어 처리쪽에서 많이 사용 
'''