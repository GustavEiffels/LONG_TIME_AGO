import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import  accuracy_score
from sklearn.datasets import load_digits
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import confusion_matrix
from sklearn.decomposition import PCA
from sklearn.mixture import GaussianMixture
import pandas as pd
from sklearn.model_selection import StratifiedShuffleSplit
import matplotlib.image as mpimg
from pandas.plotting import scatter_matrix
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import OrdinalEncoder
from sklearn.preprocessing import OneHotEncoder
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
#산포도 출력
import platform
from matplotlib import font_manager, rc
import matplotlib
#매킨토시의 경우
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
#윈도우의 경우
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    rc('font', family=font_name)
#우분투 리눅스의 경우
else:
    font_name = '/usr/share/fonts/truetype/nanum/NanumMyeongjo.ttf'
    rc('font', family=font_name)
#음수 출력
matplotlib.rcParams['axes.unicode_minus'] = False


# 파이썬 ≥3.5 필수
import sys
assert sys.version_info >= (3, 5)
# 공통 모듈 임포트
import numpy as np
import pandas as pd
import os
# 깔끔한 그래프 출력을 위해 %matplotlib inline
import matplotlib as mpl
import matplotlib.pyplot as plt
mpl.rc('axes', labelsize=14)
mpl.rc('xtick', labelsize=12)
mpl.rc('ytick', labelsize=12)

# 그림을 저장할 위치 - 현재 디렉토리에 images/classification
PROJECT_ROOT_DIR = "."
CHAPTER_ID = "classification"
IMAGES_PATH = os.path.join(PROJECT_ROOT_DIR, "images", CHAPTER_ID)

os.makedirs(IMAGES_PATH, exist_ok=True)

# matplotlib.pyplot 으로 출력한 이미지를 저장하기 위한 함수
def save_fig(fig_id, tight_layout=True, fig_extension="png", resolution=300):
    path = os.path.join(IMAGES_PATH, fig_id + "." + fig_extension)
    print("그림 저장:", fig_id)
    if tight_layout:
        plt.tight_layout()
        plt.savefig(path, format=fig_extension, dpi=resolution)

# 그래프 한글 처리
from matplotlib import font_manager, rc
import platform
path = "c:/Windows/Fonts/malgun.ttf"
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)
# 그래프 음수 처리
mpl.rcParams['axes.unicode_minus'] = False
# Jupyter Notebook의 출력을 소수점 이하 3자리로 제한
# %precision 3
# DataFrame의 출력을 소수점 이하 3자리로 제한
pd.set_option('display.precision', 3) # 'display.percision' 라고 작성해야 정상적으로 동작 함

import seaborn as sns
import scipy as sp
from scipy import stats

# 사이킷런 ≥0.20 필수
import sklearn
assert sklearn.__version__ >= "0.20"

# 노트북 실행 결과를 동일하게 유지하기 위해
np.random.seed(42)





# 데이터 읽어오기
loan3000 = pd.read_csv('./data/loan3000.csv')
loan3000.info()





''' 종속 변수의 자료형이 object 라서 category로 변경 '''
loan3000.outcome = loan3000.outcome.astype('category')
loan3000.info()







''' 3. 독립변수와 종속 변수를 분리  '''
X = loan3000[['borrower_score', 'payment_inc_ratio']]
y = loan3000['outcome']

print(X.head())
print()
print(y.head())
print()


# LDA 로 훈련


''' LDA 로 훈련'''
print()
print(''' LDA 로 훈련''')
print()
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis

loan_lda = LinearDiscriminantAnalysis()
loan_lda.fit(X, y)



# 예측 확률 확인


''' 예측 확률 확인 '''
print("예측 확률 확인")
print()
print(f'loan_lda.classes_  : {loan_lda.classes_}')
print()
print(f"loan_lda.predict_proba(loan3000[['borrower_score', 'payment_inc_ratio']])")
print(loan_lda.predict_proba(loan3000[['borrower_score', 'payment_inc_ratio']]))
print()




''' 예측 결과 확인 '''
print("예측 결과 확인")
print()
print(loan_lda.predict(loan3000[['borrower_score', 'payment_inc_ratio']]))
print()



# --------------------------------------------------------------------
'''# 예측 결과 확인 
## 데이터가 1개 더라도 종속 변수는 2 차원 배열이 되어야한다.
# '''

sample = np.array([[0.7, 0.7]])
print(loan_lda.predict(sample))
print()