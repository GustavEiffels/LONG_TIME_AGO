import pandas as pd
import folium
from pandas import Series, DataFrame

# 엑셀 파일의 데이터를 읽어서 원형 마커를 출력

# 엑셀 파일 읽어오기 _ read_excel 인데 read_csv 와 옵션이 유사하다
df = pd.read_excel("./location_Of_University_In_Seoul.xlsx")
print(df.head())
print()
df.info()

# 서울 지도 생성
print("Create Seoul of Map")
m = folium.Map(location=[37.55, 126.98], zoom_start=11)
print()

# 3 개 열을 묶어서 각각 접근하도록 하기
for name, lat, lng in zip(df['Unnamed: 0'], df['위도'], df['경도']):
    folium.CircleMarker(location=[lat, lng],
                        radius=0,
                        color='red',
                        fill=True,
                        fill_color='coral',
                        fill_opacity=0.4,
                        popup=name).add_to(m)

# 서울지도 출력



# 서울 지도 출력
m.save('./SeoulUniversity.html')

