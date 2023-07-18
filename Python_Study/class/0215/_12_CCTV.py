# cctv 데이터 읽어오기
# 서울시 구별 CCTV
# 시각화 패키지
import matplotlib.pyplot as plt
# 시각화 할 때 한글을 출력할 수 있도록 하기 위해서
import numpy as np
import pandas as pd
import platform
from matplotlib import font_manager, rc

# 한글처리
# 매킨토시의 경우
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# cctv 데이터 읽어오기
cctv = pd.read_excel("./cctv.xlsx")
# cctv.info()

# 인구 데이터 읽기 -pop.txt
pop = pd.read_csv('./pop.txt', encoding='utf-8',
                  delimiter='\t', thousands=',', skiprows=2)
print(pop.head())

# 열이름 변경과 인덱스 설정
# cctv에서 첫번째 컬럼의 이름을 구별로 변경
cctv.rename(columns={cctv.columns[0]: '구별'}, inplace=True)
# cctv.info()
# pop에서 두번째 컬럼의 이름을 구별로 변경
pop.rename(columns={pop.columns[1]: '구별'}, inplace=True)
# pop.info()

# 공통된 컬럼의 값을 동일한 구조로 변경
# cctv 의 구별 데이터의 공백을 모두 제거
gu = []
for x in cctv['구별']:
    gu.append(x.replace(' ', ''))

cctv['구별'] = gu
print(cctv['구별'].head())

#데이터 필터링

# pop 에서 기간, 구별 , 계, 남자, 여자 열만 추출

pop_filter = pop[['기간', '구별', '계', '남자', '여자']]
pop_filter

# pop_filter 에서 첫번쩨 행 제거
pop_filter_result = pop_filter.drop([0])
pop_filter_result
print(pop_filter_result.head())
print()


# 새로운 열 추가
print("새로운 열 추가 ")
pop_filter_result['여성비율'] = pop['여자']/pop['계']*100
pop_filter_result
print(pop_filter_result)
print()

# 2 개의 데이터 합치기 - key 는 구별
print("2 개의 데이터 합치기 - key 는 구별")
df = pd.merge(cctv, pop_filter_result, on='구별')
print(df.head())
print()


# 불필요한 열 삭제 - 원하는 열만 추출해도 되고 , 불필요한 컬럼을 제거해도 됨
print("Before===============")
print(df.head())
print("After===============")
df_filtering = df.drop(['2011년 이전'], axis=1)
print(df_filtering.head())
print()


# 구별을 index 로 설정
print("구별을 index 로 설정")
df_idx = df_filtering.set_index('구별')
print(df_idx.head())
print()

# 그래프 그리기
print("cctv 개수를 인구수로 나눈 결과 시각화 ")
df_idx['cctv 비율'] = df_idx['소계']/df_idx['계']*100
df_idx['cctv 비율'].sort_values().plot(kind='barh', grid=True, figsize=(5, 5))
plt.show()
print()



# 시각화 산점도, 산포도
print("시각화 산점도, 산포도 그리기")
# pandas Series, DataFrame 사용---> kind
plt.figure(figsize=(5, 5))

# s 는 크기 (scale)
plt.scatter(df['계'], df['소계'], s=50)
plt.xlabel('population')
plt.ylabel('cctv 개수')
# 산포도가 대각선 기준으로 퍼져있으면 회귀분석의 대상이 될 수도 있다.
plt.show()

print()

print("산점도에 회귀선 적용 ")
# 2개의 데이터를 가지고 회귀선을 생성
# 뒤의 숫자 1 은 일차방정식!!
fp1 = np.polyfit(df_idx['계'], df_idx['소계'], 1)
f1 = np.poly1d(fp1)
print(f1)
# 100000 부터 7000000 까지 100 개로 분할한 데이터 생성
fx = np.linspace(100000, 700000, 100)

# 잔차 계산 : 잔차 -- 실제 데이터와 예측한 데이터의 차이
df_idx['잔차'] = np.abs(df_idx['소계']-f1(df_idx['계']))

plt.figure(figsize=(10, 5))
plt.scatter(df_idx['계'], df_idx['소계'], c=df_idx['잔차'], s=10)
plt.plot(fx, f1(fx), ls='dashed', lw=3, color='g')

# 텍스트 입력
for n in range(24):
    # 앞의 2개는 좌표이고 세번째 텍스트는  fontsize
    plt.text(df_idx['계'][n]*1.02,
             df_idx['소계'][n]*0.98,
             df_idx.index[n],
             fontsize=12)

plt.xlabel('인구수')
plt.ylabel('cctv 개수')
plt.show()
