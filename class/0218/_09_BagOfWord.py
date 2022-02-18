from sklearn.feature_extraction.text import CountVectorizer
import numpy as np

"""테스트 데이터 만들기"""
test_data = np.array([
                'In this break',
                'I have to modify my code method',
                'to get code',
                'which is a primary key of User Table'])

'''
Bow 특성 행렬 생성
'''
count = CountVectorizer()
bag_of_word = count.fit_transform(test_data)
print("---------- Bag Of Word ----------")
print(bag_of_word)
print()
'''
분석은 가능하지만 알아보기 힘듬 
'''

''' 배열을 다시 출력'''
print("---------- 배열을 다시 출력 -----------")
print(bag_of_word.toarray())
print()

''' 각 열의 이름을 확인 '''
print("---------- 각 열의 이름을 확인 -----------")
print(count.get_feature_names())
print()