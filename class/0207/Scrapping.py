import urllib.request

#파라미터 encoding 을 위한 모듈
from urllib.parse import quote

keyword = quote('강남')

#데이터 읽기
response = urllib.request.urlopen('https://search.naver.com/search.naver?where=news&ie=utf8&sm=nws_hty&query='
                                  +keyword)

# 바이트 배열
data = response.read()


# 읽어온 데이터의 인코딩 확인
encodings = response.info().get_content_charset()

# 인코딩 설정
html = data.decode('utf8')
print(html)