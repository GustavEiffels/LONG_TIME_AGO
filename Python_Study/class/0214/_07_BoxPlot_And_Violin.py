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

tips = sns.load_dataset('tips')
plt.figure()
# sns.boxplot(x='day', y='total_bill', data=tips)
#
#
# # violin : 빈도수 파악은 되지만 일반적인 방식의 이상치를 별도로 출력하지는 않음
# sns.violinplot(x='day', y='total_bill', data=tips)

# # 막대 그래프
# print('hue --> 범주 추가 ')
# sns.barplot(x='smoker', y='total_bill', hue='time', data=tips)

# 빈도수 출력
# print(" 빈도수 출력 ====")
# sns.countplot(x='smoker', hue='day', data=tips)

# # 상관관계 출력 - 모든 상관관계 출력
# sns.pairplot(tips)

# 상관관계 와 회귀선을 같이 출력
sns.jointplot(x='total_bill', y='tip', kind='reg', data=tips)
plt.show()



