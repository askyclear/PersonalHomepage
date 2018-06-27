<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
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
						<c:forEach items="${boardcategories }" var="category">
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
						<c:forEach items="${albumcategories }" var="category">
							<c:choose>
								<c:when test="${category.id eq boardCategoryId }">
									<li class="active"><a
										href="${pageContext.request.contextPath }/album?albumCategoryId=${category.id }">${category.name }</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="${pageContext.request.contextPath }/album?albumCategoryId=${category.id }">${category.name }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul></li>
				<li><a href="${pageContext.request.contextPath }/setting"><span
						class="nav_title">설정</span></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath }/login"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</ul>
		</div>
	</div>
	</nav> </header>
	<div class="container text-center"
		style="width: 50%; position: absolute; left: 25%; top: 25%;">
		<div style="margin: 0 auto;">
			<div>
				<h2>회원가입 화면</h2>
			</div>
			<form action="${pageContext.request.contextPath }/signup/signup.do" method="post" name="signupform" onsubmit="return check()">
				<div class="form-group row">
					<label for="userId" class="col-sm-2 col-form-labe"><b>아이디</b></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" placeholder="아이디를 입력하세요."
							name="userId" required>

					</div>
					<div class="col-sm-2">
						<input id="duplicationConfirmId" type="button"
							class="btn btn-success" value="중복확인" />
					</div>
				</div>

				<div class="form-group row">
					<label for="nickname" class="col-sm-2 col-form-labe"><b>닉네임</b></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" placeholder="닉네임 입력"
							name="nickname" required>
					</div>
					<div class="col-sm-2">
						<input id="duplicationConfirmNickname" type="button"
							class="btn btn-success " value="중복확인" />
					</div>
				</div>
				<div class="form-group row">
					<label for="password" class="col-sm-2 col-form-labe"><b>비밀번호</b></label>
					<div class="col-sm-10">
						<input type="password" class="form-control password" id="password"
							placeholder="비밀번호를 입력하세요." name="password" required>
					</div>

				</div>
				<div class="form-group row">
					<label for="passwordConfirm" class="col-sm-2 col-form-labe"><b>비밀번호
							확인</b></label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="passwordConfirm"
							placeholder="비밀번호를 입력하세요." name="passwordConfirm" required>
					</div>
					<div class="password_check_block bg-warning"></div>
				</div>

				<div class="form-gorup">
					<button type="submit" class="form-control btn btn-success"  >회원가입</button>


				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="js/signupcheck.js"></script>
	<script type="text/javascript" src="js/onLoad.js"></script>
</body>
</html>