import numpy as np

# 0 부터 9 까지 비복원 추출을 이용해서 배열을 만들어서 리턴
print("0 부터 9 까지 비복원 추출을 이용해서 배열을 만들어서 리턴")
print(np.random.permutation(10))
print()


# seed를 고정
np.random.seed(42)

# 0 부터 9 까지 비복원 추출을 이용해서 배열을 만들어서 리턴 다시
print("0 부터 9 까지 비복원 추출을 이용해서 배열을 만들어서 리턴 다시 ")
print(np.random.permutation(10))
print()

# 비복원 추출 : 같은 값이 나오지 않음