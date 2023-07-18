import requests as re
import bs4

# html 가져오기
response = re.get("https://finance.naver.com/item/main.naver?code=005930")
html = response.text

# html memory 펼치기
bs = bs4.BeautifulSoup(html, 'html.parser')
# print(bs)

# 필요한 데이터 선택
tags = bs.select('#content > div.section.invest_trend > h4 > em')
for tag in tags:
    print(tag.getText())

