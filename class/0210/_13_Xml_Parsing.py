# xml 파싱을 수행해서 DataFrame 만들기
import pandas as pd
from pandas import Series, DataFrame

# Web 에서 데이터를 가져오기 위한 package ---> python 기본 package

import urllib.request

# xml Parsing 을 위한 package
import xml.etree.ElementTree as et

url = "https://www.hani.co.kr/rss/sports"
request = urllib.request.Request(url)
response = urllib.request.urlopen(request)

# 가져온 내용을 DOM 으로 펼쳐서 root를 찾아오기
tree = et.parse(response)
xroot = tree.getroot()
print(xroot)
print()

# Channel 에 있는 tag 의 모든 것을 찾아오기
print("== All of Tag in Channel ==")
channel = xroot.findall('channel')
print(channel)
print()

# Item 태그의 모든것을 찾기
print("== All of things in Item Tag ==")
items = channel[0].findall('item')
print(items)
print()

rows1 = []
for node in items:
    # title 이 존재하면 찾는다
    title = node.find("title").text if node is not None else None

    # node 에 link가 존재할 때만 저장
    link = node.find('link').text if node is not None else None

    # 가져온 데이터들을 dict 로 만들어서 list 에 추가
    rows1.append({'title': title, 'link': link})

df = pd.DataFrame(rows1, columns=['title', 'link'])
print(df)

