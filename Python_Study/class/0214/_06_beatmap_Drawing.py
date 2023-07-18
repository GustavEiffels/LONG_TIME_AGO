# 히트맵 그리기 - 2개의 범주형 변수와 1개의 수치데이 필요
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

flights = sns.load_dataset('flights')
flights.info()

print("===================")

heatmap_data = flights.pivot('month', 'year', 'passengers')
print(heatmap_data)

print()
print("annot 숫자를 표현 , fmt = 'd' ---> 정수(default 는 실수) ")
print("annot 는 값 출력 여부 ")
print("fmt 는 값의 서식 ")
print("plot 의 경우는 하나의 범주만 적용, 이경우 2개의 범주 사용가능 ")
plt.figure()
sns.heatmap(heatmap_data, annot=True )
plt.show()