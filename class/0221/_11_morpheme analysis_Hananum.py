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
    한글 형태소 분석 // 한나눔 
'''
from konlpy.tag import Hannanum
hannanum = Hannanum()

# case1
print(hannanum.nouns(text))
print()
'''
['당황', '오늘', '일']
'''

# case 2
print(hannanum.pos(text))
print()
'''
[('항상', 'M'),
 ('천천히', 'M'),
  ('당황', 'N'), ('하', 'X'), ('지', 'E'), 
  ('말', 'P'), ('고', 'E'), ('오늘', 'N'), 
  ('도', 'J'), ('서두르', 'P'), ('다가', 'E'), 
  ('일', 'N'), ('을', 'J'), ('그르치', 'P'), ('었다', 'E')]
'''

# case 3
print(hannanum.morphs(text))
print()
'''
['항상', '천천히', '당황', '하', '지', '말', '고', '오늘', '도', '서두르', '다가', '일', '을', '그르치', '었다']
'''


