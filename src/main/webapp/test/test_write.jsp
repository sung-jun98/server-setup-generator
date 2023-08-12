<html>
 <head></head> 
 <body>
   <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
  <meta charset="UTF-8"> 
  <title>게시물 업로드 테스트</title> 
  <div class="row"> 
   <form method="post" action="../writeAction"> 
    <input type="submit" class="btn btn-primary pull-right" value="글쓰기"> 
    <table class="table-striped" style="text-align:center; border:1px solid #ddddd"> 
     <tbody> 
      <tr> 
       <td><input type="text" class="form-control" placeholder="글제목" name="bbsTitle" maxlength="50"></td> 
       <td><textarea type="text" class="form-control" placeholder="글내용" name="bbsContent" maxlength="50"></textarea></td> 
      </tr> 
     </tbody> 
    </table> 
    <input type="hidden" name="sessionID" value="EFF5E960562FBAA6F0E868DE0637BC0F"> 
    <input type="hidden" name="sessionID" value="EFF5E960562FBAA6F0E868DE0637BC0F">
   </form> 
  </div>  
 </body>
</html>