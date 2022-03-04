import pandas as pd

''' CSV 파일 Load '''
university = pd.read_csv('../python_statistics-main/descriptive.csv', encoding='ms949')

print("========================= descriptive read =========================")
university.info()
print()



''' 데이터 전처리 : DATA Preprocessing '''
''' 조건 : cost  열을 삭제 '''
''' 방법 1. 필요한 열을 나열  or 2. 불필요한 열을 제거 '''


# 2. 불필요한 열을 제거
''' axis ---> 행방향 혹은 열방향을 가리킴 '''
# True 복사본에 작업한 후 리턴
university.drop('cost', axis=1, inplace=True)
print(university)
print()

''' gender 열의 값이 1 과 2로 구성 '''
#  1 은 남자
#  2 는 여자
''' 성별 컬럼을 추가 '''
university['sex'] = 'Male'
idx = 0
for v in university['gender']:
    if v == 2:
        university['sex'][idx] = 'Female'
    idx = idx + 1

university.drop('gender', axis=1, inplace=True)
print(university)
print()


''' level 컬럼은 학력 '''
''' 1.0 , 2.0 , 3.0 으로 구성 '''
''' 1.0  = 고졸 '''
''' 2.0  = 대졸 '''
''' 3.0  = 대학원졸 '''

university['Education'] = 'no'

idx = 0
for _ in university['level']:
    if _ == 1.0:
        university['Education'][idx] = '고'
    elif _ == 2.0:
        university['Education'][idx] = '대'
    elif _ == 3.0:
        university['Education'][idx] = '대학원'
    idx = idx + 1

university.drop('level', axis=1, inplace=True)
print(university)


''' Pass 변경 '''
''' Pass 컬럼은 합격 여부 '''
''' 1.0 , 2.0으로 구성 '''
# 1.0 == 합격
# 2.0 == 불합격

uni = university.copy()

university['PassOrNot'] = 'No Response'

idx = 0
for _ in university['pass']:
    if _ == 1.0:
        university['PassOrNot'][idx] = 'pass'
    else:
        university['PassOrNot'][idx] = 'None Pass'

    idx = idx + 1

print("======================================================")
uni['passOrNot'] = university.copy()['PassOrNot']
print(uni)
print()

''' 거주지 resident '''
# 1.0 ==  특별시
# 2.0 ==  광역시
# 3.0 ==  시 군

uni['Resident'] = 'No Response'

idx = 0
for _ in university['resident']:
    if _ == 1.0:
        uni['Resident'][idx] = '특별시'
    elif _ == 2.0:
        uni['Resident'][idx] = '광역시'
    elif _ == 3.0:
        uni['Resident'][idx] = '시 군 '

    idx = idx + 1
print("==uni===")
uni.drop('pass', axis=1, inplace=True)
uni.drop('resident', axis=1, inplace=True)
uni.info()
print(uni)

''' 교차 분할표 작성 '''
''' 학력과 합격 여부에서 응답 없음을 제거 '''

uni = uni[(uni['Education'] == '고')
          | (uni['Education'] == '대')
          | (uni['Education'] == '대학원')
]

uni = uni[(uni['passOrNot'] == 'pass')
          | (uni['passOrNot'] == 'None Pass')
]
print(uni)
