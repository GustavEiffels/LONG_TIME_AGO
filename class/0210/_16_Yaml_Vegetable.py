import yaml

# yaml 열고 읽을 때
print("Read yaml ===")
with open('./vegetables.yml') as f:
    vegetables = yaml.load(f, Loader=yaml.FullLoader)
    print(vegetables)

# yaml 파일 새로 생성
print("Create Yaml File by write ========= ")
fruits = {'fruits': ['apple', 'banana', 'coconut', ]}
with open('./fruits.yml', 'w') as f:
    yaml.dump(fruits, f)