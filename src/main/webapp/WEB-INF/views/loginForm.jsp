<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
<title>Hudini 개인 홈피</title>
</head>

<body>
	<header> <nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">NickName : Hudini</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<!-- class가 active면 nav가 선택된 상태라는 거!-->
				<li class="active"><a
					href="${pageContext.request.contextPath }/main"><span
						class="nav_title">홈</span></a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <span class="nav_title">게시판</span>
				</a>
					<ul class="dropdown-menu" id="board-categories">
						<c:forEach items="${categories }" var="category">
							<c:choose>
								<c:when test="${category.id eq boardCategoryId }">
									<li class="active"><a
										href="${pageContext.request.contextPath }/board?boardCategoryId=${category.id }">${category.name }</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="${pageContext.request.contextPath }/board?boardCategoryId=${category.id }">${category.name }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <span class="nav_title">사진첩</span></a>
					<ul class="dropdown-menu" id="photo-categories">
						<li><a href="${pageContext.request.contextPath }/album">전체보기</a></li>
					</ul></li>
				<li><a href="${pageContext.request.contextPath }"><span
						class="nav_title">설정</span></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${empty sessionScope.isLogined }">
				<li><a href="${pageContext.request.contextPath }/login"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</c:if>
				<c:if test="${not empty sessionScope.isLogined }">
					<li><span>${sessionScope.id }님 환영합니다</span><a href="${pageContext.request.contextPath }/login/logout.do"><span
						class="glyphicon glyphicon-log-in"></span> Logout</a></li>
						
				</c:if>
			</ul>
		</div>
	</div>
	</nav> </header>
	<div class="container text-center" style="width:50%; position: absolute; left:25%; top:25%;">
		<div style="margin: 0 auto;">
			<div>
				<h2>로그인 화면</h2>
			</div>
			<form action="login/login.do" method="post">
				<div class="form-group row">
					<label for="userId" class="col-sm-2 col-form-labe"><b>아이디</b></label>
					<div class="col-sm-10">
						<input type="text" class="form-control" placeholder="아이디를 입력하세요."
							name="userId" required value="${userId }">
					</div>
				</div>
				
				<div class="form-group row">
					<label for="password" class="col-sm-2 col-form-labe"><b>비밀번호</b></label>
					<div class="col-sm-10">
						<input type="password" class="form-control"
							placeholder="비밀번호를 입력하세요." name="password" required value="${password }">
					</div>
				</div>
				<div class="form-gorup">
					<button type="submit" class="form-control btn btn-success">Login</button>
					
				</div>

				<br>
				<div class="container" style="background-color: #f1f1f1; width:100%;" >
					
					<button type="button" class="btn btn-success"><a href="${pageContext.request.contextPath }/signup">회원가입</a></button>
					<br>
					<span class="psw">Forgot Password <a href="#">비밀번호 잊음?</a></span>
				</div>
			</form>
		</div>
	</div>
	<% String isError =(String) request.getAttribute("errorMessage");
		if(isError != null){
			out.println("<script> alert('" + isError + "')</script>");
		}
	%>
</body>
</html>