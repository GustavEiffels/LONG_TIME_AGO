# -*- coding: utf-8 -*-
from pymongo import MongoClient 
#데이터베이스 연결
conn = MongoClient('127.0.0.1')

#데이터베이스 설정
db = conn.mymongo

#컬렉션 설정
collect = db.users 

'''
#document 생성 : {'key':'value'}
doc1 = {'empno':'10001', 'name':'kim', 'phone':'010-111-111', 'age':35}
doc2 = {'empno':'10002', 'name':'lee', 'age':45}
doc3 = {'empno':'10003', 'name':'park', 'phone':'010-222-222', 'age':25}
doc4 = {'empno':'10004', 'name':'choi', 'age':42}
doc5 = {'empno':'10005', 'name':'yun', 'phone':'010-222-222', 'age':37}


collect.insert_one(doc1)
collect.insert_one(doc2)
collect.insert_one(doc3)

collect.insert_many([doc4, doc5])

print(collect.count_documents({}))

collect.insert_many(
    [
        {'num': i} for i in range(10)
    ]
)

# 전체 문서 조회
result = collect.find()

#print(result)
# 조회 문서 출력
for r in result :
    print(r)
'''

'''
# 조건 검색
print('조건 검색')
result2 = collect.find({ 'age':{'$gte':30} }) #크거나 같다
for r in result2 :
    print(r)

result2 = collect.find({ 'age':{'$gte':30} }).sort("age")
for r in result2 :
    print(r)
'''

'''
collect.update_one(
    { "empno" : "10001" }, 
    { "$set" : 
        { "name" : "kkk" }
    }
)
'''

'''
collect.update_many(
    { 'age':{'$gte':30} }, 
    { "$set" : 
        { "name" : "park" }
    }
)
'''    

'''
collect.delete_many( {"age": { "$gte": 30 } } )

# 전체 문서 조회
result = collect.find()

#print(result)
# 조회 문서 출력
for r in result :
    print(r)
'''
