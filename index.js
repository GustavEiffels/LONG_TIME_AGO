/*** express Module 을 가져온다 */
const express = require('express')

/*** 다운로드 받은 Express app 을 이용해서 함수를 이용 -- 함수 등록  */
const app = express()

/** Port 설정  */
const port = 2000


/** Mongoose 설정  */
const mongoose = require('mongoose')
mongoose.connect('mongodb+srv://samsung:1234@test-cluster.7eyzo.mongodb.net/myFirstDatabase?retryWrites=true&w=majority',
{}).then(()=>console.log("Mongo DB Connected"))
// .catch(()=>console.log("Error Emerge"))



/** root directory 에 Hello World 가 출력 되도록 설정  */
app.get('/',(req, res) => res.send('Hello World!'))

/** 설정한 port로 실행 */
app.listen(port, ()=> console.log('Example app listening on port ${port} !'))