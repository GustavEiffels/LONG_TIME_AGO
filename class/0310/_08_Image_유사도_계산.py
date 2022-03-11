from sklearn.datasets import load_digits
import matplotlib.pyplot as gridspec
import matplotlib.pyplot as plt

# 0 ~ 1 까지 숫자 이미지 데이터 가져오기
digits = load_digits()
print(' Load digit = 0 ~ 1 까지 숫자 이미지 데이터 ')
print(digits)
print()

# 0 번 이미지를 가져오기
d1 = digits.images[0]
d2 = digits.images[10]

# 1 번 이미지 가져오기
d3 = digits.images[1]
d4 = digits.images[11]

# plt.imshow(d1)
# plt.title(' d1 = 0 ')
# plt.show()
#
# plt.imshow(d2)
# plt.title(' d1 = 10 ')
# plt.show()
#
# plt.imshow(d3)
# plt.title(' d1 = 1 ')
# plt.show()
#
# plt.imshow(d4)
# plt.title(' d1 = 11 ')
# plt.show()


''' 4개의 데이터를 1차원 벡터로 변환 '''
v1 = d1.reshape(64, 1)
v2 = d2.flatten()
v3 = d3.reshape(64, 1)
v4 = d4.flatten()

''' 동일한 image 의 vector 의 내적 '''
# v1, v2 는 2차원이기 때문에
print(f' v1 @ v2 = {v1.T @ v2}')
print()
# result :  v1 @ v2 = [3064.]

print(f' v3 @ v4 = {v3.T @ v4}')
print()
# result :  v1 @ v2 = [3661.]

''' 다른 이미지의 벡터의 내적 '''

print(f' v1 @ v4 = {v1.T @ v4}')
print()
# result :   v1 @ v4 = [1883.]

print(f' v3 @ v2 = {v3.T @ v2}')
print()
# result :  v3 @ v2 = [2421.]

''' 임계값을 설정해서 이 이미지가 같다 혹은 다르다로 설정이 가능하다 '''
