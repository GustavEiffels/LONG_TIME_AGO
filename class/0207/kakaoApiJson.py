import requests, json

url = 'https:' \
      '//dapi.kakao.com' \
      '/v2/local/search/' \
      'category.json?' \
      'category_group_code=' \
      'PM9&' \
      'y=37.57002838826&' \
      'x=126.97962084516&' \
      'radius=5000'
headers = {'Authorization':'KakaoAK f2601275e6109be5405e18e4b2818d7e'}

data = requests.get(url, headers=headers)
# print(data.text)

# json parsing ---> 문자 타입이 dict 이다
# header 에서 중괄호로 묶었기 때문이다
result = json.loads(data.text)
# print(type(result))

documnets = result["documents"]
print(documnets)

print("horizon=================================")

for temp in documnets:
    print(temp)

print("horizon=================================")
for temp1 in  documnets:
    print(temp1['address_name'], ':', temp1['phone'])


