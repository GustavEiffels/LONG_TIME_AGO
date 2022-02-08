import matplotlib.pyplot as plt

test1 = [100, 120, 140, 160, 180]
test2 = [50, 90, 120, 180, 360]

plt.figure(figsize=(5, 3))
plt.bar(range(0, 5, 1), test1, width=0.4)
plt.bar(range(0, 5, 1), test2, width=0.4, bottom=test1)
plt.show()
