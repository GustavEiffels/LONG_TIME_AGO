from nltk.corpus import stopwords
from nltk.tokenize import sent_tokenize
from nltk.tokenize import word_tokenize

String = "2 hours ago  I bought a cup of coffee which is Americano" \
         "It is price 1500 won in korea"
print("단어 Tokenizer ==========")
print(word_tokenize(String))
print()
word_english = word_tokenize(String)

'''
불용어 적용 관사 접속사 삭제
'''

print("Stopword----------------------------------------------")
result = [w for w in word_english
          if not w in stopwords.words('english')]
print(result)
print()


'''
sklearn 사용
-- sklearn 이 nltk 보다 불용어가 많다
'''
print("sklearn 사용---------------------------------------------")
from sklearn.feature_extraction.text import ENGLISH_STOP_WORDS
result = [w for w in word_english
          if not w in ENGLISH_STOP_WORDS]
print(result)