const openPopupBtn = document.getElementById("open-popup");
const closePopupBtn = document.getElementById("close-popup");
const popupContainer = document.getElementById("popup-container");//나타날 모달창
const canvas = document.getElementById("flowchartworkspace"); //캔버스 

//====================
//======= 시작 ========새로운 요소가 캔버스에 추가될 때마다 실행될 콜백 함수 정의

const nonModal_load = function(mutationList){
	for (const mutation of mutationList){
			//요소가 실제로 추가되었을 떄 실행될 로직 기술
			if(mutation.addedNodes[0] && mutation.addedNodes[0].classList.contains('flowchart-operator') && 
				+ /^로그인 기능.*/.test(mutation.addedNodes[0].textContent) ){//오퍼레이터 이름이 '로그인 기능'일때만 동작
				
					popupContainer.style.display = "block";
				}
			//오퍼레이터의 타이틀명을 확인하기 위한 테스트 코드
			//if(mutation.addedNodes[0] && mutation.addedNodes[0].classList.contains('flowchart-operator')){					
			//	console.log('mutation.addedNodes[0].textContent : ' + mutation.addedNodes[0].textContent);}//

		}
	}



//캔버스 내 새로운 요소를 감지하기 위한 MutationObserver 추가
const observer = new MutationObserver(nonModal_load);

//캔버스 요소 감시 시작
observer.observe(canvas, {childList : true, subtree : true});

//========= 끝 =========새로운 요소가 캔버스에 추가될 때마다 실행될 콜백 함수 정의 끝
//======================
openPopupBtn.addEventListener("click", function() {
  popupContainer.style.display = "block";
});

closePopupBtn.addEventListener("click", function() {
  popupContainer.style.display = "none";
});

popupContainer.addEventListener("click", function(event) {
  if (event.target === popupContainer) {
    //popupContainer.style.display = "none";
  }
});
