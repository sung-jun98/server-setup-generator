//const canvas = document.getElementById("flowchartworkspace"); //캔버스
const tooltip = document.getElementById("tooltip");

canvas.addEventListener('mousemove', (event) => {
    // 마우스 이동시 툴팁 위치 업데이트
    tooltip.style.left = event.pageX + 'px';
    tooltip.style.top = event.pageY + 'px';
}, true);

canvas.addEventListener('mouseover', () => {
    // 캔버스에 들어왔을 때 툴팁 표시
    tooltip.style.display = 'block';
    tooltip.style.visibility = 'visible';
    //console.log("(tooltip.js) 캔버스 안에 들어옴");
}, true);

canvas.addEventListener('mouseout', () => {
    // 캔버스에서 벗어났을 때 툴팁 숨김
    //tooltip.style.display = 'none';
}, true);