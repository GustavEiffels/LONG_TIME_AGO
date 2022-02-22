import cv2

# 하나의 영역 선택
# 컬럼 이미지의 화소 안의 채널 접근
img = cv2.imread('./pikachu.jpg')
img1 = cv2.imread('./pikachu.jpg')

'''
ROI 는 종료시 C 를 눌러야한다.
'''

# 격자 생성
roi = cv2.selectROI(img, showCrosshair=True)
print(roi)

# 격자 생성 취소
roi1 = cv2.selectROI(img1, showCrosshair=False)
print(roi1)

'''선택한 영역을 추출해서 출력'''
select_img = img[roi[1]:roi[1]+roi[3], roi[0]:roi[0]+roi[2]]
select_img1 = img1[roi1[1]:roi1[1]+roi1[3], roi1[0]:roi1[0]+roi1[2]]

cv2.imshow('img', select_img)
cv2.imshow('img1', select_img1)

cv2.waitKey(0)
cv2.destroyAllWindows()