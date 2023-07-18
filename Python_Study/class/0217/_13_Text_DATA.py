text_data = [' I am a rich... ',
             ' I have to validation checking...   ',
             '           It is looks so difficult for me but I has done few day ago .......      ']
print(text_data)
print()
'''
좌우 공백 문자를 제거 
'''
print("좌우 공백 문자를 제거")
strip_text = [string.strip() for string in text_data]
print(strip_text)
print()

'''
대소문자 변경 
'''

# 1. 대문자
print(" 1. 대문자 ")
upper_text = [string.upper().strip() for string in text_data]
print(upper_text)
print()

# 2. 소문자
print(" 2. 소문자 ")
lower_text = [string.lower().strip() for string in text_data]
print(lower_text)
print()

# . 을 삭제
print(" . 을 삭제 ")
text_data = [' I am a rich... ',
             ' I have to validation checking...   ',
             '           It is looks so difficult for me but I has done few day ago .......      ']
replace_text = [string.replace('.', '').strip().upper() for string in text_data]
print(replace_text)
print()

# 모든 공백 제거
print("모든 공백 제거")
delete_empty = [string.replace('.', '').strip().upper().replace(' ','') for string in text_data]
print(delete_empty)