import cv2
import _00_Base_for_Copy as bs

image = bs.readImageGray('./dog.jpg')

''' image print '''
cv2.imshow('Origin', image)

''' Gaussian Blur commit '''
gaus = cv2.GaussianBlur(image, (9, 9), 0, 0)
dst1 = cv2.Laplacian(gaus, cv2.CV_16S, 9)

''' Gog 가우시안 블러링을 2번 수행해서 차분을 이용 '''
gaus1 = cv2.GaussianBlur(image, (3, 3), 0)
gaus2 = cv2.GaussianBlur(image, (9, 9), 0)

dog = gaus1 - gaus2
dst2 = cv2.Laplacian(dog, cv2.CV_16S, 9)

''' image print '''
cv2.imshow('Gaus', gaus)
cv2.imshow('dog', dog)
cv2.imshow('dst1', dst1.astype('uint8'))
cv2.imshow('dst2', dst2.astype('uint8'))

''' keyboard set '''
bs.keyboardseet()