from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize

'''
한글 불용어 처리 ---> 나중에 크롤링 해서 들고오면 괜찮겠다 
'''
korean = "어 오늘 내가 해야할 일은 로그인 처리 시 유저코드를 세션에 부여 하는 것이다"

'''
불용어 사전 
'''
korean_stopword_dictionary = ['어', '시', '하는', '것이다']

'''
문장을 단어 단위로 토큰화
'''
word_kor = word_tokenize(korean)

'''
출력
'''
result = [k for k in word_kor if k not in korean_stopword_dictionary]
print(result)

