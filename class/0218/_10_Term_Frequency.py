import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer

'''
테스트 데이터 만들기
-- 가중치를 알아보기 위해 중복되는 단어 code
가로로 겹침 : 중요도가 높음 
세로로 겹침 : 중요도가 낮음 
'''

text_data = np.array([
                'In this break',
                'I have to modify my code method to get code',
                'which is a primary key of User Table'])

'''
단어의 중요도를 회소행렬로 출력

하나의 문장에서 자주 등장하면 중요도는 높아짐 
여러 문장에서 자주등장하면 중요도는 낮아짐 
'''
print("단어의 중요도를 회소행렬로 출력")
tfidf = TfidfVectorizer()
feature_matrix = tfidf.fit_transform(text_data)
print(feature_matrix)
print()

'''
특성의 이름 파악 
'''
print("특성의 이름 파악")
print(tfidf.vocabulary_)
print()

'''
밀집 행렬로 변환 
'''
print("밀집 행렬로 변환")
print(feature_matrix.toarray())
