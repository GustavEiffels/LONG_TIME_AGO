


let time = 0;
var starFlag = true;

let hour  = 0;
let min   = 0;
let sec   = 0;
let mil   = 0;
let timer;
let startTime;
let timerInterval;


/**

 */



/**
 * 
  @name        : dataTable 
  @description : 시간 데이터를 넣기 위한 배열  
 */
let dataTable = [];

let isArrayExist = false;


let isInit = true;

/**
 * 
            @init        : 초기화 method  
            @description : local storage 에서 데이터를 들고오고 그 값을 토대로 rendering   
 */
(function() {
  

  let cacheData = localStorage.getItem('data');

  if(cacheData == null )
  {
    localStorage.setItem('data', JSON.stringify(dataTable));
  }
  else
  {
    dataTable = JSON.parse(cacheData);
    console.log(dataTable);
  }

})();

window.onload = function() {
    // 오늘 날짜를 표시할 element 를 변수에 지정 
    let todayTime = document.getElementById('todayTime');

    // 오늘 날짜를 yyyy.MM.dd 형태로 만들어 element 에 값을 변경 
    let dateTime = new Date();
    todayTime.textContent = dateTime.getFullYear()+'.'+(dateTime.getMonth()+1)+'.'+dateTime.getDate();
    todayTime.style.display = 'block'; // 해당 element 를 보이게 설정 


    if(dataTable.length != 0)
    {
      document.getElementById('dataTable').style = 'block';
    }

    console.log(dataTable);
    console.log('before rendering');
    rendering();
  
}

/**
 * 
            @name        : insertTimeData 
            @description : 정지 버튼을 누르고 html 에 오늘 날짜와 배열에 방금 생성한 시간데이터를 넣고 
                          해당 배열을 기반으로 datatable rendering  
            @parmaeter   : title -> 시간 데이터의 attribute  : 해당 시간에 주된 내용이 무엇을 했는지 저장 
                          time  -> 시간 데이터
 */
function insertTimeData(title, time)
{

  let idxNumber = 1;

  // 배열의 크기가 0 => 오늘 생성된 시간 데이터가 없을 경우 
  if(dataTable.length != 0)
  {
    // index 직접 설정  
    idxNumber = dataTable[dataTable.length-1].number+1
  }
  

  dataTable.push({"number":idxNumber, "title":title, "time":time} );

  toSpringBoot(idxNumber,title,time);

  localStorage.setItem('data', JSON.stringify(dataTable));

  
  rendering();
}


/**
  @name        : rendering
  @description : DataTable 에 tbody 요소를 생성  
 */
function rendering()
{


  // dataTable element 
  let table = document.getElementById('firstTable');

  // tbody element 
  let tbody = table.querySelector("tbody");

  // tbody element 의 이전 값들을 지운다. 
  tbody.innerHTML = "";


  // 배열안에 든 시간 데이터의 시간 값 
  let hourSub = 0;

  // 배열안에 든 시간 데이터의 분 값
  let minSub  = 0;

  // 배열안에 든 시간 데이터의 초 값
  let secSub  = 0;


  // 배열의 반복을 실행 => 행을 동적으로 생성하기 위해서 
  dataTable.forEach((item)=>{

    // 새로운 행을 생성 
    let row = document.createElement("tr");

    // 해당 행에 추가할 첫번째 열 데이터 생성 
    let cel1 = document.createElement("td");

    // 첫째 열에는 record 의 인덱스가 들어감 
    cel1.textContent = item.number;

    // 행에 해당 열을 추가 
    row.appendChild(cel1);


    // 두번째 행에는 레코드의 제목이 들어감
    let cel2 = document.createElement("td");
    cel2.textContent = item.title;
    row.appendChild(cel2);


    // 세번째 행에는 레코드의 시간 데이터가 들어감 
    let cel3 = document.createElement("td");
    let tempTime     = item.time;
    cel3.textContent = tempTime;

    // split function 으로 값을 나눈 다음, 정수로 변환
    let token = tempTime.split(":");
    hourSub += parseInt(token[0]);
    minSub  += parseInt(token[1]);
    secSub  += parseInt(token[2]);
    row.appendChild(cel3);

    // 네번째 행에는 함수를 가진 button 이 들어감 -> button 동적 생성 
    let cel4   = document.createElement("td");

    // button element 도 따로 생성 
    let button = document.createElement("button");

      // 생성된 button 의 attribute 설정 
      button.className="dataTableBtn";
      button.textContent = "setting"
      button.addEventListener("click",()=>{ console.log('test'); }); 
      cel4.appendChild(button); 
      row.appendChild(cel4)

    tbody.appendChild(row);
    
  })

  console.log('render');

  // data table tfoot 설정 
  setTfoot(hourSub,minSub,secSub);
}


