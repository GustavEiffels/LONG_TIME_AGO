import random
import matplotlib.pyplot as plt

simul = 100000
dice = []
for i in range(0, simul, 1):
    dice.append(random.randint(1, 6))

plt.figure()
plt.hist(dice, bins=6)
plt.title(f'{simul} time')
plt.show()