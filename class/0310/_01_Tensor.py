from scipy import misc
import matplotlib.pyplot as plt

img_rgb = misc.face()

''' 데이터 구조 확인 '''
print(f' img_rgb shape : {img_rgb.shape}')
# result :img_rgb shape : (768, 1024, 3)

plt.imshow(img_rgb, cmap=plt.cm.gray)
plt.axis('off')
plt.title('RGB COLOR')
plt.show()

''' red 값만 가지고 출력'''
plt.imshow(img_rgb[:, :, 0], cmap=plt.cm.gray)
plt.axis('off')
plt.title('RGB COLOR Only Red')
plt.show()
