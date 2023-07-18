from pymongo import MongoClient

con = MongoClient('127.0.0.1')

# help(MongoClient)

# 사용할 Database 연결
db = con.mymongo

# 컬렉션 설정 -- 테이블 생성 또는 연결

collect = db.users

# ------ 데이터 삽입이 계속 되기 때문에 주석
# 삽입할 데이터 설정 - dict
# doc1 = {'empno': '0001', 'ename': 'Espresso'}
# doc2 = {'empno': '0002', 'ename': 'Americano'}
# doc3 = {'empno': '0003', 'ename': 'Cafe Latte'}
# doc4 = {'empno': '0004', 'ename': 'Capuccino'}
#
# # 데이터 삽입
# collect.insert_one(doc1)
# collect.insert_one(doc2)
# collect.insert_many([doc3, doc4])

# 조회
result = collect.find()

for temp in result:
    print(temp)
    print(type(temp['_id']))