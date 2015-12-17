<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:resources title="Error Page">
	<div id="content" class="col-sm-11 full">
		<div class="row box-error">
			<div class="col-lg-5 col-lg-offset-4 col-md-6 col-md-offset-3">
				<h1>Page Not Available  !!!</h1>
				<p>The page you are looking for is temporarily unavailable.</p>
				<a class="navbar-brand" href='<c:url value="/home" />'
					style="width: 40%"> <span>Home</span> </a>
			</div>
			<!--/col-->
		</div>
		<!--/row-->
	</div>
	<!--/content-->
</tags:resources>