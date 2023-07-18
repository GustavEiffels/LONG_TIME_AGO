import seaborn as sns
iris = sns.load_dataset('iris')
print(type(iris))
print()
print(iris.head())
'''
<class 'pandas.core.frame.DataFrame'>

   sepal_length  sepal_width  petal_length  petal_width species
0           5.1          3.5           1.4          0.2  setosa
1           4.9          3.0           1.4          0.2  setosa
2           4.7          3.2           1.3          0.2  setosa
3           4.6          3.1           1.5          0.2  setosa
4           5.0          3.6           1.4          0.2  setosa
'''


''' 
sepal_length,
 sepal_width,
  petal_length,
   petal_width
    를 가지고
    species를 분류하는 모델을 만드는 경우 '''

# species 배열을 drop 시킴
''' 특징 배열이 됨 '''
X = iris.drop('species', axis=1)
print(X.head())
print()
'''result 
sepal_length  sepal_width  petal_length  petal_width
0           5.1          3.5           1.4          0.2
1           4.9          3.0           1.4          0.2
2           4.7          3.2           1.3          0.2
3           4.6          3.1           1.5          0.2
4           5.0          3.6           1.4          0.2
'''

# Target 배열
y = iris['species']
print(y.head())
print()
'''result 
0    setosa
1    setosa
2    setosa
3    setosa
4    setosa
'''