import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import fetch_olivetti_faces

faces = fetch_olivetti_faces()

f, ax = plt.subplots(1, 3)

ax[0].imshow(faces.images[6], cmap=plt.cm.bone)
ax[0].grid(False)

ax[1].imshow(faces.images[10], cmap=plt.cm.bone)
ax[1].grid(False)

# 행렬에 스칼라 데이터를 곱한 후 더하기
ax[2].imshow(0.7 * faces.images[6]+0.3*faces.images[10],
             cmap=plt.cm.bone)
ax[2].grid(False)
plt.show()