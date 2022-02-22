import cv2
import _pickachu as p

# 3차원 배열
img = p.img

'''
회전 
'''
x_axis = cv2.flip(img, 0)
y_axis = cv2.flip(img, 1)
xy_axis = cv2.flip(img, -1)

'''
반복 복사 
'''
rep_image = cv2.repeat(img, 1, 2)

'''
전치
'''
trans_image = cv2.transpose(img)

titles = ['img', 'x_axis', 'y_axis', 'xy_axis', 'rep_image', 'trans_image']

'''
이미지를 출력

eval 는 문자열을 객체로 변환해주는 함수 
'''
for title in titles:
    cv2.imshow(title, eval(title))


cv2.waitKey(0)
cv2.destroyAllWindows()