import pandas as pd
import folium
from pandas import Series, DataFrame

pop = pd.read_excel('./pop.xlsx')
print(pop.head())
pop.info()


# 컬럼이름을 전부 문자열로 변환
# 이름에 전부 str 함수를 적용
# 컬럼 이름이 모두 문자열이면 데이터 프레임 , 컬럼이름 또는 데이터 프레임['컬럼이름
# # 을 모두 사용할 수 있지만 문자열이 아니라면 ']
print("================== 컬럼이름을 전부 문자열로 변환 ==================")
pop.columns = pop.columns.map(str)
print(pop.head())
print()

# json parsing

import json
print("json data 읽어오기 ")
geo_path = './g.json'

# java 에서는 try 와 catch 그리고 finally 가 외부와 독접적인 block
# python 에서는 하나의 모듈은 하나의 블럭이다
# 사용하기 전에 선언만 되어 있으면 된다
try:
    geo_data = json.load(open(geo_path, encoding='utf-8'))

except:
    geo_data = json.load(open(geo_path, encoding='utf-8-sig'))

print(geo_data)


# chropleth 생성
print("chropleth 생성=================")
print("경기도 지역 지도를 생성 ")
# tiles ='Stamen Terrian' 이라고 하면 지역 이름이 사라짐
g_map = folium.Map(location=[37.5502, 126.982], zoom_start=9)

# 단계 구분도를 생성
pop.index = pop['구분']
folium.Choropleth(geo_data=geo_data,
                        data=pop['2017'],
                        columns=[pop.index, pop['2017']],
                        fill_color='YlOrRd',
                        fill_opacity=0.5,
                        line_opacity=0.2,
                        threshold_scale=[10000, 100000, 300000, 500000, 700000],
                        key_on='feature.properties.name').add_to(g_map)

g_map.save('./chropleth.html')