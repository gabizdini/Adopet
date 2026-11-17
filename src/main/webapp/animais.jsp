<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>Adopet - Animais</title>
</head>
<body>
	<%@include file="modal.html"%>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
		  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  ${message}
		</div>

		<div id="top" class="row">
			<div class="col-md-3">
				<h3>Animais</h3>
			</div>

			<div class="col-md-6">
				<div class="input-group h2">
					<input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar animais">
					<span class="input-group-btn">
						<button class="btn btn-danger" type="submit">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>

			<div class="col-md-3">
				<a href="${pageContext.request.contextPath}/animal/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Animal</a>
			</div>
		</div>

		<hr />

		<div id="list" class="row">
			<div class="table-responsive col-md-12">
				<table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>Nome</th>
							<th>Espécie</th>
							<th>Porte</th>
							<th>Resgate</th>
							<th>Peso</th>
							<th>Castrado</th>
							<th>Status</th>
							<th>Abrigo</th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="animal" items="${animais}">
							<tr>
								<td>${animal.getNome()}</td>
								<td>${animal.getEspecie()}</td>
								<td>${animal.getPorte()}</td>
								<td><fmt:formatDate value="${animal.getDataResgate()}" pattern="dd/MM/yyyy" /></td>
								<td><fmt:formatNumber value="${animal.getPeso()}" minFractionDigits="1" maxFractionDigits="2" /> kg</td>
								<td>${animal.isCastrado() ? 'Sim' : 'Não'}</td>
								<td>
									<span class="label ${animal.getStatus() == 'Disponível' ? 'label-success' : (animal.getStatus() == 'Adotado' ? 'label-default' : 'label-warning')}">
										${animal.getStatus()}
									</span>
								</td>
								<td>${animal.getAbrigo().getNome()}</td>

								<td class="actions">
									<a class="btn btn-info btn-xs"
									   href="${pageContext.request.contextPath}/animal/update?animalId=${animal.getId()}">
										<span class="glyphicon glyphicon-edit"></span>
									</a>
								</td>

								<td class="actions">
									<a class="btn btn-danger btn-xs modal-remove"
									   animal-id="${animal.getId()}"
									   animal-name="${animal.getNome()}" data-toggle="modal"
									   data-target="#delete-modal" href="#"><span
									   class="glyphicon glyphicon-trash"></span></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<%@include file="pagination.jsp"%>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			// fecha o alert após 3 segundos
			setTimeout(function() {
				$("#alert").slideUp(500);
			}, 3000);

			// envia o id e o nome do animal para o modal de exclusão
			$(".modal-remove").click(function () {
				var animalName = $(this).attr('animal-name');
				var animalId = $(this).attr('animal-id');
				$(".modal-body #hiddenValue").text("o animal '" + animalName + "'");
				$("#id").attr("value", animalId);
				$("#form").attr("action", "${pageContext.request.contextPath}/animal/delete");
			});
		});
	</script>
</body>
</html>
