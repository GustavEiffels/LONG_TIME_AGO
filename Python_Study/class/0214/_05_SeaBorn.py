# seaborn 을 사용하기 위한 package 설정
import matplotlib.pyplot as plt
import platform
from matplotlib import font_manager, rc

import numpy as np
import pandas as pd
from pandas import Series, DataFrame

import seaborn as sns

# 한글처리
if platform.system() == "Darwin":
    rc('font', family="AppleGothic")
elif platform.system() == "Windows":
    font_name = font_manager.FontProperties(
        fname="c/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)



# seaborn 이 가지고 있는 tips 라는 데이터를 가져오기
# tips 는 레스토랑에서 팀에 관련해서 수집한 데이터
print('seaborn 이 가지고 있는 tips 라는 데이터를 가져오기 =========')
print()
tips = sns.load_dataset('tips')
print(tips.head())
print()
tips.info()

# 산포도
print("산포도 그리기 ===================")
plt.figure()
sns.regplot(x='total_bill', y='tip', data=tips, fit_reg=True)
plt.show()


# 산포도
print("산포도 그리기 2 ===================")
plt.figure()
sns.lmplot(x='total_bill', y='tip', hue='smoker', data=tips, fit_reg=True)
plt.show()