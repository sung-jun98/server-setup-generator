<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시작페이지</title>
<style>
    @import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css');

	* {
	  margin: 0;
	  padding: 0;
	  box-sizing: border-box;
	  font-family: Pretendard, 'Malgun Gothic', sans-serif;
	}
	
	.dropBox {
	  width: 90vw;
	  height: 80vh;
	   
	  margin: .6rem;
	  overflow: auto;
	  
	  display: flex;
	  justify-content: center;
	  align-items: center;
	
	  border-radius: 5px;
	  border: 4px dashed #ddd;
	  user-select: none;
	  transition: 0.4s;
	}
	
	/* 드롭 반응 */
	.dropBox.active {
	  background: #ddd;
	}
	
	.dropBox h1 {
	  font-size: 1.8rem;
	}
</style>
<script src="dynamic.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    
</head>
<body>
    <div class="dropBox">
	  <h1>HTML파일 드롭 </h1>
	</div>
</body>
</html>