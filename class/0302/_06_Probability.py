import pandas as pd

''' 집합 '''
# 집합 생성
a = {1, 2, 3, 1, 1, 1, 2}
print(a)

# frozenset - immutable
b = frozenset(['T', 'F'])
print(b)

print(dir(a))
print()
print(dir(b))

''' unicode 사용 가능 '''
c = set(['\u2660', '\u2661', '\u2662', '\u2663'])
print(c)