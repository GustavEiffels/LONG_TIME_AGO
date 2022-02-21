use mymongo

db.echo.insert({num:1, name:"HDFS", function:"분산 데이터 저장"})
db.echo.insert({num:2, name:"Chuckwa", function:"비정형 데이터 수집"})
db.echo.insert({num:3, name:"Flume", function:"비정형 데이터 수집"})
db.echo.insert({num:4, name:"Sqoop", function:"정형 데이터 수집"})
db.echo.insert({num:5, name:"MapReduce", function:"분산 데이터 처리"})
db.echo.insert({num:6, name:"Hbase", function:"분산 데이터베이스"})
db.echo.insert({num:7, name:"Hive", function:"데이터 분석"})
db.echo.insert({num:8, name:"Pig", function:"데이터 분석"})
db.echo.insert({num:9, name:"Mahout", function:"데이터 마이닝"})
db.echo.insert({num:10, name:"Impala", function:"실시간 SQL 질의"})
db.echo.insert({num:11, name:"Tajo", function:"실시간 SQL 질의"})
db.echo.insert({num:12, name:"Oozie", function:"워크플로우 관리"})
db.echo.insert({num:13, name:"Zookeeper", function:"분산 코디네이터"})

db.echo.find()
