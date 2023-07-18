import matplotlib.pyplot as plt
import pandas as pd

import _00_Common
air = pd.read_csv("./AirPassengers.csv",
                  names=['Date', 'Passengers'])

print(air)

'''
산점도 그리기 // 계절성 데이터를 파악하기 어려움 
'''
print("산점도 그리기 // 계절성 데이터를 파악하기 어려움")
plt.Figure(figsize=(2000, 50))
plt.scatter(x=air['Date'][0:100], y=air['Passengers'][0:100], marker=2)
plt.xticks(size=10, rotation='vertical')
plt.title("승객수")
plt.show()

'''
꺾은선 그래프 그리기 - 계절성 데이터를 파악하기 어려움  
'''
print("꺾은선 그래프 그리기 - 계절성 데이터를 파악하기 어려움")
plt.Figure(figsize=(2000, 50))
plt.plot(air['Date'][0:100], air['Passengers'][0:100], marker=2, markersize=10)
plt.xticks(size=10, rotation='vertical')
plt.title("승객수")
plt.show()

'''
꺾은선 그래프 그리기 - 계절성 데이터를 파악하기 어려움  // 0~ 50
'''
print("꺾은선 그래프 그리기 - 계절성 데이터를 파악하기 어려움")
plt.Figure(figsize=(2000, 50))
plt.plot(air['Date'][0:50], air['Passengers'][0:50], marker=2, markersize=10)
plt.xticks(size=10, rotation='vertical')
plt.title("승객수")
plt.show()
