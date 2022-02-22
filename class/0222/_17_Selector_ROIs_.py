import cv2

# 하나의 영역 선택
# 컬럼 이미지의 화소 안의 채널 접근
img = cv2.imread('./pikachu.jpg')

'''
ROI 는 종료시 C 를 눌러야한다.
'''

# 격자 생성
rois = cv2.selectROIs('img', img, False, True)
print(rois)


'''선택한 영역을 추출해서 출력'''


cv2.imshow('img', rois)

cv2.waitKey(0)
cv2.destroyAllWindows()