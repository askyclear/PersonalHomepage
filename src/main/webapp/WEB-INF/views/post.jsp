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
					<li><a href="${pageContext.request.contextPath }/setting">${sessionScope.user.nickname }님
							환영합니다 </a></li>
					<li><a
						href="${pageContext.request.contextPath }/login/logout.do"><span
							class="glyphicon glyphicon-log-in"></span> Logout</a></li>

				</c:if>
			</ul>
		</div>
	</div>
	</nav> </header>
	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-md-2"
				style="position: fixed; top: 90px; width: 220px; z-index: 2;">
				<div class="navbar-default sidebar">
					<div class="well">
						<ul class="nav ">
							<li class="nav-header">카테고리</li>
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
						</ul>
						<ul class="pagination">
							<div>페이지선택</div>
							<c:forEach var="i" begin="1" end="${pageCount }" step="1">
								<c:if test="${i % 5 == 0 }">
									<br>
								</c:if>
								<c:choose>
									<c:when test="${i } == ${curPage }">
										<li class="active"><a
											href="${pageContext.request.contextPath }/board?boardCategoryId=${boardCategoryId }&page=${i }">${i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.request.contextPath }/board?boardCategoryId=${boardCategoryId }&page=${i }">${i }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-lg-10"
				style="position: absolute; left: 220px; height: 100%;">
				<table class="table table-bordered table-striped table-hover">
					<thead class="text-center">
						<tr>
							<th class="col-sm-1 text-center">번호</th>
							<th class="col-sm-6 text-center">제목</th>
							<th class="col-sm-1 text-center">글쓴이</th>
							<th class="col-sm-1 text-center">날짜</th>
							<th class="col-sm-1 text-center">조회</th>
						</tr>
					</thead>
					<tbody id="board-content">
						<c:forEach items="${boards }" var="board">
							<tr class="board">
								<td>${board.id }</td>
								<td>${board.title }</td>
								<td>${board.userInfo.nickname }</td>
								<td>${board.modifyDate }</td>
								<td>${board.viewCount }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<button id="write_btn" type="button"
					class="btn btn-md btn-primary btn-block" style="float: right">
					글쓰기</button>
				<ul class="pagination">
					<li class="left"><span> < </span></li>

					<c:forEach var="i" begin="1" end="${pageCount }" step="1">
						<c:choose>
							<c:when test="${i } == ${curPage }">
								<li class="active"><a
									href="${pageContext.request.contextPath }/board?boardCategoryId=${boardCategoryId }&page=${i }">${i }</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href="${pageContext.request.contextPath }/board?boardCategoryId=${boardCategoryId }&page=${i }">${i }</a></li>
							</c:otherwise>
						</c:choose>

					</c:forEach>
					<li class="right"><span> > </span></li>

				</ul>
				<script type="text/javascript">
					var write_btn = document.getElementById("write_btn");
					write_btn
							.addEventListener(
									"click",
									function(evt) {
										window.location.href = '${pageContext.request.contextPath }'
												+ '/board/write?boardCegoryId='
												+ '${boardCategoryId }';
									});
					var boards = document.getElementById("board-content");
					boards
							.addEventListener(
									"click",
									function(evt) {
										id = evt.target.parentElement.firstElementChild.innerText;
										window.location.href = '${pageContext.request.contextPath }'
												+ '/board/read.do?boardIndex='
												+ id
												+ '&boardCegoryId='
												+ '${boardCategoryId }';

									});
				</script>
			</div>
		</div>
	</div>
</body>

</html>
