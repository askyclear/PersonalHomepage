<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
    <title>Hudini 개인 홈피</title>
</head>
<body>
    <header>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>                        
                    </button>
                    <a class="navbar-brand" href="#">NickName : Hudini</a>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <!-- class가 active면 nav가 선택된 상태라는 거!-->
                        <li class="active"><a href="${pageContext.request.contextPath }/main"><span class="nav_title">홈</span></a></li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                               <span class="nav_title">게시판</span>
                            </a>
                            <ul class="dropdown-menu" id="board-categories">
                                <c:forEach items="${categories }" var="category">
                                	<c:choose>
                            		<c:when test="${category.id eq boardCategoryId }">
                            			<li class="active"><a href="${pageContext.request.contextPath }/board?boardCategoryId=${category.id }">${category.name }</a></li>
                            		</c:when>
                            		<c:otherwise>
                                		<li><a href="${pageContext.request.contextPath }/board?boardCategoryId=${category.id }">${category.name }</a></li>
                                	</c:otherwise>
                                	</c:choose>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <span class="nav_title">사진첩</span></a>
                            <ul class="dropdown-menu" id="photo-categories">
                                <li><a href="${pageContext.request.contextPath }/album">전체보기</a></li>
                            </ul>
                        </li>
                        <li><a href="${pageContext.request.contextPath }"><span class="nav_title">설정</span></a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${pageContext.request.contextPath }/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <div class="container-fluid text-center">
        <div class="row content">
            <div class="col-sm-2">
                <div class="sidebar-nav-fixed affix">
                    <div class="well">
                        <ul class="nav ">
                            <li class="nav-header">카테고리</li>
                            <li class="active"><a href="#">전체보기</a>
                            </li>
                            <li><a href="#">Link</a>
                            </li>
                            <li><a href="#">Link</a>
                            </li>
                            <li><a href="#">Link</a>
                            </li>
                        </ul>
                        <ul class="pagination">
                            <div>페이지선택</div>
                            <li><a href="">1</a></li>
                            <li><a href="">2</a></li>
                            <li><a href="">3</a></li>
                            <li><a href="">4</a></li>
                            <li><a href="">5</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-sm-9">
               <div class="well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p></p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-3 well">
                        <p>Some text..</p>
                        <img src="../../placehold.it/150x80-text=IMAGE.html" class="img-responsive" style="width:100%" alt="Image">
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>