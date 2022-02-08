import sys, pymysql

# 연결 객체 변수 생성

con = None


try:
    con = pymysql.connect(
        host='127.0.0.1',
        port=3306,
        db="python",
        user="singsiuk",
        passwd="ssw0304",
        charset="utf8"
    )

    # 데이터를 조회하는 SQL
    cursor = con.cursor()

    cursor.execute("select * from usertbl")
    for row in cursor:
        print(row)
    # 데이터 1 개 가져오기 -----> tuple 로 생성
    # data = cursor.fetchone()
    # print(data)
    # print(data )

    print("horizon----------------------------")
    # 데이터 행 단위로 출력
    # for item in data:
    #     print(item)

except Exception as e:
    print(e)
    print(sys.exc_info())

finally:
    if con is not None:
        con.close()
