<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>2018305012 김성준</title>
	<meta charset="utf-8">

	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- jQuery & jQuery UI are required -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

	<!-- Flowchart CSS and JS -->
	<link rel="stylesheet" href="jquery.flowchart.css">
	<script src="jquery.flowchart.js"></script>

	<!-- NonModal CSS -->
	<link rel="stylesheet" href="nonModal.css">
	
	<!-- bootstrap CSS -->
	 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	
	<style>
		.flowchart-example-container {
			width: 1400px;
			height: 400px;
			background: white;
			border: 1px solid #BBB;
			margin-bottom: 10px;
		}
	</style>
</head>

<body>
	<h1>2018305012 김성준</h1>
	<!-- 
	<h4>Sample created with basis on some from Sebastien Drouyer, original author's <a href="https://sebastien.drouyer.com/jquery.flowchart-demo/">website</a>.</h4>
	 -->
	<h4>Flowchart</h4>
	<div id="chart_container">
		<div class="flowchart-example-container" id="flowchartworkspace"></div>
	</div>
	<button class="delete_selected_button">Delete selected operator / link</button>
	<div class="draggable_operators">
		<div class="draggable_operators_label">
			Operators (drag and drop them in the flowchart):
		</div>
		<div class="draggable_operators_divs"><!--  
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="0">1 input</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="0" data-nb-outputs="1" hidden>1 output</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="1">1 input &amp; 1 output</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="2">1 in &amp; 2 out</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="1">2 in &amp; 1 out</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">2 in &amp; 2 out</div>
			-->
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">로그인 기능</div>
			<!-- 이 밑은 나중에 삭제 혹은 수정할 것 -->
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">회원 가입</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">회원 정보 수정</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">댓글 작성</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">게시글 작성</div>
			<div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="2" data-nb-outputs="2">파일 업로드</div>
		</div>
	</div>
	
<!-- -----------------------시작 -->	
<!-- DB 관련 정보 기입 테스트 -->
	<br><br>
	<h4>DB 생성기</h4>
	<input type="button" onClick="addRow()" value="행 추가" class="btn btn-primary"></input>
	
	<form id=Building_DB_form>
	<hr>
		<table id="tableForDB" class="table table-striped table-sm">
	      <thead>
	        <tr>
	        	<th>Table Name</th>  
	            <th>Column Name</th>
	            <th>Data Type</th>
	        </tr>
	      </thead>
	      <tbody>
	        <tr>
	        	<td rowspan="1" id="rowspan_Of_TableName"><input type="text" id="tableName" name="tableName"  class="form-control"  style="border:1px solid white; background-color:transparent;"></td>
	            <td><input type="text" id="columnName1" name="columnName1" class="form-control"  style="border:1px solid white; background-color:transparent;"></td>
	            <td><input type="text" id="dataType1" name="dataType1" class="form-control"  style="border:1px solid white; background-color:transparent;"><br></td>
	            <td><label> <input type="checkbox" id="primaryKey1" name="primaryKey1">PK</label></td>
	            <td><label> <input type="checkbox" id="notNull1" name="notNull1">NN</label></td>
	        </tr>
	      </tbody>
	      <input type="submit" value="DB 테이블 생성" class="btn btn-primary">
    </table>
		
	<hr>
	</form>
