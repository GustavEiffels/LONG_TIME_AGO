from selenium import webdriver


# option 을 생성
options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
# 화면 안띄우기
options.add_argument('disable-gpu')

# 드라이버 경로를 설정
driver = webdriver.Chrome("./chromedriver", options=options)


driver.get('https://www.acmicpc.net/problem/1072')

print(driver.page_source)