import numpy as np

ar1 = np.array([100, 200, 300])


# 한줄 짜리 함수를 생성
def add_func(i):
    return i + 100


# 람다를 이용해서 작성
add_lamda = lambda i: i + 100

# vector 화 된 함수 생성
vectorized_add_lamda = np.vectorize(add_lamda)

# vector 화 된 함수를 이용한 데이터 가공
print(vectorized_add_lamda(ar1))