<!-- .......................끝 -->
	<!--  <button class="create_operator">Create operator</button>-->
	
	<div id="operator_properties" style="display: block;">
		<label for="operator_title">Operator's title: </label><input id="operator_title" type="text">
	</div>
	<div id="link_properties" style="display: block;">
		<label for="link_color">Link's color: </label><input id="link_color" type="color">
	</div>
	<button class="get_data" id="get_data">Get data</button>
	<!--  <button class="set_data" id="set_data">Set data</button>-->
	<button id="save_local">Save to local storage</button>
	<!--  <button id="load_local">Load from local storage</button>-->
	<div>
		<textarea id="flowchart_data"></textarea>
	</div>
	<!-- 모달창 테스트코드 -->
	<button id="open-popup">팝업 열기</button>
		<div id="popup-container">
		  	<div id="popup">
		    <h2>로그인 기능 관련 다이어그램</h2>
		    <p>사용할 다이어그램을 끌어다 캔버스 안에 배치하세요</p>
		    <div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="1">아이디가 없을 경우</div>
		    <div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="1">비밀번호가 틀릴 경우</div>
		    <div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="1">DB오류가 발생했을 경우</div>
		    <div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="1" data-nb-outputs="1">리턴 페이지</div>
		    <div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="3" data-nb-outputs="1">참값</div>
		    <div class="draggable_operator btn btn-outline-secondary" data-nb-inputs="3" data-nb-outputs="1">입력값</div>
		    
		    
		    <button id="close-popup">팝업 닫기</button>
		    
	  	</div>
	</div>
	
	<script src="nonModal.js"></script>
	<!--  여기까지 모달창 테스트코드 -->
	<!-- DB관련 정보 전송용 스크립트 -->
	<script src="buildingDB.js"></script>
	<!-- Bootstrap js script -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js" integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
	
	<script type="text/javascript">
//------------------------------------

		/* global $ */
		$(document).ready(function() {
			var $flowchart = $('#flowchartworkspace');
			var $container = $flowchart.parent();


			// Apply the plugin on a standard, empty div...
			$flowchart.flowchart({
				data: defaultFlowchartData,
				defaultSelectedLinkColor: '#000055',
				grid: 10,
				multipleLinksOnInput: true,
				multipleLinksOnOutput: true
			});


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



			//-----------------------------------------
			//--- operator and link properties
			//--- start
			var $operatorProperties = $('#operator_properties');
			$operatorProperties.hide();
			var $linkProperties = $('#link_properties');
			$linkProperties.hide();
			var $operatorTitle = $('#operator_title');
			var $linkColor = $('#link_color');

			$flowchart.flowchart({
				onOperatorSelect: function(operatorId) {
					$operatorProperties.show();
					$operatorTitle.val($flowchart.flowchart('getOperatorTitle', operatorId));
					return true;
				},
				onOperatorUnselect: function() {
					$operatorProperties.hide();
					return true;
				},
				onLinkSelect: function(linkId) {
					$linkProperties.show();
					$linkColor.val($flowchart.flowchart('getLinkMainColor', linkId));
					return true;
				},
				onLinkUnselect: function() {
					$linkProperties.hide();
					return true;
				}
			});

			$operatorTitle.keyup(function() {
				var selectedOperatorId = $flowchart.flowchart('getSelectedOperatorId');
				if (selectedOperatorId != null) {
					$flowchart.flowchart('setOperatorTitle', selectedOperatorId, $operatorTitle.val());
				}
			});

			$linkColor.change(function() {
				var selectedLinkId = $flowchart.flowchart('getSelectedLinkId');
				if (selectedLinkId != null) {
					$flowchart.flowchart('setLinkMainColor', selectedLinkId, $linkColor.val());
				}
			});
			//--- end
			//--- operator and link properties
			//-----------------------------------------

			//-----------------------------------------
			//--- delete operator / link button
			//--- start
			$flowchart.parent().siblings('.delete_selected_button').click(function() {
				$flowchart.flowchart('deleteSelected');
			});
			//--- end
			//--- delete operator / link button
			//-----------------------------------------



			//-----------------------------------------
			//--- create operator button
			//--- start
			var operatorI = 0;
			$flowchart.parent().siblings('.create_operator').click(function() {
				var operatorId = 'created_operator_' + operatorI;
				var operatorData = {
					top: ($flowchart.height() / 2) - 30,
					left: ($flowchart.width() / 2) - 100 + (operatorI * 10),
					properties: {
						title: 'Operator ' + (operatorI + 3),
						inputs: {
							input_1: {
								label: 'Input 1',
							}
						},
						outputs: {
							output_1: {
								label: 'Output 1',
							}
						}
					}
				};

				operatorI++;

				$flowchart.flowchart('createOperator', operatorId, operatorData);

			});
			//--- end
			//--- create operator button
			//-----------------------------------------




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


			//-----------------------------------------
			//--- save and load
			//--- start
			function Flow2Text() {
				var data = $flowchart.flowchart('getData');
				$('#flowchart_data').val(JSON.stringify(data, null, 2));
				//console.log(data.operators);//
				
//여기서부터 테스트코드(AJAX로 /staticWebReturn.java로 전송한다.)
				 $.ajax({
				    url: '/server_setup_generator/staticWebReturn',
				    type: 'POST',
				    data: JSON.stringify({ flowchartData: data }),//
				    contentType: 'application/json',//
				    dataType: 'json',//
				    success: function(response) {
				      console.log('response : ' + response.result);
				      //console.log(JSON.stringify(data, null, 2));
				    },
				    error: function(jqXHR, textStatus, errorThrown) {
				    	console.log("실패");
				      console.error('Error sending data: ' + textStatus, errorThrown);
				    }
				  });
//테스트 코드 끝
				
				
				
			} 																	//GetData 버튼을 누르면 호출되는 함수
																				//여기 밑에 AJAX으로 JSON 구조를 반환하는 로직을 짜면 될 듯
			$('#get_data').click(Flow2Text);

			function Text2Flow() {
				var data = JSON.parse($('#flowchart_data').val());
				$flowchart.flowchart('setData', data);
			}
			$('#set_data').click(Text2Flow);

			/*global localStorage*/
			function SaveToLocalStorage() {
				if (typeof localStorage !== 'object') {
					alert('local storage not available');
					return;
				}
				Flow2Text();
				localStorage.setItem("stgLocalFlowChart", $('#flowchart_data').val());
			}
			$('#save_local').click(SaveToLocalStorage);

			function LoadFromLocalStorage() {
				if (typeof localStorage !== 'object') {
					alert('local storage not available');
					return;
				}
				var s = localStorage.getItem("stgLocalFlowChart");
				if (s != null) {
					$('#flowchart_data').val(s);
					Text2Flow();
				}
				else {
					alert('local storage empty');
				}
			}
			$('#load_local').click(LoadFromLocalStorage);
			//--- end
			//--- save and load
			//-----------------------------------------
			
		});
