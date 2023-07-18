import nltk
from nltk import word_tokenize
from sklearn.preprocessing import MultiLabelBinarizer

maxims = ["Well from next time",
          "Have to think more about to reuse code",
          "when I make some method in the project",
          "because a few minutes ago",
          "I tried to use my method I made",
          "but It was so complicated to re use"]

'''
품사 태깅한 내용을 저장할 list 
'''
tagged_maxims = []
for maxim in maxims:
    maxim_tag = nltk.pos_tag(word_tokenize(maxim))
    tagged_maxims.append([tag for word, tag in maxim_tag])

'''
품사 확인 
-- 문장에서 포함된 단어들의 품사들을 볼 수 있다
'''
for temp in tagged_maxims:
    print(temp)

'''
여러개의 열에 1 을 설정할 수 있는 one hot Encoding
'''
from sklearn.preprocessing import MultiLabelBinarizer

one_hot_multi = MultiLabelBinarizer()
print(one_hot_multi.fit_transform(tagged_maxims))
print()
'''
이렇게 끝나면 읽기 어려움
----->> 각 문장이 어떤 품사들로 구성되어 있는지 확인 가능 
'''
