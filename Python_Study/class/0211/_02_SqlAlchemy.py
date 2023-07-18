import numpy as np
import pandas as pd
from pandas import Series, DataFrame
import sqlite3
import sqlalchemy

# 데이터 베이스 연결
from sqlalchemy import create_engine

con = create_engine("sqlite:///sample.db")

# 데이터 읽어오기
df = pd.read_sql_query("select * from pl", con)
print(df)