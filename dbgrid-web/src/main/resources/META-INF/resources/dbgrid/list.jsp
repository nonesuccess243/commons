<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/bootstrap/3.3.7/header.jsp"%>
<html>
<body class="container">

	<div class="page-header">
		<h2>Dbgrid demo</h2>
	</div>


	<div class="panel-body">
		<h3 class="panel-title">已配置的dbgrid列表</h3>
		<table class="table table-hover">
			<tr>
				<th>gridTitle</th>
				<th>gridName</th>
				<th>dbgrid meta</th>
				<th>dbgrid data</th>
			</tr>
			<c:forEach items="${dbgrids}" var="dbgrid">
				<tr>
					<td>${dbgrid.gridTitle}</td>
					<td>${dbgrid.gridName}</td>
					<td><a href="${dbgrid.gridName}.jsonmeta" target="_blank">dbgrid
							meta</a></td>
					<td><a href="${dbgrid.gridName}.jsonlist" target="_blank">dbgrid
							data</a></td>
				</tr>

			</c:forEach>
		</table>
	</div>



</body>
</html>
