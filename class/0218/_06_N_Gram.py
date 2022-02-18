from nltk import ngrams
from nltk.tokenize import word_tokenize


sentence = "This is  Amazing Sound Like 흐 하"
'''
2개씩 묶기 : Bigram 
'''
print("2개씩 묶기 : Bigram")
bigrams = ngrams(word_tokenize(sentence), 2)
for i in bigrams:
    print(i, end=' ')
print()
print()


'''
3 개씩 묶기 - trigram
'''
print("3 개씩 묶기 - trigram")
trigram = ngrams(word_tokenize(sentence), 3)
for i in trigram:
    print(i, end=' ')
print()