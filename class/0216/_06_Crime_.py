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

# For Printing Minus
plt.rcParams['axes.unicode_minus'] = False

'''
서울 시 구별 범죄 현항 데이터를 이용한 시각화 
'''
criminal = pd.read_csv('./crime.txt', sep='\t', thousands=',', encoding='utf-8', skiprows=1)
print(criminal.head(5))
criminal.info()


print(criminal)

'''
합계 지우기 ( 행 지우기 ) -- 행 지울때는 drop 
'''
# criminal.drop(1, inplace=True)
# print(criminal.head())


'''
불필요한 컬럼을 삭제하고 컬럼 이름을 수정 
기간, 합계검거, 살인검거, 강도검거, 강간검거, 절도검거 , 폭력검거
'''
#불필요한 컬럼 제거하기
criminal.drop(['기간', '합계검거', '살인검거', '강도검거', '강간검거', '절도검거', '폭력검거'], axis=1, inplace=True)
criminal.drop([0], inplace=True)

#컬럼 이름 설정
criminal.rename(columns={'살인발생' :'살인', '강도발생':'강도', '강간발생':'강간', '절도발생':'절도', '폭력발생':'폭력'}, inplace=True)

print(criminal.head(5))

