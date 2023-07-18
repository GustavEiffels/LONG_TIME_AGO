import numpy as np
''' Sklearn 을 이용한 표본 추출 '''
from sklearn.model_selection import train_test_split

X = np.arange(30).reshape(15, 2)
y = np.arange(15)

grp = [0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]

print('X -------')
print(X)
print()
print('y -------')
print(y)
print()
print('grp-------')
print(grp)
print()

''' 층화 무작위 추출 '''
X_train, X_test, y_train, y_test = train_test_split(X,
                                                    y,
                                                    test_size=0.2,
                                                    shuffle=True,
                                                    stratify=grp,
                                                    random_state=1004)
print(X_train)
print()


from sklearn.model_selection import StratifiedShuffleSplit

# n_splits : 몇개로 분할할건지
split = StratifiedShuffleSplit(n_splits=1,
                               random_state=32,
                               test_size=0.3,
                               train_size=None)

for train_idx, test_idx in split.split(X, grp):
    X_train = X[train_idx]
    X_test = X[test_idx]
    y_train = y[train_idx]
    y_test = y[test_idx]

print(X_train)