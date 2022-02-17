import folium

m = folium.Map(location=[37.572656, 126.973304], zoom_start=15)

# marker 출력
folium.Marker(location=[37.572656, 126.973304], popup='KB',
              icon=folium.Icon(icon='cloud')).add_to(m)
# 색상 마커
folium.Marker(location=[37.569027, 126.987279], popup='the',
              icon=folium.Icon(color='red')).add_to(m)

# 도형 그리기 - 단계 구분도에 이용
folium.RegularPolygonMarker(location=[37.5710, 126.98],
                            popup='Poly Marker',
                            number_of_sides=8,
                            fill_color='#768e123',
                            radius=30).add_to(m)
m.save('./map2.html')
