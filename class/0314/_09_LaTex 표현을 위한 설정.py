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

x = sympy.symbols('x')
print(x)
print(type(x))

''' 함수를 표현 '''
f = x * sympy.exp(x)
print(f' f : {f}')
print()
'''
result
f : x*exp(x)
'''

''' 미분 - diff '''
print(f'sympy.diff(f) : {sympy.diff(f)}')
print()
'''
result
sympy.diff(f) : x*exp(x) + exp(x)
'''

''' 미분 - simplify '''
print(f'sympy.simplify(sympy.diff(f)) : {sympy.simplify(sympy.diff(f))}')
print()
'''
result
sympy.simplify(sympy.diff(f)) : (x + 1)*exp(x)
'''

''' 심볼을 생성 '''
x, y = sympy.symbols('x y')
# 다변수 함수를 생성
f = x ** 2 + 4 * x * y + 4 * y ** 2
print(f'f : {f}')
print()
'''
result 
f : x**2 + 4*x*y + 4*y**2
'''

''' 편미분 
여러 개의 입력 변수가 있는데 그 중에서 하나만 미분
편미분을 수행할 변수를 제외하고 상수로 취급  
'''
sympy.diff(f, x)
print(f'sympy.diff(f, x) : {sympy.diff(f, x)}')
print()
'''
result 
sympy.diff(f, x) : 2*x + 4*y
'''

