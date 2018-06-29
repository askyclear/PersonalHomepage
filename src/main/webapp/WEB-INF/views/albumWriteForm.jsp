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
			<c:when test="${not empty album }">
				<form
					action="${pageContext.request.contextPath }/album/update/update.do"
					method="post" enctype="multipart/form-data">
			</c:when>
			<c:otherwise>
				<form action="${pageContext.request.contextPath }/album/write.do"
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
										name="albumCategoryId">
										<c:forEach items="${albumcategories }" var="category">
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
										placeholder="제목을 입력하세요" required value="${album.title }">
								</div>
							</div>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4"><label for="fileUpload" class="colsm-4 col-form-labe">사진</label><br>
							<img  id="thumb_img" name="thumb_img" >
						
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<input type="file"
							class="form-control-file" id="fileUpload" name="fileUpload" accept="image/*" required>
						</td>
					</tr>
					<tr>
						<td colspan="4"><label for="content"
							class="col-sm-1 col-form-labe">내용</label> <textarea
								class="form-control" id="content" name="content" rows="5"
								placeholder="사진에 대한 설명" required style="resize: none">${album.content }</textarea>
						</td>
					</tr>
					
				</tbody>
			</table>


			<c:choose>
				<c:when test="${not empty album }">
					<input type="submit" class="form-control btn btn-info"
						value="수정">
						<input type="button" class="form-control btn btn-primary" id="cancleBtn"
						value="취소">
				</c:when>
				<c:otherwise>
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
		elFile.addEventListener("change", function(evt) {
			const file = evt.target.files[0];
			var reader = new FileReader();
			var image = new Image();
			reader.readAsDataURL(file);
			reader.onload = function(_file){
				image.src = _file.target.result;
				image.onload = function(){
					var w = this.width,
						h = this.height,
						t = file.type,
						n = file.name,
						s = file.size;
						const thumbImg = document.getElementById("thumb_img");
						if(w > 1024){
							thumbImg.style.width = "80%";
						}
						if(w < 100){
							thumbImg.style.width = "50%";
						}
						thumbImg.src = image.src;
						
				}
			}
			
		});
	</script>
	<script type="text/javascript">
		const cancleBt = document.getElementById("cancleBtn");
		cancleBt.addEventListener("click", function(){
			window.location.href = "${pageContext.request.contextPath }/album/?albumCegoryId=${curCategory }";
		});
	</script>
	</body>
</html>