import numpy as np

# 0 에서 14 까지 요소가 들어있는 5, 3 matrix 생성
print("0 에서 14 까지 요소가 들어있는 5, 3 matrix 생성")
matrix = np.arange(15).reshape(5, 3)
print(matrix)
print()

# 전치 --> 방향을 바꿈 (5,3) 에서 (3,5)
print("전치 --> 방향을 바꿈 (5,3) 에서 (3,5)")
print(matrix.T)
print()

# 0 번과 1 번 축을 변경 ---> 전치를 해준 것과 값이 같다
print("0 번과 1 번 축을 변경 ---> 전치를 해준 것과 값이 같다======")
print("----- before")
print(matrix)
print("----- after")
print(matrix.transpose(1, 0))
print()


# 3 차원 만들기 // 딥러닝 할 때 차원 변환을 많이 한다
# -1 의 의미를 알아 두어야 한다 //  마지막 차원의 개수가 -1 일때 알아서 분활하여 생성
# flattern도 알아두어야 한다  // reshape 와 다르게 새로운 배열을 생성해서 리턴하는 것이다
print("3 차원 만들기 // 딥러닝 할 때 차원 변환을 많이 한다=======")
matrix = np.arange(30).reshape(5, 3, -1)
print(matrix)
print()

# 3 차원 이상일 때는 순서를 직접 설정
print("before -----------")
print(matrix)
print("after -----------")
print(matrix.transpose(0, 2, 1))

