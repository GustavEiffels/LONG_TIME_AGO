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

# Excel 파일 가져오기
population = pd.read_excel('./population.xlsx', header=1)
# print(population.head())


'''
결측치를 앞의 데이터로 채우기 
'''

population.fillna(method='ffill', inplace=True)

'''
컬럼 이름 변경하기
'''
population.rename(columns={'행정구역별(시군구)(1)': '광역시도',
                           '행정구역별(시군구)(2)': '시도',
                           '합계': '인구수'},
                  inplace=True)
print(population.head())
# 소계를 제외한 데이터만 가져오기
# population = population[(population['시도'] != '소계')]
# print(population.head())


'''
 컬럼이름 변경 
'''
population.is_copy = False
population.rename(columns={'성별(1)': '구분'}, inplace=True)
population.loc[population['구분'] == '계', '구분'] = '합계'
print(population.head())

'''
분석을 위한 새로운 컬럼 생성 
'''

population['20-39세'] = population['20~24세'] + population['25~29세'] + \
                       population['30~34세'] + population['35~39세']

population['65세이상'] = population['65~69세'] + population['70~74세'] + \
                      population['75~79세'] + population['80~84세'] + \
                      population['85세이상']

print(population.head(5))

'''피벗 테이블 생성 '''
# 열과 행단위로 통계치를 계산하기 위해서
# 열과 행단위로 집계하기 위해서 ( 기본은 합계 )
pop = pd.pivot_table(population,
                     index=['광역시도', '시도'],
                     columns=['구분'],
                     values=['인구수', '20-39세', '65세이상'])
print(pop.head())

'''컬럼 생성'''
# 도메인 지식이 있어야 한다 .
# 청년층과 노년층 비율을 가지고 새로운 컬럼 생성
pop['소멸비율'] = pop['20-39세', '여자'] / (pop['65세이상', '합계'] / 2)
print(pop.head())

'''소멸 위기 지역을 검색 ( 소멸 비율이 1보다 작은 값들을 찾기 )'''
# 값이 true  ---> 소멸 위기
# 값이 false ---> 소멸 위기 까지는 아님
pop['소멸위기지역'] = pop['소멸비율'] < 1.0
print(pop.head())

'''소멸 위기 지역 column 을 출력 '''
print(pop[pop['소멸위기지역'] == True].index.get_level_values(1))

'''index 초기화 : 인덱스를 컬럼으로 변환 '''
pop.reset_index(inplace=True)
print(pop.head())

'''첫번째 와 두번째 컬럼이름을 합쳐서 새로운 컬럼 만들기 '''

tmp_coloumns = [pop.columns.get_level_values(0)[n] +
                pop.columns.get_level_values(1)[n]
                for n in range(0, len(pop.columns.get_level_values(0)))]

pop.columns = tmp_coloumns
print(pop.head())
pop.info()

''' 시도 이름 확인 '''
print(pop['시도'].unique())

''' 시도 이름 확인 '''
# 특별시나 광역시가 아닌데 구를 가지고 있는 경우
# 구가 시도에 속한 구인지 알아야 하고 , 중복된 시도명 존재시 앞에 '도'를 붙여야함

''' 시도 이름 편집 '''
si_name = [None] * len(pop)

# 광역시가 아닌 곳 중에서 구를 가지고 있는 시도들의 구이름 디셔너리 생성
tmp_gu_dict = {'수원': ['장안구', '권선구', '팔달구', '영통구'],
               '성남': ['수정구', '중원구', '분당구'],
               '안양': ['만안구', '동안구'],
               '안산': ['상록구', '단원구'],
               '고양': ['덕양구', '일산동구', '일산서구'],
               '용인': ['처인구', '기흥구', '수지구'],
               '청주': ['상당구', '서원구', '흥덕구', '청원구'],
               '천안': ['동남구', '서북구'],
               '전주': ['완산구', '덕진구'],
               '포항': ['남구', '북구'],
               '창원': ['의창구', '성산구', '진해구', '마산합포구', '마산회원구'],
               '부천': ['오정구', '원미구', '소사구']}

