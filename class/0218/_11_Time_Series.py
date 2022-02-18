import pandas as pd

stock = pd.read_csv('./stock-data.csv')
'''
첫번째 열인 Date 의 자료형이 Object 
'''
print("stock_data file 의 상위 5개 읽어보기 -----------")
print(stock.head())
print()
print("stock- data csv 파일의 정보 =================")
stock.info()
print()

'''
 DATE  열이 OBject 임을 확인
 ---- DATA Type을 변환 
'''
stock["NewDate"] = pd.to_datetime(stock['Date'])
stock.info()
'''
NewDate 가 새롭게 추가됨 
'''