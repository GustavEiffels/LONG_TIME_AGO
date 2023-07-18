 let myDiv = document.getElementById("enterBox");
  let count = 0;
  let isFocused = false;

  myDiv.addEventListener("mouseover", function() {
    isFocused = true;
  });

  myDiv.addEventListener("mouseout", function() {
    isFocused = false;
  });

  document.addEventListener("keydown", function(event) {
    if (isFocused && event.key === "Enter") {
      count++;
      myDiv.textContent = count;
    }
  });


function resetBtn()
{
    count = 0;
    myDiv.textContent = 'Zero';
}

function saveBtn()
{
    alert('count : '+count);
    myDiv.textContent = 'Zero';
}












