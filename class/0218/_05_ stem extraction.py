from nltk.stem import PorterStemmer
from nltk.tokenize import word_tokenize

sentence = "I have to give user-code to the session for using user-code " \
           "that is the primary key "
print('sentence ==========')
print(sentence)
print()
'''
단어 단위로 토큰화
'''
print("단어 단위로 토큰화 ==========")
sentence_token = word_tokenize(sentence)
print(sentence_token)
print()

'''
어간 추출
'''
print("어간 추출 ==========")
ps_stemmer = PorterStemmer()
for w in sentence_token:
    print(ps_stemmer.stem(w), end=' ')
print()