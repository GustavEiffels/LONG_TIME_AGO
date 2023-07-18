from datetime import datetime

import _00_Common

# 단어 단위로 분할해주는 함수
from nltk import word_tokenize
text = "항상 천천히 당황하지 말고 오늘도 서두르다가 일을 그르쳤다"

'''
word_ tokenizer 
'''
print("word_ tokenizer")
print(word_tokenize(text))
print()
'''
result ---------- 
['항상', '천천히', '당황하지', '말고', '오늘도', '서두르다가', '일을', '그르쳤다']
'''

'''
    한글 형태소 분석 
'''
from konlpy.tag import Kkma
Kkma = Kkma()

start = datetime.now()

'''
case 1 
'''
print(Kkma.sentences(text))
print()
'''
['항상 천천히 당황하지 말고 오늘도 서두르다가 일을 그르쳤다']
'''



'''
case 2 단어 단위로 분할 
'''
print(Kkma.nouns(text))
print()
'''
['당황', '오늘', '일']
'''



'''
case 3 품사와 함께 tagging
'''
print(Kkma.pos(text))
print()

print("end")
'''
[('항상', 'MAG'), ('천천히', 'MAG'),
 ('당황', 'NNG'), ('하', 'XSV'), ('지', 'ECD'), 
 ('말', 'VXV'), ('고', 'ECE'), ('오늘', 'NNG'), 
 ('도', 'JX'), ('서두르', 'VV'), ('다가', 'ECD'), 
 ('일', 'NNG'), ('을', 'JKO'), ('그르치', 'VV'), 
 ('었', 'EPT'), ('다', 'EFN')]
'''
end = datetime.now()


result=(end - start)
print(result)