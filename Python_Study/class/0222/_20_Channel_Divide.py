import cv2
import _pickachu as p

img = p.img
print(img.shape)
print("-------------------")

'''
채널 분리  --- 2차원 배열 3개로 변경됨 
'''

print("-------------------")
dst = cv2.split(img)
print(dst)


'''
채널 병합 
'''
merge = cv2.merge(dst)

# 추출된 생상의 값만으로 이미지 출력
cv2.imshow('img', img)
cv2.imshow('blue', dst[0])
cv2.imshow('green', dst[1])
cv2.imshow('red', dst[2])

cv2.imshow('merge', merge)

cv2.waitKey(0)
cv2.destroyAllWindows()