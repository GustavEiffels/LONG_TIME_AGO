import requests

# response = requests.get("https://www.acmicpc.net/problem/1072")
#
# print(response.text)
#
# response = requests.get("https://www.youtube.com/watch?v=jMycidDAGe8&feature=youtu.be")
# print(response.text)


# image file 저장

imgurl = "https://image.fmkorea.com/files/attach/new/20180627/425547776/837628905/1125113061/be82af9c593fedfcc40d20b5a9dae43c.png"
response = requests.get(imgurl)

with open('./pika.png', 'wb') as f:
    img = response.content
    f.write(img)