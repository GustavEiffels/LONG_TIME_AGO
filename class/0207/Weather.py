import bs4
import requests as req
import bs4 as b

# html 확인
res = req.get(
    'https://www.weather.go.kr/weather/observation/currentweather.jsp'
)

# print(res.text)

soup = bs4.BeautifulSoup(res.text, 'html.parser')
# print(soup)

# 원하는 정보가 존재하는 테이블을 선택
table = soup.select('#content_weather > table')
print(table)

print('================================================')
# cell 의 이름만 추출 ---> cell 은 th
# table 이 list 형태라서  0
for temp in table[0].find_all('th'):
    print(temp.text)


print('================================================')
# table 의 각 행을 전부 추출
for temp1 in table[0].find_all('tr'):
    # 각 줄의 각 칸들을 list 로 변환 줄단위로 list 변화
    tds = list(temp1.find_all('td'))

    #list 순회
    for td in tds:
        if td.find('a'):
            print(td.find('a').text, ":", tds[7].text, ":", tds[10].text, ":", tds[11].text)
