# 분류 실습 - sklearn
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.datasets import load_digits
digits = load_digits()
print(digits)
print()


'''
data 키에 특징 배열이 있다 . 
target 키에 target 배열이 있다
'''

X = digits.data
print("X Feature--")
print(X.shape)
print()

y = digits.target
print("y target--")
print(y.shape)
print()


''' 데이터 분할 '''
# test 비율 8 : 2
X_train, Xtest, y_train, ytest \
    = train_test_split(X, y, test_size=0.2, random_state=42)

print(f' X_train.shape = {X_train.shape}')
print()

# model 을 생성해서 훈련
from sklearn.naive_bayes import GaussianNB

model = GaussianNB()
model.fit(X_train, y_train)
y_model = model.predict(Xtest)

# 평가 지표 확인
''' 훈련한 데이터가 아니라 다른 데이터 가지고 확인 '''
from sklearn.metrics import accuracy_score
print(f" 평가 지표 확인 결과 : {accuracy_score(ytest, y_model)}")


''' 이미지 분류 같은 경우 
어디서 오차가 많이 생기는 걸까??

------> 확인 하려면 
1. 오차 행렬을 만듬
2. heat map 을 만들어서 출력 
'''

# 오차 행렬을 heatmap 으로 출력
from sklearn.metrics import confusion_matrix
import seaborn as sns

mat = confusion_matrix(ytest, y_model)

sns.heatmap(mat, square=True, annot=True, cbar=False)

plt.xlabel('predicted vale')
plt.ylabel('true value')
plt.show()