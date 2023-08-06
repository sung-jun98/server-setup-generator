<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 업로드 테스트</title>
</head>
<body>
	<div class="row">
		<form method="post" action="../writeAction">
			<table class="table-striped" style="text-align:center; border:1px solid #ddddd">
				<tbody>
					<tr>
						<td><input type="text" class="form-control" placeholder="글제목" name="bbsTitle"
							maxlength="50"></td>
						<td><textarea type="text" class="form-control" placeholder="글내용" name=bbsContent
							maxlength="50"></textarea></td>
					</tr>
				</tbody>
				<input type="submit" class="btn btn-primary pull-right" value="글쓰기">
			</table>
		</form>
	</div>
</body>
</html>