from nltk import pos_tag
from nltk import word_tokenize

sentence = 'During this break I recorded what I was doing on a team project'

'''
토큰화
'''
print("----- Tokenizer -----")
tokens = word_tokenize(sentence)
print(tokens)
print()
'''
품사 태깅 
'''
print("----- 품사 태깅 -----")
tags = pos_tag(tokens)
print(tags)
print()
'''
특정 품사만 골라내기 
'''
print("----- 특정 품사만 골라내기 -----")
print([word for word, tag in tags if tag in ['NN', 'NS', 'NNP', 'NNPS']])



