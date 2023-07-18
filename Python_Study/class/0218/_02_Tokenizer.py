from nltk.tokenize import word_tokenize
from nltk.tokenize import sent_tokenize


String = " 2hours ago  I bought a cup of coffee which is Americano. " \
         "It's price 1500 won in korea."
'''
문장 Tokenizer 
구두점을 기준으로 분할해서 list 로 리턴
'''
print("문장 Tokenizer ==========")
print(sent_tokenize(String))
print()
'''
단어 Tokenizer
공백을 기준으로 분할해서 list 로 리턴  
'''
print("단어 Tokenizer ==========")
print(word_tokenize(String))

