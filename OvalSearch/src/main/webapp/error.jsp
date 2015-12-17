<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:resources title="Error Page">
	<div id="content" class="col-sm-11 full">
		<div class="row box-error">
			<div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3">
			<img src="<c:url value="/img/blocked.png" />" alt="Access Denied" height="80px" width="80px" style="margin-left: 35%">
				<h1>Access Denied !!</h1>
				<p>You are not allowed to access this page.</p>
				<a class="navbar-brand"
					href='<c:url value="/home" />' style="width: 40%"> <span>Home</span> </a>
			</div>
			<!--/col-->
		</div>
		<!--/row-->
	</div>
	<!--/content-->
</tags:resources>