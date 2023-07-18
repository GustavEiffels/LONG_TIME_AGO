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


student = pd.read_csv('./student.csv', encoding='cp949')
student.info()
print(student.head())

'''
graph 그리기 
'''
student.index=student['이름']
student.plot(kind='bar')

'''
표준 값 구하기 
'''
kormean, korstd = student['국어'].mean(), student['국어'].std()
engmean, engstd = student['영어'].mean(), student['영어'].std()
matmean, matstd = student['수학'].mean(), student['수학'].std()

student['국어표준값'] = (student['국어']-kormean)/korstd
student['영어표준값'] = (student['영어']-engmean)/engstd
student['수학표준값'] = (student['수학']-matmean)/matstd

print(student)
# 값이 너무 작아서 비교하기 힘듬

'''
편차 값 구하기
'''
student['국어편차값'] = student['국어표준값'] * 10 + 50
student['영어편차값'] = student['영어표준값'] * 10 + 50
student['수학편차값'] = student['수학표준값'] * 10 + 50

print(student)
student[['국어편차값', '수학편차값', '영어편차값']].plot(kind='bar')
plt.figure()
student
plt.show()

