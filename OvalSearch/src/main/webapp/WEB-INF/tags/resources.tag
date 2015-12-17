<%@ tag description="Project required css and js files" language="java"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="title" required="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${title}</title>
<jsp:include page="/framework-resource.jsp" />
<link rel="icon" href='<c:url value="/img/Wms.png"/>'
	type="image/x-icon" />
</head>
<body>
	<div class="container">
		<div class="row">
			<jsp:doBody />
		</div>
	</div>
	<footer>
	<div class="row">
		<div class="col-sm-12" style="text-align: center">&copy;
			Snapdeal.com</div>
		<!--/.col-->
	</div>
	<!--/.row--> </footer>
</body>
</html>
