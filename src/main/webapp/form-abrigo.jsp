<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>Adopet - ${not empty abrigo ? 'Atualizar' : 'Adicionar'} Abrigo</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty abrigo ? 'Atualizar' : 'Adicionar'} Abrigo</h3>

		<form action="${pageContext.request.contextPath}/abrigo/${action}" method="POST">

			<input type="hidden" value="${abrigo.getId()}" name="abrigoId">

			<div class="row">
				<!-- input text -->
				<div class="form-group col-md-6">
					<label for="nome">Nome</label>
					<input type="text" class="form-control" id="nome" name="nome"
						   autofocus="autofocus" placeholder="Nome do abrigo" maxlength="100"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe o nome do abrigo.')"
						   oninput="setCustomValidity('')"
						   value="${abrigo.getNome()}" />
				</div>

				<!-- input tel -->
				<div class="form-group col-md-3">
					<label for="telefone">Telefone</label>
					<input type="tel" class="form-control" id="telefone" name="telefone"
						   placeholder="(35) 99999-9999" maxlength="20"
						   pattern="[0-9\(\)\+\- ]{8,20}"
						   required
						   oninvalid="this.setCustomValidity('Informe um telefone válido, ex.: (35) 99999-9999.')"
						   oninput="setCustomValidity('')"
						   value="${abrigo.getTelefone()}" />
				</div>

				<!-- input date -->
				<div class="form-group col-md-3">
					<label for="dataFundacao">Data de fundação</label>
					<input type="date" class="form-control" id="dataFundacao" name="dataFundacao"
						   value="${abrigo.getDataFundacao()}" />
				</div>
			</div>

			<div class="row">
				<!-- input number -->
				<div class="form-group col-md-3">
					<label for="capacidade">Capacidade (animais)</label>
					<input type="number" class="form-control" id="capacidade" name="capacidade"
						   min="1" max="1000" step="1"
						   required
						   oninvalid="this.setCustomValidity('Informe a capacidade (entre 1 e 1000).')"
						   oninput="setCustomValidity('')"
						   value="${abrigo.getCapacidade()}" />
				</div>

				<!-- select (chave estrangeira: companies) -->
				<div class="form-group col-md-6">
					<label for="company">Associação mantenedora</label>
					<select id="company" class="form-control" name="company"
							required
							oninvalid="this.setCustomValidity('Por favor, selecione a associação.')"
							oninput="setCustomValidity('')">
						<option value="" ${not empty abrigo ? '' : 'selected'}>Selecione uma associação</option>
						<c:forEach var="comp" items="${companies}">
							<option value="${comp.getId()}"
								${abrigo.getCompany().getId() == comp.getId() ? 'selected' : ''}>
								${comp.getName()} (${comp.getUser().getName()})
							</option>
						</c:forEach>
					</select>
				</div>

				<!-- input checkbox -->
				<div class="form-group col-md-3">
					<label>&nbsp;</label>
					<div class="checkbox">
						<label>
							<input type="checkbox" id="aceitaVoluntarios" name="aceitaVoluntarios" value="true"
								${abrigo.isAceitaVoluntarios() ? 'checked' : ''} />
							Aceita voluntários
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<!-- textarea -->
				<div class="form-group col-md-12">
					<label for="descricao">Descrição</label>
					<textarea class="form-control" id="descricao" name="descricao" rows="4"
							  placeholder="Fale um pouco sobre o abrigo">${abrigo.getDescricao()}</textarea>
				</div>
			</div>

			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/abrigos" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${not empty abrigo ? 'Atualizar' : 'Cadastrar'} Abrigo</button>
				</div>
			</div>
		</form>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