//-------시작-------------------------------------
//여기서부터 테스트코드(Input/Output 태그를 더블클릭했을 때 변경할 수 있도록 하는 코드)

		// 더블 클릭 이벤트 리스너 등록
		document.addEventListener('dblclick', function(event) {
		  // 이벤트가 draggable_operator 클래스를 포함하고 있는지 확인
		  if (event.target.classList.contains('flowchart-operator-connector-label')) {
				original_text = event.target.textContent; //보낼 데이터의 키값은 원래 오리지널 텍스트다.
		    // input 태그 생성
		    var input = document.createElement('input');
		    input.type = 'text';
		    input.value = event.target.textContent;
		    input.addEventListener('blur', function() {
		      // input 태그에서 포커스가 벗어났을 때 새로운 텍스트로 대체
		      event.target.textContent = input.value;
		    });
		    
		    input.addEventListener('keydown', function(eventKey) {
			      // input 태그에서 엔터를 눌렀을 때 새로운 텍스트로 대체
			      
			      if(eventKey.key == 'Enter'){
			      	eventKey.preventDefault();
			      	event.target.textContent = input.value;
			      	//overWriteData.java로 입력한 값을 전송한다.
			      	var data = {};
			      	data[original_text] = input.value;
			      	 $.ajax({
						    url: '/server_setup_generator/overWriteData',
						    type: 'POST',
						    data: JSON.stringify(data),
						    contentType: 'application/json',
						    dataType: 'json',
						    success: function(response) {
						      console.log('response : ' + response.result);
						      //console.log(JSON.stringify(data, null, 2));
						    },
						    error: function(jqXHR, textStatus, errorThrown) {
						    	console.log("실패");
						      console.error('Error sending data: ' + textStatus, errorThrown);
						    }
						  });
			    }
		    });
		    // 기존 텍스트 대신 input 태그 삽입
		    event.target.textContent = '';
		    event.target.appendChild(input);
		    input.focus();
		  }
		});
