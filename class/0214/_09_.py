import plotly
import chart_studio
chart_studio.tools.set_credentials_file(username='username', api_key='api_key')
import chart_studio.plotly as py
import plotly.graph_objs as go
import plotly.figure_factory as ff
import pandas as pd

df = pd.read_csv("https://raw.githubusercontent.com/plotly/datasets/master/school_earnings.csv")

table = ff.create_table(df)
plotly.offline.iplot(table, filename='jupyter-table1')

data = [go.Bar(x=df.School, y=df.Gap)]
plotly.offline.iplot(data, filename='jupyter-basic_bar')