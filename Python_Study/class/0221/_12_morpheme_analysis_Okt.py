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

from konlpy.tag import Okt
okt = Okt()

start = datetime.now()
# case 1
print(okt.nouns(text))
'''
['항상', '당황', '오늘', '일', '르']
'''

# case 2
print(okt.pos(text))
'''
[('항상', 'Noun'), ('천천히', 'Adverb'), ('당황', 'Noun'), 
('하지', 'Verb'), ('말고', 'Josa'), ('오늘', 'Noun'), 
('도', 'Josa'), ('서두르다가', 'Verb'), ('일', 'Noun'), ('을', 'Josa'), 
('그', 'Determiner'), ('르', 'Noun'), ('쳤다', 'Verb')]
'''

# case 3
print(okt.morphs(text))
'''
['항상', '천천히', '당황', '하지', '말고', '오늘', '도', '서두르다가', '일', '을', '그', '르', '쳤다']
'''
end = datetime.now()

result=(end - start)
print(result)

