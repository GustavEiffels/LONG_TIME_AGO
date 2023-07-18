# 크롬 실행
from selenium import webdriver

# 드라이버 경로를 설정하면 Chrome 실행
driver = webdriver.Chrome("./chromedriver")

# 다음 login page로 이동
driver.get('https://www.acmicpc.net/login?next=%2F')

# 5초 정도 대기
driver.implicitly_wait(5)

# 아이디와 비밀번호 입력
driver.find_element_by_xpath('//*[@id="login_form"]/div[2]/input').send_keys('')
driver.find_element_by_xpath('//*[@id="login_form"]/div[3]/input').send_keys('!')


# 로그인 버튼을 찾아서 클릭
driver.find_element_by_xpath('//*[@id="submit_button"]').click()
