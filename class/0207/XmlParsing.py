import requests as req
import bs4

# html 확인
res = req.get("http://rss.donga.com/total.xml")

rss = bs4.BeautifulSoup(res.text, 'lxml-xml')
#print(rss)

# item 태그 안의 title 추출
items = rss.find_all('item')

for item in items:
    print(item.find('title').getText())