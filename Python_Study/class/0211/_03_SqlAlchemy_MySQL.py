import numpy as np
import pandas as pd
import pymysql
from pandas import Series, DataFrame
import sqlalchemy
pymysql.install_as_MySQLdb()
import pymysql
# 데이터 베이스 연결
from sqlalchemy import create_engine

con = create_engine("mysql+mysqldb://singsiuk:ssw0304@localhost/python")

# 데이터 읽어오기
df = pd.read_sql_query("select * from usertbl", con)
print(df)