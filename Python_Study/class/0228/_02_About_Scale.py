import numpy as np
import pandas as pd
import cv2

# 시각화 패키지
import matplotlib.pyplot as plt

# 시각화에서 한글을 사용하기 위한 설정
import platform
from matplotlib import font_manager, rc

if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# 시각화에서 음수를 표현하기 위한 설정
import matplotlib

matplotlib.rcParams['axes.unicode_minus'] = False


df = pd.read_csv('../python_statistics-main/descriptive.csv')
print(df.shape)
print()
df.info()
print()
print(df.tail())
print()

# 각 변수의 기술 통계 정보 확인
print(df.describe())


'''
명목 척도 
구별만을 위해서 의미없는 수치로 구성한 데이터 

1. 요약 통계량을 구하는 것은 무의미 , 빈도를 확인하는 것에만 의미

2. 데이터를 분류해서 빈도를 구하고 , 막대 그래프 나 원 그래프를 이용해서 시각화

= 위의 데이터에서는 성별이나 거주지역이 명목척도 이다
'''

# 의미 없는 통계량
print(f" 의미없는 통계량 ==================== : \n    {df['gender'].describe()}")
print()


# 이 경우 빈도수를 확인한다
''' 이렇게 사용하면 이상치 확인도 가능 , unique 써도 확인 가능 '''
print(" 빈도수 확인 =======")
print(df['gender'].value_counts())

''' 이상치를 제거하고 시각화 '''
df_gender = df[(df['gender'] == 1) | (df['gender'] == 2)]
# 개수를 보여줄 때는 막대 그래프가 제일 효과적이다
plt.figure()
df_gender['gender'].value_counts().plot.bar(color='k', alpha=0.8)
plt.show()
print(df_gender['gender'].value_counts())
print()


''' 등간 척도 '''
'''값의 종류나 개수를 확인하는 것이 좋다 // 잘못된 값이 들어있을 수 있기 때문에'''
# 1 unique ()
print(df['survey'].unique())
print()

# 2 value_counts()
print(df['survey'].value_counts())
print()



''' 기술 통계량의 요약 // NAN 은 describe 가 제거한다 '''
print(df['survey'].describe())
plt.figure()
df['survey'].value_counts().plot.pie()
plt.show()
print()

''' 비율 척도  Ratio Scale '''
# 값이 너무 많아진다
print(df['cost'].value_counts().head())
print()
print(df['cost'].value_counts().tail())
print()
plt.figure()
df['cost'].value_counts().plot.bar(color='k', alpha=0.8)
plt.show()


''' 이상치 제거 '''
cost = df['cost']
print(cost.describe())
print()

''' min 이나 max 에 이상치가 있는 것을 확인할 수 있다 . 최대값과 최소 값이 너무 차이가 난다'''
# 실제 작업시 IQR을 이용하거나 해야한다.
print(cost[(cost >= 2) & (cost <= 10)].describe())


''' 바로 시각화를 하는 것이 아니라 histogram 을 생성 '''
plt.figure(figsize=(10, 6))

ys, xs, patchess = plt.hist(cost[(cost >= 2) & (cost <= 10)],
                            # 구간의 개수
                            bins=3,
                            # 퍼센트 비율 표시 여부
                            density=True,
                            # 누적 옵션
                            cumulative=False,
                            # 모양 // step , bar 존재
                            histtype='bar',
                            orientation='vertical',
                            # 너비로 1.0 이면 다른 것과 붙어서 나옴
                            rwidth=0.8,
                            color='b')
plt.xticks([(xs[i] + xs[i+1])/2 for i in range(0, len(xs)-1)],
           ['하', '중', '상'])
plt.title("생활비")
plt.show()

''' 파이 그래프 // 비율을 알고 싶을 때 '''
# pie 그래프는 hist 를 사용할 수 없어서 다른 방법 모색
cost = df['cost']

# 1.  범위 줄이기 //
cost = cost[(cost >= 2) & (cost <= 10)]

# 2. 범위에 값을 설정 // 구간화 시작 ---->
cost[(cost >= 2) & (cost <= 3)] = 1

# 3. 조건 만들기
# cost[(cost >= 2) & (cost <= 3)] = '하'
''' -----> 안되는 것은 아니지만 , 숫자와 문자가 같이 들어가서 좋지 않음 '''
cost[(cost > 3) & (cost <= 6)] = 2
cost[(cost > 6)] = 3
# 구간화 끝 -------->

# 자료형 변환
cost = cost.astype(int)

# 시각화
plt.pie(cost.value_counts(),
        labels=['low', 'middle', 'high'],
        autopct='%.1f%%')
plt.title("생활 수준 ")
plt.legend()
plt.show()

