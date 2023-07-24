<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 기능 테스트용 샘플</title>
</head>
<body>
	<form method="POST" action="/server_setup_generator/test_login_apply">
		<input type="text" id="id" name="id" placeholder="아이디">
		<input type="text" id="password" name="password" placeholder="패스워드">
		<input type="submit" value="로그인">
	</form>
</body>
</html>