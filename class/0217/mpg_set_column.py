import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import platform
from matplotlib import font_manager, rc

# For Printing Hangle
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# For Printing Minus
plt.rcParams['axes.unicode_minus'] = False

''' 데이터 읽어 오기 '''
mpg = pd.read_csv('./auto-mpg.csv', header=None)

'''
컬럼 이름 만들기 
'''
mpg.columns=['mpg',
             'Cylinders',
             'displacement',
             'horsepower',
             'weight',
             'acceleration',
             'model year',
             'origin',
             'name']

mpg.info()