///////////////////////////////////////
	//오퍼레이터 내에 필수로 입력해야 하는 input 입력값 위에서 엔터를 입력했을시
	document.addEventListener('keydown', function(event){
		if(event.key ==='Enter' && event.target.classList.contains('elasticValueLabel')){
			event.preventDefault;
			
			var id_for_key = event.target.getAttribute('id'); //data객체의 key 값 정의
			console.log('id : ' + id_for_key);//
			
			//보낼 데이터 객체 data{} 정의
			var data = {};
			data[id_for_key] = event.target.value;
			console.log(data);//
			
			$.ajax({
				url: '/server_setup_generator/overWriteData',
				type: 'POST',
				data: JSON.stringify(data),
				contentType: 'application/json',
				dataType: 'json',
				 success: function(response) {
				     console.log('response : ' + response.result);
				      //console.log(JSON.stringify(data, null, 2));
				    },
				    error: function(jqXHR, textStatus, errorThrown) {
				    	console.log("실패");
				      console.error('Error sending data: ' + textStatus, errorThrown);
				    }
				 });
				
		}
	});
///////////////////////
	//HTML 파일을 입력했을 시에 자동으로 ProcessHTML 서블릿으로 연결되게 한다.
	/* $(document).ready(function(){
	    // 파일 선택시 자동으로 폼 제출
	    $('#loginStartPage').change(function(){
	        $(this).closest('form').submit();
	    });
	}); */
	
	$(document).ready(function(){
	    // 캔버스 내의 특정 컨테이너 요소를 대상으로 이벤트 핸들러 추가
	    $('#flowchartworkspace').on('change', '#loginStartPage', function() {
	        $(this).closest('form').submit();
	    });
	});
	
	$(document).ready(function(){
		//$(".fileForm").submit(function(event){
		$(document).on('submit', '.fileForm', function(event){
			event.preventDefault(); //기본 폼 제출 동작 방지
			
			var opTitle = $(this).closest('form').parent().parent().parent().parent().siblings('.flowchart-operator-title').text();
			var formData = new FormData(this);//보내려고 하는 HTML파일이 담긴다.
			
			console.log(opTitle);
			formData.append("opTitle", opTitle); //테스트
			
			   $.ajax({
				      url: '/server_setup_generator/processHTML',
				      type: 'POST',
				      data: formData,
				      processData: false,
				      contentType: false,
				      success: function(response) {
				        // 서버 응답에 대한 처리 로직 작성
				        console.log("HTML 파일 전송 성공");
				        console.log(response);
				      },
				      error: function(xhr, status, error) {
				        // 에러 처리 로직 작성
				        console.log("HTML 파일 전송 실패");
				        console.log(error, status, xhr);
				      }
				  });
		});
	});

////////////////////////////

	/* var essential_Inputs = document.querySelectorAll('.elasticValueLabel');
	
	essential_Inputs.forEach(function(element){
		
		
		element.addEventListener('keydown', function(event){
			if(keydown === 'Enter'){
				event.preventDefault();
				
				 
			}
		});
	
	}); */
//----------끝
//-------------------------------------------

		var defaultFlowchartData = { //처음 화면을 로드했을 떄 보여질 디폴트 구조 정의
			operators: {
				operator1: {
					top: 20,
					left: 20,
					properties: {
						title: 'Operator 1',
						inputs: {},
						outputs: {
							output_1: {
								label: 'Output 1',
							}
						}
					}
				},
				operator2: {
					top: 80,
					left: 300,
					properties: {
						title: 'Operator 2',
						inputs: {
							input_1: {
								label: 'Input 1',
							},
							input_2: {
								label: 'Input 2',
							},
						},
						outputs: {}
					}
				},
			},
			links: {
				link_1: {
					fromOperator: 'operator1',
					fromConnector: 'output_1',
					toOperator: 'operator2',
					toConnector: 'input_2',
				},
			}
		};
		if (false) console.log('remove lint unused warning', defaultFlowchartData);
		
	</script>
</body>

</html>