function setTfoot(hour,min,sec)
{ 

  let foot = document.querySelector('tfoot');
  foot.innerHTML = "";

  let cel1 = document.createElement('td');
  let cel2 = document.createElement('td');
  let cel3 = document.createElement('td');

  // 시간의 총 합을 구하려고 한다. 
  let cel4 = document.createElement('td');
  min  += Math.floor(sec/60);
  hour += Math.floor(min/60);
  cel4.className ="totalTime"
  cel4.textContent = hour+" 시간 "+min%60+" 분 "+sec%60+" 초 ";

  foot.appendChild(cel1);
  foot.appendChild(cel2);
  foot.appendChild(cel3);
  foot.appendChild(cel4);


  console.log()

}




function init(){
  document.getElementById("time").innerHTML = "00:00:00";
  document.getElementById("timeMil").innerHTML = ":000";
  hour  = 0;
  min   = 0;
  sec   = 0;
  mil   = 0;
}

// parameter : start, pause, stop
// function  : if a timer start change number color
async function changeFontColor(command)
{
  $(".fa").css("color","grey");
  document.getElementById(command+'btn').style = 'color: white;';
}


  // start btn
  function startBtnOperate()
  {

    document.getElementById('time').style='color: white;'
    document.getElementById('timeMil').style='color: red;'

    if(starFlag)
    {
      
      changeFontColor('start');
        starFlag = false;
        if(time == 0)
        {
          init();
        }
        startTime = Date.now() - time;
        timerInterval = setInterval(updateTimer, 10);
      
    }
  }


  function updateTimer() {
    let currentTime = Date.now();
    time = currentTime - startTime
    presentCurrentTime();
  }



  // pause btn
  function pauseBtn()
    {
      // when time is not zero -> start button was operated 
      if(time != 0)
      {
        changeFontColor('pause');
        clearInterval(timerInterval);
        starFlag = true;
    
      }
    };






  // stop btn
  function stopBtn()
  {
    if(time != 0){
      changeFontColor('stop').then(()=>{
        clearInterval(timerInterval);
        confirm(document.getElementById("time").textContent+document.getElementById("timeMil").textContent);
  
        starFlag  = true;
        time      = 0;
        let title = prompt();

        document.getElementById('dataTable').style = 'block';




        insertTimeData(title,document.getElementById("time").textContent);
        init();
      })
    }
  }
    


  function presentCurrentTime()
  {
      if( mil == 1000 ) { mil  = 0; }
      
      mil  = time%1000;
      sec  = Math.floor(time/1000);
      min  = Math.floor(sec/60);
      hour = Math.floor(min/60);
      sec  = sec%60;
      min  = min%60;

    let th    = hour;
    let tm    = min;
    let ts    = sec;
    let tmil  = mil; 
    
    if       (   th  < 10  )  { th   = "0"   + hour;  }
    if       (   tm  < 10  )  { tm   = "0"   + min;   }
    if       (   ts  < 10  )  { ts   = "0"   + sec;   }
    if       (  tmil < 10  )  { tmil = "00"  + mil;   }
    else { if( tmil  < 100 )  { tmil = "0"   + mil; } }

    document.getElementById("time")   .innerHTML    = th + ":" + tm + ":" + ts;
    document.getElementById("timeMil").innerHTML = ":"+tmil;
  }


  function toSpringBoot(number, title, time)
  {
    console.log('number'+number);
    console.log('title'+title);
    console.log('time'+time);


    fetch("//localhost:8099/stopWatch/saveRecord", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({title:title, number: number, time:time})
    })
      .then(response => response.json())
      .then(result => {
        // 요청이 성공적으로 처리되었을 때 수행할 동작
        console.log(result);
      })
      .catch(error => {
        // 요청 처리 중 오류가 발생했을 때 수행할 동작
        console.error(error);
      });
  }

