import pandas as pd
data = pd.DataFrame(
    {'id': ['1', '2', '3', '4'],
     'fac1': ['a', 'a', 'a', 'a'],
     'fac2': ['b', 'b', 'b', 'b']}
)
print(data)
print()
print(pd.crosstab(data.fac1, data.fac2))