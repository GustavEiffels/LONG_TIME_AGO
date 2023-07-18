import numpy as np
import seaborn as sns
import scipy.stats as ss
import matplotlib.pyplot as plt

''' 정규 분포의 가능도 함수 '''
def likelihood_mu(mu):
    return ss.norm(loc=mu).pdf(0)

mus = np.linspace(-5, 5, 1000)
likelihood_mu = [likelihood_mu(m) for m in mus]

plt.plot(mus, likelihood_mu)
plt.title('Likelihood Function')
plt.show()