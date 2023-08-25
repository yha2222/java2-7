<%@page import="kr.or.ddit.member.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	List<MemberVO> memList = (List<MemberVO>)request.getAttribute("memList");

	String msg = (String)session.getAttribute("msg");
	if(msg != null){
		session.removeAttribute("msg"); // 꺼낸 거 String에 있으니까 지워도 됨
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>주소</th>
		</tr>
		
<%
	if(memList.size() == 0){
%>
	<tr><td colspan="4">데이터가 존재하지 않습니다.</td></tr>
<%
	} else {
		
		for(MemberVO mv : memList){
%>
		<tr>
			<td><%out.print(mv.getMemId()); %></td>
			<td><%out.print(mv.getMemName()); %></td>
			<td><%out.print(mv.getMemTel()); %></td>
			<td><%out.print(mv.getMemAddr()); %></td>
		</tr>
<%
		}
	}
%>
	<tr align="center">
		<td colspan="4"><a href="./insert.do">[회원 등록]</a></td>
	</tr>

	</table>
<%
	if( msg != null && msg.equals("성공")){
%>		
<script>
	alert('정상적으로 처리되었습니다.');
</script>
<%
	}
%>

</body>
</html>