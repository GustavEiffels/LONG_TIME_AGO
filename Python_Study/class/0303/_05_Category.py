import numpy as np
import pandas as pd
import seaborn as sns
from scipy import stats as sp

# 시각화 패키지
import matplotlib.pyplot as plt

# 시각화에서 한글을 사용하기 위한 설정
import platform
from matplotlib import font_manager, rc

if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
# 윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)

# 시각화에서 음수를 표현하기 위한 설정
import matplotlib

matplotlib.rcParams['axes.unicode_minus'] = False


#6개의 확률을 생성
mu = [0.1, 0.1, 0.1, 0.1, 0.3, 0.3]

''' 다항 분포 클래스를 이용해서 카테고리 분포 객체를 생성 '''
rv = sp.multinomial(1, mu)

xx = np.arange(1, 7)

# 원핫 인코딩
print(xx)
print()
xx_ohe = pd.get_dummies(xx)
print(xx_ohe)
print()


result = rv.pmf(xx_ohe.values)
print(result)

# 샘플 데이터 100개 생성
np.random.seed(42)
X = rv.rvs(100)
print(X)

y = X.sum(axis=0) / float(len(X))
plt.bar(np.arange(1, 7), y)
plt.show()

print()

''' 다항 분포 '''
# 시행 횟수 와 각 카테고리의 확률 생성

''' 시행 횟수 '''
N = 30
mu = [0.1, 0.1, 0.1, 0.1, 0.3, 0.3]

rv = sp.multinomial(N, mu)

''' Simulation '''
np.random.seed(42)
X = rv.rvs(100)
print(X)
print()

''' 데이터 프래임으로 변경 '''
df = pd.DataFrame(X).stack().reset_index()
df.columns = ['시도 횟수', 'class', 'Standard Value']
print(df)
print()
sns.violinplot(x='class', y='Standard Value', data=df, inner='quartile')

# 값까지 같이 보고 싶을 때
sns.swarmplot(x='class', y='Standard Value', data=df, color=".3")
plt.show()

plt.boxplot(X)
plt.show()