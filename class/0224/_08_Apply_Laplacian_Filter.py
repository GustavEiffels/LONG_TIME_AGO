import cv2
import _00_Base_for_Copy as bs

''' image load '''
image = bs.readImageGray('./lena.jpg')

''' image print '''
cv2.imshow('origin', image)

''' Adapt Laplacian '''
lap = cv2.Laplacian(image, cv2.CV_32F)
print(f'Confirm Laplacian : \n {lap}')
print()

''' Laplacian with normalization'''
dst = cv2.convertScaleAbs(lap)
dst = cv2.normalize(dst, None, 0, 255, cv2.NORM_MINMAX)

''' only Laplacian Print '''
cv2.imshow('Only Laplacian Print ', lap)

''' Laplacian applied Normalization'''
cv2.imshow('Laplacian applied Normalization', dst)


''' Applied in Gaussian Filter '''
blur = cv2.GaussianBlur(image, ksize=(7, 7), sigmaX=0.0)

lap1 = cv2.Laplacian(blur, cv2.CV_32F)
dst1 = cv2.convertScaleAbs(lap1)
dst1 = cv2.normalize(dst1, None, 0, 255, cv2.NORM_MINMAX)

''' Applied in Gaussian Filter '''
cv2.imshow('Applied in Gaussian Filter', blur)
cv2.imshow('Applied in Gaussian Filter and Normalization', dst1)

''' key controller '''
bs.keyboardseet()