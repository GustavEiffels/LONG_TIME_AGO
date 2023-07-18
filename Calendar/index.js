// 날짜 데이터이기 때문에 Date 객체를 활용
let date = new Date();

// 년도와 월은 자주 사용해야하기 때문에 미리 값을 갖는게 좋기 때문에 변수 두개를 생성
const renderCalendar =()=>{
const viewYear = date.getFullYear();
const viewMonth = date.getMonth();

// querySelector를 이용해서  year-month 태그에 접근 
document.querySelector('.year-month').textContent=`${viewYear}년 ${viewMonth +1}월`;

const prevLast = new Date(viewYear, viewMonth, 0);
const thisLast = new Date(viewYear, viewMonth + 1 ,0);


// PLDate 지난달 날짜
// PLDay 지난달 요일 
const PLDate = prevLast.getDate();
const PLDay = prevLast.getDay();


//  TLDate 이번달 날짜 
//  TLDay  이번달 요일
const TLDate = thisLast.getDate();
const TLDay = thisLast.getDay();

//전체 달력에 필요한 날짜를 만들기 
const prevDates=[];
const thisDates=[...Array(TLDate+1).keys()].slice(1);
const nextDates = [];

if(PLDay !==6){
    for(let i=0;i<PLDay+1;i++){
        prevDates.unshift(PLDate-i);
    }
}
for(let i=1;i<7 - TLDay;i++){
    nextDates.push(i);
}

const dates=prevDates.concat(thisDates,nextDates);

dates.forEach((date,i)=>{
    dates[i]=`<div class="date">${date}</div>`;
})
document.querySelector('.dates').innerHTML=dates.join('');


const fistDateIndex =dates.indexOf(1);
const lastDateIndex =dates.lastIndexOf(TLDate);
dates.forEach((date,i)=>{
    const condition = i >= fistDateIndex && i <lastDateIndex+1
                    ?'this'
                    :'other';
                    dates[i]=`<div class="date"><span class="${condition}">${date}`
})
const today = new Date();
 if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
   for (let date of document.querySelectorAll('.this')) {
     if (+date.innerText === today.getDate()) {
       date.classList.add('today');
       break;
     }
   }
 }


}
const prevMonth = () => {
    date.setMonth(date.getMonth() - 1);
    renderCalendar();
  }
  
  const nextMonth = () => {
    date.setMonth(date.getMonth() + 1);
    renderCalendar();
  }
  
  const goToday = () => {
    date = new Date();
    renderCalendar();
  }
  

renderCalendar();
 // 오늘 날짜 그리기
 


