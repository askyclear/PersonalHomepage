<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
	table {
	width:100%;
	}
</style>
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
								<c:when test="${category.id eq albumCategoryId }">
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
		<div class="row content">
			<div class="col-md-2"
				style="position: fixed; top: 90px; width: 220px; z-index: 2;">
				<div class="navbar-default sidebar">
					<div class="well">
						<ul class="nav ">
							<li class="nav-header">카테고리</li>
							<c:forEach items="${albumcategories }" var="category">
								<c:choose>
									<c:when test="${category.id eq albumCategoryId }">
										<li class="active"><a
											href="${pageContext.request.contextPath }/album?albumCategoryId=${category.id }">${category.name }</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.request.contextPath }/album?albumCategoryId=${category.id }">${category.name }</a></li>
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
											href="${pageContext.request.contextPath }/album?albumCategoryId=${albumCategoryId }&page=${i }">${i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.request.contextPath }/album?albumCategoryId=${albumCategoryId }&page=${i }">${i }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-lg-10"
				style="position: absolute; left: 220px; height: 100%;">
				<div class="col-md-12 col-lg-10 photo-content">
					<ul class="col-md-4">
						<c:forEach items="${photos }" var="photo" varStatus="status">
							<c:if test="${status.index %3 == 0 }">
								<li style="display: inline-block; width: 100%;">
									<div class=" well">
										<table>
										<thead>
											<tr>
												<td><span>번호</span></td>
												<td><span>작성자</span></td>
												<td><span>조회수</span></td>
											</tr>
										</thead>
										<tbody>
											
											<tr>
											<td><span class="index">${photo.id }</span></td>
											<td><span>${photo.userInfo.nickname }</span></td>
											<td><span>${photo.viewCount }</span></td>
											</tr>
											<tr>
											<td  colspan="3"><img
											src="${pageContext.request.contextPath }${photo.saveFileName }"
											class="img-thumbnail" style="width: 100%" alt="Image"></td></tr>
											<tr>
											<td colspan="3">
											<p><b><c:if test="${fn:length(photo.title) >= 10 }">
												${fn:substring(photo.title,0,10) }...
											</c:if>
											<c:if test="${fn:length(photo.title) <= 10 }">
												${photo.title }
											</c:if>
										</b><br>
											<c:if test="${fn:length(photo.content) >= 20 }">
												${fn:substring(photo.content,0,20) }...
											</c:if>
											<c:if test="${fn:length(photo.content) <= 20 }">
												${photo.content }
											</c:if>
										</p>
											</td>
											</tr>	
										</tbody>
										</table>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
					<ul class="col-md-4">
						<c:forEach items="${photos }" var="photo" varStatus="status">
							<c:if test="${status.index %3 == 1 }">
								<li style="display: inline-block; width: 100%;">
									<div class=" well">
										<table>
										<thead>
											<tr>
												<td><span>번호</span></td>
												<td><span>작성자</span></td>
												<td><span>조회수</span></td>
											</tr>
										</thead>
										<tbody>
											
											<tr>
											<td><span class="index">${photo.id }</span></td>
											<td><span>${photo.userInfo.nickname }</span></td>
											<td><span>${photo.viewCount }</span></td>
											</tr>
											<tr>
											<td  colspan="3"><img
											src="${pageContext.request.contextPath }${photo.saveFileName }"
											class="img-thumbnail" style="width: 100%" alt="Image"></td></tr>
											<tr>
											<td colspan="3">
											<p><b><c:if test="${fn:length(photo.title) >= 10 }">
												${fn:substring(photo.title,0,10) }...
											</c:if>
											<c:if test="${fn:length(photo.title) <= 10 }">
												${photo.title }
											</c:if>
										</b><br>
											<c:if test="${fn:length(photo.content) >= 20 }">
												${fn:substring(photo.content,0,20) }...
											</c:if>
											<c:if test="${fn:length(photo.content) <= 20 }">
												${photo.content }
											</c:if>
										</p>
											</td>
											</tr>	
										</tbody>
										</table>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
					<ul class="col-md-4">
						<c:forEach items="${photos }" var="photo" varStatus="status">
							<c:if test="${status.index %3 == 2 }">
								<li style="display: inline-block; width: 100%;">
									<div class=" well">
										<table>
										<thead>
											<tr>
												<td><span>번호</span></td>
												<td><span>작성자</span></td>
												<td><span>조회수</span></td>
											</tr>
										</thead>
										<tbody>
											
											<tr>
											<td><span class="index">${photo.id }</span></td>
											<td><span>${photo.userInfo.nickname }</span></td>
											<td><span>${photo.viewCount }</span></td>
											</tr>
											<tr>
											<td  colspan="3"><img
											src="${pageContext.request.contextPath }${photo.saveFileName }"
											class="img-thumbnail" style="width: 100%" alt="Image"></td></tr>
											<tr>
											<td colspan="3">
											<p><b><c:if test="${fn:length(photo.title) >= 10 }">
												${fn:substring(photo.title,0,10) }...
											</c:if>
											<c:if test="${fn:length(photo.title) <= 10 }">
												${photo.title }
											</c:if>
										</b><br>
											<c:if test="${fn:length(photo.content) >= 20 }">
												${fn:substring(photo.content,0,20) }...
											</c:if>
											<c:if test="${fn:length(photo.content) <= 20 }">
												${photo.content }
											</c:if>
										</p>
											</td>
											</tr>	
										</tbody>
										</table>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="row">
					<div class="col-md-11">
						<button style="height: 38px" value="사진등록"
							class="btn btn-md btn-primary btn-block"
							onclick="window.location.href='${pageContext.request.contextPath }/album/write?albumCategoryId=${albumCategoryId }'">
							<span>사진등록</span>
						</button>
					</div>
					<div class="col-md-1"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var photo_content = document.querySelector(".photo-content");
		photo_content.addEventListener("click", function(evt) {
			if(evt.target.tagName =="TD" || evt.target.tagName == "TR" || evt.target.tagName =="IMG" || evt.target.tagName =="P"){
				var parentEle = backTrace(evt.target);
				var id = parentEle.getElementsByClassName("index")[0].innerText;
				window.location.href = '${pageContext.request.contextPath }'
						+ '/album/read.do?albumIndex='
						+ id
						+ '&albumCategoryId='
						+ '${albumCategoryId }';
				
			}
		});
		function backTrace(ele){
			var childEle = ele;
			var count = 0;
			console.log(ele.parentElement);
			while(count < 5){
				if(childEle.tagName === 'DIV'){
					count = 5;
				}else{
					childEle = childEle.parentElement;
					count++;
				}
			};
			return childEle;
		}
	</script>
</body>

</html>
