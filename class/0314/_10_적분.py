import numpy as np

import pandas as pd

import matplotlib.pyplot as plt


# numpy 와 호환되는 자료 구조 ( Series, DataFrame )
from matplotlib import font_manager, rc

# 운영 체제
import platform

# 시각화
import seaborn as sns

# 통계
import statsmodels.api as sm
import statsmodels.formula.api as swf
from statsmodels.stats import power

# 데이터 전처리 & 머신러닝
import sklearn

import matplotlib

path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)

matplotlib.rcParams['axes.unicode_minus'] = False

plt.rc("font", size=18)  # 그림의 폰트 크기를 18로 고정
gray = {"facecolor": "gray"}
black = {"facecolor": "black"}
red = {"facecolor": "red"}
green = {"facecolor": "green"}
blue = {"facecolor": "blue"}

''' LaTex 표현을 위한 설정 '''
import sympy

sympy.init_printing(use_latex='mathjax')


''' 정적분 '''
x = sympy.symbols('x')

''' 함수를 생성 '''
f = x ** 3 - 3*x ** 2 + x +6
print(f)
print()
''' result 
x**3 - 3*x**2 + x + 6
'''

''' 부정 적분을 수행 '''
F = sympy.integrate(f)
print(F)
print()
''' result 
x**4/4 - x**3 + x**2/2 + 6*x
'''

''' 0 부터 2 까지의 면적 구하기 '''
(F.subs(x, 0) - F.subs(x, 2)).evalf()
print(f'(F.subs(x, 0) - F.subs(x, 2)).evalf() : {(F.subs(x, 0) - F.subs(x, 2)).evalf()}')
print()
''' result 
(F.subs(x, 0) - F.subs(x, 2)).evalf() : -10.0000000000000
'''

''' scipy 로 구하기 -- 함수로 구해야한다 '''
def f(x):
    return x**3 - 3*x**2 + x + 6

# 함수를 이용해서 면적 구하기 - 심볼을 이용하는 경우와 동일
# 뒤의 숫자는 오차의 상한 값


