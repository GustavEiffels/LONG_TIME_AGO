import pandas as pd

'''
데이터 생성 
'''
data_dict = [{"Red": 2, "Blue": 4},
             {"Red": 4, "Blue":3},
             {"Red": 1, "Yellow": 2},
             {"Red": 2, "Yellow": 2}]

from sklearn.feature_extraction import DictVectorizer

dictVectorizer = DictVectorizer(sparse=False)
print(dictVectorizer.fit_transform(data_dict))
print()
'''
알아보기 쉽게 가공 
'''
print(dictVectorizer.get_feature_names())
