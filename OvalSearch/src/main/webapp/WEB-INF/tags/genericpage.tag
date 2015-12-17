<%@ tag description="generic page for courier" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="title" required="true" %>
<%@ attribute name="userName" required="true" %>
<%@ attribute name="headScript" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, inital-scale=1.0">
	
	<!-- Title -->
	<title>${title}</title>
	
	<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/style.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/retina.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/panel.css" />" rel="stylesheet">
	<link href="<c:url value="/css/custom.css" />" rel="stylesheet">
    <link href="<c:url value="/css/jquery.appendGrid-1.4.1.min.css" />" rel="stylesheet">
    <!-- end: CSS -->

    <script src="<c:url value="/js/jquery-2.0.3.min.js"/>"></script>
    <script src="<c:url value="/js/jquery-migrate-1.2.1.min.js"/>"></script>
    <script src="<c:url value="/js/pages/form-elements.js"/>"></script>
    <script src="<c:url value="/js/jquery.validate.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/js/jquery.appendGrid-1.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
    <!-- page scripts -->

    <script src="<c:url value="/js/jquery.dataTables.min.js"/>"></script>
    <script src="<c:url value="/js/jquery.chosen.min.js"/>"></script>
    <script src="<c:url value="/js/dataTables.bootstrap.min.js"/>"></script>
    <script src="<c:url value="/js/jquery.cleditor.min.js"/>"></script>
    <!-- theme scripts -->
    <script src="<c:url value="/js/custom.min.js"/>"></script>
    <script src="<c:url value="/js/core.min.js"/>"></script>
    <script src="<c:url value="/js/table.js"/>"></script>
    <script src="<c:url value="/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-datepicker.min.js"/>"></script>
    <script src="<c:url value="/js/jquery.noty.min.js"/>"></script>
    <jsp:invoke fragment="headScript" />
</head>
<body>
	<header class="navbar">
		<div class="container">
			<!-- start: Header Menu -->
			<a class="navbar-brand col-md-2 col-sm-1 col-xs-2" href='<c:url value="/home" />'>
				<span>Home</span>
			</a>
			<div class="nav-no-collapse header-nav">
				<ul class="nav navbar-nav pull-right">
					<!-- start: User Dropdown -->
					<li class="dropdown"><a class="btn account dropdown-toggle"
						data-toggle="dropdown" href="#">
							<div class="avatar">
								<img src="<c:url value="/img/user.png" />" alt="User">
							</div>
							<div class="user">
								<span class="hello">Welcome!</span> <span class="name">${userName}</span>
							</div> </a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/j_spring_security_logout" />"><i
									class="fa fa-cog"></i> Logout</a></li>
						</ul>
					</li>
					<!-- end: User Dropdown -->
				</ul>
			</div>
			<!-- end: Header Menu -->
		</div>
	</header>
	<jsp:invoke fragment="header" />
	<div class="container">
		<div class="row">
			<jsp:include page="/WEB-INF/sidebar.jsp"></jsp:include>
			<jsp:doBody/>
		</div>
	</div>
	<jsp:invoke fragment="footer" />
</body>
</html>