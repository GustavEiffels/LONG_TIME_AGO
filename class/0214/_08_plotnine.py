import pandas as pd
import matplotlib.pyplot as plt
from pandas import Series, DataFrame
import plotnine

df = pd.DataFrame({'letter': ['Alpha', 'Beta', 'Delta', 'Gamma']*2,
                'pos': [1, 2, 3, 4]*2,
                'num_of_letters': [5, 4, 5, 5]*2})
print(df)

plt.figure()
(plotnine.ggplot(df)
    + plotnine.geom_col(plotnine.aes(x='letter', y='pos', fill='letter'))
            + plotnine.geom_line(plotnine.aes(x='letter', y='num_of_letters', color='letter'), size=1)
                    + plotnine.scale_color_hue(l=0.45)                   # some contrast to make the lines stick out
                            + plotnine.ggtitle('Greek Letter Analysis')
)
plt.show()
