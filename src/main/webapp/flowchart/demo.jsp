<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>Home</title>
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
	<!-- <script src="nonModal.js"></script> -->
	
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
	<div class="draggable_operators">
		<div class="draggable_operators_label">
			Operators (drag and drop them in the flowchart):
		</div>
		<div class="draggable_operators_divs">
			<div class="draggable_operator" data-nb-inputs="1" data-nb-outputs="0">1 input</div>
			<div class="draggable_operator" data-nb-inputs="0" data-nb-outputs="1">1 output</div>
			<div class="draggable_operator" data-nb-inputs="1" data-nb-outputs="1">1 input &amp; 1 output</div>
			<div class="draggable_operator" data-nb-inputs="1" data-nb-outputs="2">1 in &amp; 2 out</div>
			<div class="draggable_operator" data-nb-inputs="2" data-nb-outputs="1">2 in &amp; 1 out</div>
			<div class="draggable_operator" data-nb-inputs="2" data-nb-outputs="2">2 in &amp; 2 out</div>
			<div class="draggable_operator" data-nb-inputs="4" data-nb-outputs="2">로그인 기능</div>
		</div>
	</div>
	<button class="create_operator">Create operator</button>
	<button class="delete_selected_button">Delete selected operator / link</button>
	<div id="operator_properties" style="display: block;">
		<label for="operator_title">Operator's title: </label><input id="operator_title" type="text">
	</div>
	<div id="link_properties" style="display: block;">
		<label for="link_color">Link's color: </label><input id="link_color" type="color">
	</div>
	<button class="get_data" id="get_data">Get data</button>
	<button class="set_data" id="set_data">Set data</button>
	<button id="save_local">Save to local storage</button>
	<button id="load_local">Load from local storage</button>
	<div>
		<textarea id="flowchart_data"></textarea>
	</div>
	<!-- 모달창 테스트코드 -->
	<button id="open-popup">팝업 열기</button>
		<div id="popup-container">
		  	<div id="popup">
		    <h2>로그인 기능 관련 다이어그램</h2>
		    <p>사용할 다이어그램을 끌어다 캔버스 안에 배치하세요</p>
		    <div class="draggable_operator" data-nb-inputs="0" data-nb-outputs="1">변수이름</div>
		    
		    <button id="close-popup">팝업 닫기</button>
		    
	  	</div>
	</div>
	
	<script src="nonModal.js"></script>
	<!--  여기까지 모달창 테스트코드 -->
	<script type="text/javascript">
		
//여기서부터 테스트코드(Input/Output 태그를 더블클릭했을 때 변경할 수 있도록 하는 코드)

		// 더블 클릭 이벤트 리스너 등록
		document.addEventListener('dblclick', function(event) {
		  // 이벤트가 draggable_operator 클래스를 포함하고 있는지 확인
		  if (event.target.classList.contains('flowchart-operator-connector-label')) {
		    // input 태그 생성
		    var input = document.createElement('input');
		    input.type = 'text';
		    input.value = event.target.textContent;
		    input.addEventListener('blur', function() {
		      // input 태그에서 포커스가 벗어났을 때 새로운 텍스트로 대체
		      event.target.textContent = input.value;
		    });
		    // 기존 텍스트 대신 input 태그 삽입
		    event.target.textContent = '';
		    event.target.appendChild(input);
		    input.focus();
		    
		    
		  }
		});
//여기까지 테스트코드
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
				
				//여기서부터 테스트코드
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