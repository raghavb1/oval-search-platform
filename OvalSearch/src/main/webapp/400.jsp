<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:resources title="Error Page">
	<div id="content" class="col-sm-11 full">
		<div class="row box-error">
			<div class="col-lg-3 col-lg-offset-5 col-md-6 col-md-offset-3">
				<h1>Bad request</h1>
				<p>Invalid Parameters Passed. Please correct and proceed.</p>
				<a class="navbar-brand" href='<c:url value="/home" />'
					style="width: 40%"> <span>Home</span> </a>
			</div>
			<!--/col-->
		</div>
		<!--/row-->
	</div>
	<!--/content-->
</tags:resources>