import numpy as np
import datetime

# list 와 ndarray 작업 속도 비교

# list 생성 - 1 부터 100000000 까지
li = list(range(1, 10000001, 1))

# 현재 시간
listStartTime = datetime.datetime.now()
print(f"List Operating start time : {listStartTime}")

# 모든 데이터에 10 을 곱해서 저장
for i in li:
    i * 10;

listEndTime = datetime.datetime.now()
print(f"List Operating End time : {listEndTime}")




listResult = listEndTime-listStartTime;
print(f"Operating Time of List is : {listResult}")


# ndarray 시간 -----------------------------
print('----------------------------------------')




ar = np.arange(1, 10000001, 1)
arrayStartTime = datetime.datetime.now()
print(f"Array Operating start time : {arrayStartTime}")

# 모든 데이터에 10 을 곱해서 저장
ar * 10

arrayEndTime = datetime.datetime.now()
print(f"Array Operating End time : {arrayEndTime}")





arrayResult = arrayEndTime-arrayStartTime;
print(f"Operating Time of array is : {arrayResult}")


if arrayResult<listResult:
    print("Array 가 더 빠르다")
else:
    print("list 가 더 빠르다")

# ndrayy