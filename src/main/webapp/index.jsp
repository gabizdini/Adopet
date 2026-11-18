<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%--
	Página inicial: apenas navegação, sem dados do banco.
	Não precisa de controller: é aberta pelo Tomcat como welcome-file (web.xml).
	O AuthFilter (/*) continua exigindo login para acessá-la.
--%>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>

		<div id="container" class="container-fluid text-center">
			<h3 class="page-header">Bem-vindo(a), ${sessionScope.loggedUser.getName()}!</h3>
			<p class="lead">Escolha uma área para gerenciar:</p>

			<div class="row">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/users" class="btn btn-default btn-lg">
						<span class="glyphicon glyphicon-user"></span>&nbsp;Usuários</a>
					<a href="${pageContext.request.contextPath}/posts" class="btn btn-default btn-lg">
						<span class="glyphicon glyphicon-pencil"></span>&nbsp;Posts</a>
					<a href="${pageContext.request.contextPath}/companies" class="btn btn-default btn-lg">
						<span class="glyphicon glyphicon-pushpin"></span>&nbsp;Associações</a>
					<a href="${pageContext.request.contextPath}/abrigos" class="btn btn-primary btn-lg">
						<span class="glyphicon glyphicon-tower"></span>&nbsp;Abrigos</a>
					<a href="${pageContext.request.contextPath}/animais" class="btn btn-primary btn-lg">
						<span class="glyphicon glyphicon-heart"></span>&nbsp;Animais</a>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>
