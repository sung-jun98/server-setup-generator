//demo.jsp에서 DB관련 정보를 입력하고 submit을 눌렀을 때, 페이지 전체가 리프레시 되지 않고, 일부분만 바뀔 수 있게 AJAX를 통해서 
//서버와 비동기 통신을 하는 코드 

var count = 1; //행 추가를 할 떄 쓸 변수

$(function() {
  $('#Building_DB_form').submit(function(e) {
	    e.preventDefault(); // 기본적인 form submit 동작 방지
		const formData = $('#Building_DB_form').serializeArray();
	//console.dir("$('#Building_DB_form').serializeArray() : " + formData );//테스트용
		const data = {};
		
		formData.forEach(function(element) {
		  data[element.name] = element.value;
		});
	//console.dir	("data  : " + data);//테스트용
	console.log("JSON.stringify(data)  : " + JSON.stringify(data));//테스트용
	
		making_operatios_of_DB(data); //작성한 DB테이블을 바탕으로 선택할수 있는 오퍼레이션을 만든다.
	
		$.ajax({
		  url: '/server_setup_generator/dbBuilder',
		  method: 'POST',
		  data: JSON.stringify(data),
		  contentType: 'application/json',
		  success: function(response) {
		    console.log(response);
		  },
		  error: function(jqXHR, textStatus, errorThrown) {
		    console.error(jqXHR);
		  }
		});
		
		//이 밑에 Submit 버튼을 눌렀을 시, columnName(1, 2, 3...)들의 내용들을 모두 draggable_operators_divs 밑에
		//draggable_operator로 만들어 주는 코드를 작성해야 한다.
	});
	
	
	//====================================================================
	//=========게시물 업로드 관련 논모달창에서 DB테이블 생성 버튼을 눌렀을 때=============
	$('#Building_DB_form2').submit(function(e) {
	    e.preventDefault(); // 기본적인 form submit 동작 방지
		const formData = $('#Building_DB_form2').serializeArray();
	//console.dir("$('#Building_DB_form').serializeArray() : " + formData );//테스트용
		const data = {};
		
		formData.forEach(function(element) {
		  data[element.name] = element.value;
		});
	//console.dir	("data  : " + data);//테스트용
	console.log("JSON.stringify(data)  : " + JSON.stringify(data));//테스트용
	
		making_operatios_of_DB(data); //작성한 DB테이블을 바탕으로 선택할수 있는 오퍼레이션을 만든다.
	
		$.ajax({
		  url: '/server_setup_generator/dbBuilder',
		  method: 'POST',
		  data: JSON.stringify(data),
		  contentType: 'application/json',
		  success: function(response) {
		    console.log(response);
		  },
		  error: function(jqXHR, textStatus, errorThrown) {
		    console.error(jqXHR);
		  }
		});
		
		//이 밑에 Submit 버튼을 눌렀을 시, columnName(1, 2, 3...)들의 내용들을 모두 draggable_operators_divs 밑에
		//draggable_operator로 만들어 주는 코드를 작성해야 한다.
	});
	
});

function addRow(){ //DB 생성 표의 행 추가 버튼 기능 구현
		var tableForDB = document.getElementById("tableForDB");
		var newRow = tableForDB.insertRow();
		var cell1 = newRow.insertCell();
		var cell2 = newRow.insertCell();
		var cell3 = newRow.insertCell();
		var cell4 = newRow.insertCell();
		
		count += 1; //새로 추가할 열의 HTML 속성명의 index이므로 하나씩 늘려준다.
		
		document.getElementById("rowspan_Of_TableName").setAttribute("rowspan", count); //TableName이 차지하는 면적 늘려줌
		cell1.innerHTML = '<input type="text" id="columnName' + count + '" name="columnName' + count + '"  class="form-control"  style="border:1px solid white; background-color:transparent;">';
		cell2.innerHTML = '<input type="text" id="dataType' + count + '" name="dataType' + count + '"  class="form-control"  style="border:1px solid white; background-color:transparent;">';
		cell3.innerHTML = '<label> <input type="checkbox" id="primaryKey' + count + '" name="primaryKey' + count + '">PK</label>';
		cell4.innerHTML = '<label> <input type="checkbox" id="notNull' + count + '" name="notNull' + count + '">NN</label>';
		
		
	}
	
