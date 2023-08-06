const openPopupBtn = document.getElementById("open-popup");
const closePopupBtn = document.getElementById("close-popup");
const popupContainer = document.getElementById("popup-container");//나타날 모달창
const canvas = document.getElementById("flowchartworkspace"); //캔버스 

const SelectOption_login = document.getElementById("selectOption_login"); //로그인 오퍼레이터 선택지 
const const_for_writeOp = document.getElementById("writeAction"); //게시물 작성 오퍼레이션 선택지

//====================
//======= 시작 ========새로운 요소가 캔버스에 추가될 때마다 실행될 콜백 함수 정의

const nonModal_load = function(mutationList){
	for (const mutation of mutationList){
			//요소가 실제로 추가되었을 떄 실행될 로직 기술
			if(mutation.addedNodes[0] && mutation.addedNodes[0].classList.contains('flowchart-operator') && 
				+ /^로그인 기능.*/.test(mutation.addedNodes[0].textContent)){//오퍼레이터 이름이 '로그인 기능'일때만 동작
				//jquery에서 지원하는 모달 다이어그램 실행
				$( function(){
				    $("#dialog" ).dialog({
				    	width : 600
				    });
				  });
					//popupContainer.style.display = "block";
					
					
				}
				else if(mutation.addedNodes[0] && mutation.addedNodes[0].classList.contains('flowchart-operator') && 
				+ /^게시물 작성.*/.test(mutation.addedNodes[0].textContent)){
					//jquery에서 지원하는 모달 다이어그램 실행
					$( function(){
					    $("#dialog_writeAction" ).dialog({
					    	width : 600
					    });
					 });
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
openPopupBtn.addEventListener("click", function() { //팝업 실행 버튼을 눌렀을 시의 로직
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
//============================================
//============더블클릭관련 로직====================
//'로그인 기능' 선택지를 클라이언트가 더블클릭 했을 때 관련 논모달 창 나옴.
SelectOption_login.addEventListener("dblclick", function(){
	$( function(){
		$( "#dialog" ).dialog({
			width : 600
		});
	});
})

//'게시물 작성' 선택지를 클라이언트가 더블클릭 했을 때 관련 논모달 창 나옴.
const_for_writeOp.addEventListener("dblclick", function(){
	$( function(){
		$( "#dialog_writeAction" ).dialog({
			width : 600
		});
	});
})
