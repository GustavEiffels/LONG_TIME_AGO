/*** express Module 을 가져온다 */
const express = require('express')

/*** 다운로드 받은 Express app 을 이용해서 함수를 이용 -- 함수 등록  */
const app = express()

/** Port 설정  */
const port = 2000

/**  User 가져오기  */
const {User} = require("./models/User");

/** body parser */
const bodyParser = require('body-parser')

/** config 에서 정보 가져오기  */
const config = require('./config/key')


/** body parser Option 주기 */

/** application/x-www-form-urlencoded 로 오는 데이터를 분석할 수 있도록 함 */
app.use(bodyParser.urlencoded({extended: true}));

/** application/json 형태로 오는 데이터 분석할 수 있도록 설정 */
app.use(bodyParser.json());





/** Mongoose 설정  */
const mongoose = require('mongoose')
mongoose.connect(config.mongoURI,{}).then(()=>console.log("Mongo DB Connected"))
// .catch(()=>console.log("Error Emerge"))



/** root directory 에 Hello World 가 출력 되도록 설정  */
app.get('/',(req, res) => res.send('Hello World!'))



/** 회원가입을 위한 Route  */
app.post('/register',(req,res) => {

    /** Client 에서 보내주는 정보들을 client 에서 가져오면  */
    /** 그것들을 데이터 베이스에 넣어준다. ---> User.js 가져와야한다. */

    /** req.body 안에 데이터들이 들어있다. */
    const user = new User(req.body);
    
    /** mongodb 에 데이터 저장  */
    user.save((err,doc)=>{
        /** error 발생 시 error message 를 json 형식으로 return  */
        if(err) return res.json({success:false, err})
        return res.status(200).json({
            success: true
        })
    })

})


/** 설정한 port로 실행 */
app.listen(port, ()=> console.log('Example app listening on port ${port} !'))



