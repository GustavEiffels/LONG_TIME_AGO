# 1. import
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='AppleGothic')
plt.rcParams['axes.unicode_minus'] = False

# 음수 출력
plt.rcParams['axes.unicode_minus'] = False

# #매킨토시의 경우
# rc('font', family='AppleGothic')
#
# plt.rcParams['axes.unicode_minus'] = False
# #윈도우의 경우
# elif platform.system() == 'Windows':
#     font_name = font_manager.FontProperties(fname=“폰트파일경로").get_name()
#     rc('font', family=font_name)
# #우분투 리눅스의 경우
# else:
#     font_name = '/usr/share/fonts/truetype/nanum/NanumMyeongjo.ttf’
#     rc('font', family=font_name)




# 데이터 생성
s1 = [10000, 200002, 900000, 800000]
s2 = [999999, 888288, 787878, 10000000]

# 그래프 크기 설정
plt.figure(figsize=(10, 4))

# 그래프를 그리기
plt.plot(s1, label="0207")
plt.plot(s2, label="0208")
plt.grid()

plt.xlabel('인덱스')
plt.ylabel('테스트')

plt.title('my money')

plt.legend()

# 그래프를 화면에 출력
plt.show()