import pandas as pd
from pandas import Series, DataFrame

df = pd.read_json('http://swiftapi.rubypaper.co.kr:2029/hoppin/movies?version=1&page=1&count=30&genreId=&order=releasedateasc')

# print(df)

# 데이터가 존재하면 정상적으로 출력
print('== hoppin ==')
hoppin = df['hoppin']
print(hoppin)
print()

print("---------- movies ----------")
movies = hoppin['movies']
print(movies)
print()

print('====== movie =====')
movie = movies['movie']
print(movie)
print()

# 반복문을 통해서 movie 에 있는 'title' data 를 출력하고 list 에 저장
rows = []
for item in movie:
    print(item['title'])
    rows.append({'title': item['title'], 'ratingAverage': item['ratingAverage']})

print()
df = DataFrame(rows)
df.columns = ['제목', '평점']
print(df)