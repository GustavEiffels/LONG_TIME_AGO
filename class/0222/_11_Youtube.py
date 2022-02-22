import cv2
import pafy

# 동영상 url
url = "https://www.youtube.com/watch?v=rl9BGrb7s9w&t=5094s"


# 연결
video = pafy.new(url)

# 정보 출력
print('title=', video.title)
print('video.rating', video.rating)

# Youtube  동영상 캡쳐 준비
best = video.getbest(preftype='mp4')
cap = cv2.VideoCapture(best.url)

while(True):
    # 동영상에서 캡쳐 - retval 은 image 존재 여부
    # frame 이 영상
    retval, frame = cap.read()
    if not retval:
        break

    cv2.imshow('frame', frame)

    '''
    흑백으로 변환 
    '''
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    '''
    경계선 검출 
    '''
    edges = cv2.Canny(gray, 100, 200)
    cv2.imshow('edges', edges)

    key = cv2.waitKey(25)

    # 사용 정지
    if key==27:
        break

cv2.destroyAllWindows()