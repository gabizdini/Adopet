<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>Adopet - ${not empty animal ? 'Atualizar' : 'Adicionar'} Animal</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty animal ? 'Atualizar' : 'Adicionar'} Animal</h3>

		<form action="${pageContext.request.contextPath}/animal/${action}" method="POST">

			<input type="hidden" value="${animal.getId()}" name="animalId">

			<div class="row">
				<!-- input text -->
				<div class="form-group col-md-6">
					<label for="nome">Nome</label>
					<input type="text" class="form-control" id="nome" name="nome"
						   autofocus="autofocus" placeholder="Nome do animal" maxlength="100"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe o nome do animal.')"
						   oninput="setCustomValidity('')"
						   value="${animal.getNome()}" />
				</div>

				<!-- input radio -->
				<div class="form-group col-md-6">
					<label>Espécie</label>
					<div>
						<label class="radio-inline">
							<input type="radio" name="especie" value="Cachorro" required
								${empty animal || animal.getEspecie() == 'Cachorro' ? 'checked' : ''} /> Cachorro
						</label>
						<label class="radio-inline">
							<input type="radio" name="especie" value="Gato"
								${animal.getEspecie() == 'Gato' ? 'checked' : ''} /> Gato
						</label>
						<label class="radio-inline">
							<input type="radio" name="especie" value="Outro"
								${animal.getEspecie() == 'Outro' ? 'checked' : ''} /> Outro
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<!-- select com opções fixas -->
				<div class="form-group col-md-3">
					<label for="porte">Porte</label>
					<select id="porte" class="form-control" name="porte"
							required
							oninvalid="this.setCustomValidity('Por favor, selecione o porte.')"
							oninput="setCustomValidity('')">
						<option value="" ${not empty animal ? '' : 'selected'}>Selecione o porte</option>
						<option value="Pequeno" ${animal.getPorte() == 'Pequeno' ? 'selected' : ''}>Pequeno</option>
						<option value="Médio" ${animal.getPorte() == 'Médio' ? 'selected' : ''}>Médio</option>
						<option value="Grande" ${animal.getPorte() == 'Grande' ? 'selected' : ''}>Grande</option>
					</select>
				</div>

				<!-- input date -->
				<div class="form-group col-md-3">
					<label for="dataResgate">Data do resgate</label>
					<input type="date" class="form-control" id="dataResgate" name="dataResgate"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe a data do resgate.')"
						   oninput="setCustomValidity('')"
						   value="${animal.getDataResgate()}" />
				</div>

				<!-- input number com decimais -->
				<div class="form-group col-md-3">
					<label for="peso">Peso (kg)</label>
					<input type="number" class="form-control" id="peso" name="peso"
						   min="0.1" max="999" step="0.01"
						   required
						   oninvalid="this.setCustomValidity('Informe o peso em kg (ex.: 12.5).')"
						   oninput="setCustomValidity('')"
						   value="${animal.getPeso()}" />
				</div>

				<!-- input checkbox -->
				<div class="form-group col-md-3">
					<label>&nbsp;</label>
					<div class="checkbox">
						<label>
							<input type="checkbox" id="castrado" name="castrado" value="true"
								${animal.isCastrado() ? 'checked' : ''} />
							Castrado
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<!-- select com opções fixas -->
				<div class="form-group col-md-6">
					<label for="status">Status</label>
					<select id="status" class="form-control" name="status" required>
						<option value="Disponível" ${animal.getStatus() == 'Disponível' ? 'selected' : ''}>Disponível</option>
						<option value="Em processo" ${animal.getStatus() == 'Em processo' ? 'selected' : ''}>Em processo de adoção</option>
						<option value="Adotado" ${animal.getStatus() == 'Adotado' ? 'selected' : ''}>Adotado</option>
					</select>
				</div>

				<!-- select (chave estrangeira: abrigos) -->
				<div class="form-group col-md-6">
					<label for="abrigo">Abrigo</label>
					<select id="abrigo" class="form-control" name="abrigo"
							required
							oninvalid="this.setCustomValidity('Por favor, selecione o abrigo.')"
							oninput="setCustomValidity('')">
						<option value="" ${not empty animal ? '' : 'selected'}>Selecione um abrigo</option>
						<c:forEach var="ab" items="${abrigos}">
							<option value="${ab.getId()}"
								${animal.getAbrigo().getId() == ab.getId() ? 'selected' : ''}>
								${ab.getNome()}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/animais" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${not empty animal ? 'Atualizar' : 'Cadastrar'} Animal</button>
				</div>
			</div>
		</form>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
