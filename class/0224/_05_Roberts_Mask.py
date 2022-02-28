import cv2
import _00_Base_for_Copy as bs

image = bs.readImageGray('./edge.jpg')
print(f'image : \n {image}')
print()

'''image print test '''
cv2.imshow('origin', image)

''' Create Robers Mask '''
data1 = [-1, 0, 0,
         0, 1, 0,
         0, 0, 0]
data2 = [0, 0, -1,
         0, 1, 0,
         0, 0, 0]
dst, dst1, dst2 = bs.differential(image, data1, data2)  # 회선 수행 및 두 방향의 크기 계산

cv2.imshow("image", image)
cv2.imshow("roberts edge", dst)
cv2.imshow("dst1", dst1)
cv2.imshow("dst2", dst2)


bs.keyboardseet()