/** 몽구스 객체 가져 오기  */
const mongoose = require('mongoose')


const userSchema = mongoose.Schema({
    name:{
        type:String,
        maxlength:50,
    },
    email:{
        type:String,
        /** 공백 제거 */
        trim:true,
        unique:1
    },
    password:{
        type:String,
        maxlength:50
    },
    role:{
        type:Number,
        default:0
    },
    image:String,
    tokenExp:{
        type:Number
    }
})

const User = mongoose.model('User',userSchema)

/** 다른 파일에서도 사용가능하게 만듬  */
module.exports={User}