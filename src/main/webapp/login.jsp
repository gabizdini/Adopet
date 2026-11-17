<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
		<title>Adopet - Login</title>
		<style>
			body { background: #f5f5f5; }
			.login-box {
				max-width: 360px;
				margin: 80px auto;
				padding: 30px;
				background: #fff;
				border-radius: 6px;
				box-shadow: 0 2px 8px rgba(0, 0, 0, .15);
			}
			.login-box .alert { position: static; width: 100%; }
		</style>
	</head>
	<body>
		<div class="login-box">
			<div class="text-center">
				<img height="60" width="60" alt="Adopet" src="${pageContext.request.contextPath}/images/logo.png">
				<h3>Adopet</h3>
				<p class="text-muted">ONG de adoção de animais</p>
			</div>

			<c:if test="${not empty error}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<form action="${pageContext.request.contextPath}/login" method="POST">
				<div class="form-group">
					<label for="email">E-mail</label>
					<input type="email" class="form-control" id="email" name="email"
						   autofocus="autofocus" placeholder="seu@email.com" required
						   value="${email}">
				</div>

				<div class="form-group">
					<label for="password">Senha</label>
					<input type="password" class="form-control" id="password" name="password"
						   placeholder="Sua senha" required>
				</div>

				<button type="submit" class="btn btn-danger btn-block">
					<span class="glyphicon glyphicon-log-in"></span>&nbsp;Entrar
				</button>
			</form>
		</div>
	</body>
</html>