function addRow2(){ //DB 생성 표의 행 추가 버튼 기능 구현
		var tableForDB = document.getElementById("tableForDB2");
		var newRow = tableForDB.insertRow();
		var cell1 = newRow.insertCell();
		var cell2 = newRow.insertCell();
		var cell3 = newRow.insertCell();
		var cell4 = newRow.insertCell();
		
		count += 1; //새로 추가할 열의 HTML 속성명의 index이므로 하나씩 늘려준다.
		
		document.getElementById("rowspan_Of_TableName2").setAttribute("rowspan", count); //TableName이 차지하는 면적 늘려줌
		cell1.innerHTML = '<input type="text" id="columnName' + count + '" name="columnName' + count + '"  class="form-control"  style="border:1px solid white; background-color:transparent;">';
		cell2.innerHTML = '<input type="text" id="dataType' + count + '" name="dataType' + count + '"  class="form-control"  style="border:1px solid white; background-color:transparent;">';
		cell3.innerHTML = '<label> <input type="checkbox" id="primaryKey' + count + '" name="primaryKey' + count + '">PK</label>';
		cell4.innerHTML = '<label> <input type="checkbox" id="notNull' + count + '" name="notNull' + count + '">NN</label>';
		
		
	}
	
function making_operatios_of_DB(data){ //디비의 속성을 다이어그램으로 다룰 수 있도록 캔버스에 오퍼레이터를 추가한다.
	
	var draggable_operators_divs = document.getElementsByClassName("draggable_operators_divs");
	
	//생성한 columnName 데이터값 가져오기 + 그 데이터값으로 오퍼레이터 만들기
	for (var i = 1; i<=count; i++){  
		var columnName = "columnName" + i.toString(); 
		console.log("columnName : " + columnName );
		console.log("columnName" + (i) + " : " + data[columnName]);
		
		const newElement = document.createElement("div");
		newElement.className = "draggable_operator ui-draggable ui-draggable-handle btn btn-outline-secondary";
		newElement.setAttribute("data-nb-inputs", "1");
		newElement.setAttribute("data-nb-outputs", "1");
		newElement.textContent = data[columnName];
		document.querySelector(".draggable_operators_divs").appendChild(newElement);
		init_operators_of_DB();//다시 초기화 진행
		//<div class="draggable_operator" data-nb-inputs="0" data-nb-outputs="1">1 output</div>
	}
	
	//input 태그들의 내용을 빈칸으로 지우기
}
//============================================================
//------------------초기화 함수 start----건드리지 말것 태그를 페이지에 추가한 다음, 초기화를 다시 한번 해 주어야 드래그앤 드롭을 해줄 수 있다.
//
//

// Apply the plugin on a standard, empty div...
	var $flowchart = $('#flowchartworkspace');
	var $container = $flowchart.parent();
	/*$flowchart.flowchart({
				data: $('#flowchart_data').val(),
				defaultSelectedLinkColor: '#000055',
				grid: 10,
				multipleLinksOnInput: true,
				multipleLinksOnOutput: true
			});*/
			
			
function getOperatorData($element) {
				var nbInputs = parseInt($element.data('nb-inputs'), 10);
				var nbOutputs = parseInt($element.data('nb-outputs'), 10);
				var data = {
					properties: {
						title: $element.text(),
						inputs: {},
						outputs: {}
					}
				};

				var i = 0;
				for (i = 0; i < nbInputs; i++) {
					data.properties.inputs['input_' + i] = {
						label: 'Input ' + (i + 1)
					};
				}
				for (i = 0; i < nbOutputs; i++) {
					data.properties.outputs['output_' + i] = {
						label: 'Output ' + (i + 1)
					};
				}

				return data;
}

function init_operators_of_DB(){ 
	//-----------------------------------------
			//--- draggable operators
			//--- start
			//var operatorId = 0;
			var $draggableOperators = $('.draggable_operator');
			$draggableOperators.draggable({
				cursor: "move",
				opacity: 0.7,

				// helper: 'clone',
				appendTo: 'body',
				zIndex: 1000,

				helper: function(e) {
					var $this = $(this);
					var data = getOperatorData($this);
					return $flowchart.flowchart('getOperatorElement', data);
				},
				stop: function(e, ui) {
					var $this = $(this);
					var elOffset = ui.offset;
					var containerOffset = $container.offset();
					if (elOffset.left > containerOffset.left &&
						elOffset.top > containerOffset.top &&
						elOffset.left < containerOffset.left + $container.width() &&
						elOffset.top < containerOffset.top + $container.height()) {

						var flowchartOffset = $flowchart.offset();

						var relativeLeft = elOffset.left - flowchartOffset.left;
						var relativeTop = elOffset.top - flowchartOffset.top;

						var positionRatio = $flowchart.flowchart('getPositionRatio');
						relativeLeft /= positionRatio;
						relativeTop /= positionRatio;

						var data = getOperatorData($this);
						data.left = relativeLeft;
						data.top = relativeTop;

						$flowchart.flowchart('addOperator', data);
					}
				}
			});
			//--- end
			//--- draggable operators
			//-----------------------------------------
			
			
}
//----------여기까지 초기화 함수----------이 안은 손대지 말것
//===================================














