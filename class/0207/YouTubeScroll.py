from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time

# 드라이버 경로
driver = webdriver.Chrome('./chromedriver')

driver.get('https://www.youtube.com/')

body = driver.find_element_by_tag_name('body')

time.sleep(5)

body.send_keys(Keys.PAGE_DOWN)

time.sleep(5)

body.send_keys(Keys.PAGE_DOWN)