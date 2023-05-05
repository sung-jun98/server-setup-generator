//demo.jsp에서 DB관련 정보를 입력하고 submit을 눌렀을 때, 페이지 전체가 리프레시 되지 않고, 일부분만 바뀔 수 있게 AJAX를 통해서 
//서버와 비동기 통신을 하는 코드 
$(function() {
  $('#Building_DB_form').submit(function(e) {
	    e.preventDefault(); // 기본적인 form submit 동작 방지
		const formData = $('#Building_DB_form').serializeArray();
	console.dir("$('#Building_DB_form').serializeArray() : " + formData );//테스트용
		const data = {};
		
		formData.forEach(function(element) {
		  data[element.name] = element.value;
		});
	console.dir	("data  : " + data);//테스트용
	console.log("JSON.stringify(data)  : " + JSON.stringify(data));//테스트용
		
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