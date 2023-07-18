'''
Regex
'''
import re

'''
특수 문자와 숫자 제거 
'''

String = "Hi !! My Name is a man. 1231512b★"

'''
특수 문자 제거 
'''
print("---------- 특수 문자 제거 ----------")
p = re.compile("\W+")
# 단어가 아닌 - 숫자 나 문자가 아닌
result = p.sub(" ", String)
print(result)
print()

'''
숫자 제거 
'''
# 숫자
print("---------- 숫자 제거 ----------")
p = re.compile("\d+")
result = p.sub(" ", result)
print(result)
