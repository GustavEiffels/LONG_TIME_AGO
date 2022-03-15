# 데이터 가져오기
import seaborn as sns
iris = sns.load_dataset('iris')
print(iris.head())
print()

# 특징 배열 과 타겟 배열로 분할
''' 특징 배열 
--- 주어지는 값 
--- 독립변수 , features 
'''
print("-- 특징 배열  --")
X_iris = iris.drop('species', axis=1)
print(X_iris.head())
print()




''' 타겟 배열
--- 종속변수 
--- 예측 할 값  , target 
'''
print("-- 타겟 배열 --")
y_iris = iris['species']
print(y_iris.head())
print()



'''
하려는 일
----> 4가지 데이터를 가지고 품종을 맞추려고 한다.
'''


# 훈련 데이터와 테스트 데이터로 분할
from sklearn.model_selection import train_test_split

''' 어떤 비율로 나뉘는지 확인 '''
print(X_iris.shape)
print()

X_train, Xtest, y_train, ytest = train_test_split(X_iris, y_iris, random_state=42)
print(X_train.shape)


# 분류 모델을 생성해서 훈련
from sklearn.naive_bayes import GaussianNB

model = GaussianNB()
print(model.fit(X_train, y_train))

# 예측
y_model = model.predict(Xtest)

# 평가 지표 확인
from sklearn.metrics import accuracy_score
print(f'result = {accuracy_score(ytest, y_model)}')

