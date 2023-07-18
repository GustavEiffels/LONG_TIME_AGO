import pandas as pd
from pandas import Series, DataFrame

# help(pd.read_html)

li = pd.read_html('https://ko.wikipedia.org/wiki/%EC%9D%B8%EA%B5%AC%EC%88%9C_%EB%82%98%EB%9D%BC_%EB%AA%A9%EB%A1%9D')

# 최상단 부분만 출력
print("최상단 부분만 출력")
print(li[0])