<%--
	Fragmento de paginação reutilizado por todas as listagens.
	Uso: <%@include file="pagination.jsp"%>
	Atributos esperados (enviados por ControllerUtil.setPagination):
	  currentPage, totalPages, totalItems, paginationUrl
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div id="bottom" class="row">
	<div class="col-md-12">
		<ul class="pagination">
			<c:choose>
				<c:when test="${currentPage > 1}">
					<li><a href="${paginationUrl}?page=${currentPage - 1}" rel="prev">&lt; Anterior</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><a>&lt; Anterior</a></li>
				</c:otherwise>
			</c:choose>

			<c:forEach var="p" begin="1" end="${totalPages}">
				<li class="${p == currentPage ? 'active' : ''}">
					<a href="${paginationUrl}?page=${p}">${p}</a>
				</li>
			</c:forEach>

			<c:choose>
				<c:when test="${currentPage < totalPages}">
					<li><a href="${paginationUrl}?page=${currentPage + 1}" rel="next">Pr&oacute;ximo &gt;</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><a>Pr&oacute;ximo &gt;</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		<p class="text-muted">
			P&aacute;gina ${currentPage} de ${totalPages} &middot; ${totalItems} registro(s)
		</p>
	</div>
</div>
