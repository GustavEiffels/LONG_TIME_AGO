import numpy as np
import pandas as pd
from pandas import Series,DataFrame

# SQLite3 연동
# 관계형 데이터 베이스에서 공통된 Interface 를 사용하기 위한 package
print("관계형 데이터 베이스에서 공통된 Interface 를 사용하기 위한 package")

# sqlite3 를 사용하기 위한 package
import sqlite3

# 데이터 베이스 연결
con =sqlite3.connect('./sample.db')
print("conecting result ====")
print(con)
print()

# 데이터베이스에 sql을 실행하기 위한 cursor 를 가져온다
print("데이터베이스에 sql을 실행하기 위한 cursor 를 가져온다")
print()
cursor = con.cursor()

# pl 이라는 테이블이 존재하면 삭제하는 - sql을 실행
print("pl 이라는 테이블이 존재하면 삭제하는 - sql을 실행")
cursor.execute("drop table if exists pl")
print()

# table 생성
print("table 생성")
cursor.execute("create table pl(id integer not null primary key autoincrement,"
               "name text not null,"
               "language text not null)")
print()

# 데이터 삽입
print("데이터 삽입==========")
cursor.execute("""insert into pl(name, language)
                values('반다이', 'Java')""")
cursor.execute("""insert into pl(name, language)
                values('커피', 'Java')""")
cursor.execute("""insert into pl(name, language)
                values('바님', 'python')""")
print()


print("검색 ")
cursor.execute('select * from pl')
for row in cursor:
    print(row)

print()
# 작업 완료
print("complete :")
con.commit()