for n in pop.index:
    # 고성이 2곳이므로 도를 추가
    if pop['광역시도'][n][-3:] not in ['광역시', '특별시', '자치시']:
        if pop['시도'][n][:-1] == '고성' and pop['광역시도'][n] == '강원도':
            si_name[n] = '고성(강원)'
        elif pop['시도'][n][:-1] == '고성' and pop['광역시도'][n] == '경상남도':
            si_name[n] = '고성(경남)'
        # 그 이외의 지역은 마지막 한글자를 제거해서 군 이나 시 글자를 제거
        else:
            si_name[n] = pop['시도'][n][:-1]
        for keys, values in tmp_gu_dict.items():
            if pop['시도'][n] in values:
                if len(pop['시도'][n]) == 2:
                    si_name[n] = keys + ' ' + pop['시도'][n]
                elif pop['시도'][n] in ['마산합포구', '마산회원구']:
                    si_name[n] = keys + ' ' + pop['시도'][n][2:-1]
                else:
                    si_name[n] = keys + ' ' + pop['시도'][n][:-1]
    # 세종은 이름을 수정
    elif pop['광역시도'][n] == '세종특별자치시':
        si_name[n] = '세종'
    else:
        if len(pop['시도'][n]) == 2:
            si_name[n] = pop['광역시도'][n][:2] + ' ' + pop['시도'][n]
        else:
            si_name[n] = pop['광역시도'][n][:2] + ' ' + pop['시도'][n][:-1]
print(si_name)

''' 불필요한 데이터 컬럼을 제거 '''

pop['ID'] = si_name

# 위와 같은 방법을 추천하지 않음
# 원본 데이터를 수정하기 때문에 !
del pop['20-39세남자']
del pop['65세이상남자']
del pop['65세이상여자']

print(pop.head())

'''Cartogram을 위한 엑셀 파일 읽기'''
draw_korea_raw = pd.read_excel('./draw_korea_raw.xlsx')
print(draw_korea_raw.head())

# 컬럼 이름을 인덱스로 만들기
draw_korea_raw_stacked = pd.DataFrame(draw_korea_raw.stack())
print(draw_korea_raw_stacked)

''' index 초기화 '''
draw_korea_raw_stacked.reset_index(inplace=True)
print(draw_korea_raw_stacked)

'''컬럼 이름 변경하기 '''
# level_x 등의 이름 변경
draw_korea_raw_stacked.rename(columns={'level_0': 'y', 'level_1': 'x', 0: 'ID'},
                              inplace=True)
print(draw_korea_raw_stacked)

''' 경계선 그리기 '''
# x 와 y 를 가지고 경계선 그리기
BORDER_LINES = [
    [(5, 1), (5, 2), (7, 2), (7, 3), (11, 3), (11, 0)],  # 인천
    [(5, 4), (5, 5), (2, 5), (2, 7), (4, 7), (4, 9), (7, 9),
     (7, 7), (9, 7), (9, 5), (10, 5), (10, 4), (5, 4)],  # 서울
    [(1, 7), (1, 8), (3, 8), (3, 10), (10, 10), (10, 7),
     (12, 7), (12, 6), (11, 6), (11, 5), (12, 5), (12, 4),
     (11, 4), (11, 3)],  # 경기도
    [(8, 10), (8, 11), (6, 11), (6, 12)],  # 강원도
    [(12, 5), (13, 5), (13, 4), (14, 4), (14, 5), (15, 5),
     (15, 4), (16, 4), (16, 2)],  # 충청북도
    [(16, 4), (17, 4), (17, 5), (16, 5), (16, 6), (19, 6),
     (19, 5), (20, 5), (20, 4), (21, 4), (21, 3), (19, 3), (19, 1)],  # 전라북도
    [(13, 5), (13, 6), (16, 6)],  # 대전시
    [(13, 5), (14, 5)],  # 세종시
    [(21, 2), (21, 3), (22, 3), (22, 4), (24, 4), (24, 2), (21, 2)],  # 광주
    [(20, 5), (21, 5), (21, 6), (23, 6)],  # 전라남도
    [(10, 8), (12, 8), (12, 9), (14, 9), (14, 8), (16, 8), (16, 6)],  # 충청북도
    [(14, 9), (14, 11), (14, 12), (13, 12), (13, 13)],  # 경상북도
    [(15, 8), (17, 8), (17, 10), (16, 10), (16, 11), (14, 11)],  # 대구
    [(17, 9), (18, 9), (18, 8), (19, 8), (19, 9), (20, 9), (20, 10), (21, 10)],  # 부산
    [(16, 11), (16, 13)],  # 울산
    #     [(9,14), (9,15)],
    [(27, 5), (27, 6), (25, 6)], ]

plt.figure(figsize=(8, 11))

# 지역 이름 표시
for idx, row in draw_korea_raw_stacked.iterrows():

    # 광역시는 구 이름이 겹치는 경우가 많아서 시단위 이름도 같이 표시
    # (중구, 서구)
    if len(row['ID'].split()) == 2:
        dispname = '{}\n{}'.format(row['ID'].split()[0], row['ID'].split()[1])
    elif row['ID'][:2] == '고성':
        dispname = '고성'
    else:
        dispname = row['ID']
    # 서대문구, 서귀포시 같이 이름이 3자 이상인 경우에 작은 글자로 표시한다.
    if len(dispname.splitlines()[-1]) >= 3:
        fontsize, linespacing = 9.5, 1.5
    else:
        fontsize, linespacing = 11, 1.2

    plt.annotate(dispname, (row['x'] + 0.5, row['y'] + 0.5), weight='bold',
                 fontsize=fontsize, ha='center', va='center',
                 linespacing=linespacing)
