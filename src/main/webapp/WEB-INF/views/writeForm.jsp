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
		<c:choose>
			<c:when test="${not empty board }">
				<form
					action="${pageContext.request.contextPath }/board/update/update.do"
					method="post" enctype="multipart/form-data">
			</c:when>
			<c:otherwise>
				<form action="${pageContext.request.contextPath }/board/write.do"
					method="post" enctype="multipart/form-data">
			</c:otherwise>
		</c:choose>
		<div class="form-group">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<td colspan="2">
							<div class="form-group row">
								<label for="category-select" class="col-sm-4 col-form-labe">카테고리</label>
								<div class="col-sm-6">
									<select class="form-control" id="category-select"
										name="boardCategoryId">
										<c:forEach items="${boardcategories }" var="category">
											<c:if test="${category.id != 0 }">
												<c:choose>
													<c:when test="${category.id eq curCategory }">
														<option value="${category.id }" selected>${category.name }</option>
													</c:when>
													<c:otherwise>
														<option value="${category.id }">${category.name }</option>
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</td>
						<td colspan="2">
							<div class="form-group row">
								<label for="title" class="col-sm-2 col-form-labe">제목</label>
								<div class="col-sm-10">
									<input id="title" class="form-control" type="text" name="title"
										placeholder="제목을 입력하세요" required value="${board.title }">
								</div>
							</div>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4"><label for="content"
							class="col-sm-1 col-form-labe">내용</label> <textarea
								class="form-control" id="content" name="content" rows="10"
								placeholder="내용을 입력하세요." required style="resize: none">${board.content }</textarea>
						</td>
					</tr>
				</tbody>
			</table>


			<c:choose>
				<c:when test="${not empty board }">
					<div class="form-group text-left">
						<label for="fileUpload">첨부파일</label> 
						<input type="file"
							class="form-control-file" id="fileUpload" name="fileUpload" style="display:none">
						<c:forEach items="${fileInfo }" var="boardFile">
						<a
							href="${pageContext.request.contextPath }/board/${boardFile.fileName }/${board.id }"><span>${boardFile.fileName }</span></a>
					</c:forEach>
						<!-- image만 올릴때는 accept="image/png,image/jpeg" -->
					</div>
					<input type="text" style="display: none;" name="id"
						value="${board.id }">
					<input type="submit" class="form-control btn btn-info"
						value="수정">
						<input type="button" class="form-control btn btn-primary" id="cancleBtn"
						value="취소">
				</c:when>
				<c:otherwise>
					<div class="form-group text-left">
						<label for="fileUpload">첨부파일</label> <input type="file"
							class="form-control-file" id="fileUpload" name="fileUpload">
						<!-- image만 올릴때는 accept="image/png,image/jpeg" -->
					</div>
					<input type="submit" class="form-control btn btn-info"
						value="확인">
						<input type="button" class="form-control btn btn-primary" id="cancleBtn"
						value="취소">
				</c:otherwise>
			</c:choose>



		</div>
		</form>
	</div>
	<script type="text/javascript">
		const elFile = document.getElementById("fileUpload");
		elFile.addEventListener("change", function() {
			const image = evt.target.files[0];
			// ele.src = window.URL.createObjectURL(image); 로 썸네일 보여줌;
		});
	</script>
	<script type="text/javascript">
		const cancleBt = document.getElementById("cancleBtn");
		cancleBt.addEventListener("click", function(){
			window.location.href = "${pageContext.request.contextPath }/board/read.do?boardIndex=${board.id }&boardCegoryId=${curCategory }";
		});
	</script>
</body>
</html>