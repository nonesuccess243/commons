<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
<h2>Dbgrid demo</h2>

<table border="1">
	<tr>
		<th>gridTitle</th>
		<th>gridName</th>
		<th>dbgrid meta</th>
		<th>dbgrid data</th>
	</tr>
<c:forEach items="${dbgrids}" var="dbgrid">
	<tr>
	<td>
			${dbgrid.gridTitle}
	</td>
	<td>
			${dbgrid.gridName}
	</td>
	<td>
		<a href="${dbgrid.gridName}.jsonmeta" target="_blank">dbgrid meta</a>
	</td>
	<td>
		<a href="${dbgrid.gridName}.jsonlist" target="_blank">dbgrid data</a>
	</td>
	</tr>

</c:forEach>
</table>

</body>
</html>