for path in BORDER_LINES:
    ys, xs = zip(*path)
    plt.plot(xs, ys, c='black', lw=1.5)

# y축의 위아래 변경
plt.gca().invert_yaxis()
# plt.gca().set_aspect(1)

# 축과 라벨 제거
plt.axis('off')

# 자동 레이아웃 설정
plt.tight_layout()
plt.show()
'''
draw_korea_raw_stacked 와 pop 의 도시이름 일치시키기
'''
# draw_korea_raw_stacked 와 pop 의 도시이름 비교
print(set(draw_korea_raw_stacked['ID'].unique()) - set(pop['ID'].unique()))
print(set(pop['ID'].unique()) - set(draw_korea_raw_stacked['ID'].unique()))

# 일치하지 않는 데이터 삭제
tmp_list = list(set(pop['ID'].unique()) - set(draw_korea_raw_stacked['ID'].unique()))

for tmp in tmp_list:
    pop = pop.drop(pop[pop['ID'] == tmp].index)

print(set(pop['ID'].unique()) - set(draw_korea_raw_stacked['ID'].unique()))

'''draw_korea_raw_stacked 와 pop 의 데이터 합치기'''
pop = pd.merge(pop, draw_korea_raw_stacked, how='left', on=['ID'])
print(pop.head())

'''좌표와 인구수를 이용해서 피봇 테이블 생성'''
mapdata = pop.pivot_table(index='y', columns='x', values='인구수합계')
print(mapdata)

'''  존재하는 데이터 확인 '''
masked_mapdata = np.ma.masked_where(np.isnan(mapdata), mapdata)
print(masked_mapdata)

'''카토그램을 생성 -DataFrame과 컬럼이름 및 색상 값을 받아서 Cartogram을 그려주는 함수'''


def drawKorea(targetData, blockedMap, cmapname):
    gamma = 0.75

    # 인구수 데이터의 크고 낮음을 분류하기 위한 값 만들기
    whitelabelmin = (max(blockedMap[targetData]) -
                     min(blockedMap[targetData])) * 0.25 + \
                    min(blockedMap[targetData])
    # 컬럼이름을 대입하기
    datalabel = targetData
    # 최대값과 최소값 구하기
    vmin = min(blockedMap[targetData])
    vmax = max(blockedMap[targetData])

    # x 와 y를 가지고 피봇 테이블 만들기
    mapdata = blockedMap.pivot_table(index='y', columns='x', values=targetData)
    # 데이터가 존재하는 것 골라내기
    masked_mapdata = np.ma.masked_where(np.isnan(mapdata), mapdata)
    # 그래프 영역 크기 만들기
    plt.figure(figsize=(9, 11))
    # 색상 설정
    plt.pcolor(masked_mapdata, vmin=vmin, vmax=vmax, cmap=cmapname,
               edgecolor='#aaaaaa', linewidth=0.5)
    # 지역 이름 표시
    for idx, row in blockedMap.iterrows():
        # 광역시는 구 이름이 겹치는 경우가 많아서 시단위 이름도 같이 표시
        # (중구, 서구)
        if len(row['ID'].split()) == 2:
            dispname = '{}\n{}'.format(row['ID'].split()[0], row['ID'].split()[1])
        elif row['ID'][:2] == '고성':
            dispname = '고성'
        else:
            dispname = row['ID']
        # 서대문구, 서귀포시 같이 이름이 3자 이상인 경우에 작은 글자로 표시
        if len(dispname.splitlines()[-1]) >= 3:
            fontsize, linespacing = 10.0, 1.1
        else:
            fontsize, linespacing = 11, 1.

        # 글자색상 만들기
        annocolor = 'white' if row[targetData] > whitelabelmin else 'black'
        # 텍스트 출력하기
        plt.annotate(dispname, (row['x'] + 0.5, row['y'] + 0.5), weight='bold',
                     fontsize=fontsize, ha='center', va='center', color=annocolor,
                     linespacing=linespacing)
    # 시도 경계 그리기
    for path in BORDER_LINES:
        ys, xs = zip(*path)
        plt.plot(xs, ys, c='black', lw=2)

        plt.gca().invert_yaxis()

        plt.axis('off')

        cb = plt.colorbar(shrink=.1, aspect=10)
        cb.set_label(datalabel)

        plt.tight_layout()
        plt.show()
        drawKorea('인구수합계', pop, 'Blues')

drawKorea('인구수합계', pop, 'Blues')


pop['소멸위기지역'] = [1 if con else 0 for con in pop['소멸위기지역']]
drawKorea('소멸위기지역', pop, 'Reds')


