<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HTTP Security Headers: Report</title>
</head>
<body>
<fieldset>
	<legend>HTTP Security Header Report: ${report.name}</legend>
	<table>
		<tr>
			<th></th><th>Header</th><th>Values</th><th>Reference</th>
		</tr>
		<c:forEach var="item" items="${report.items}">
			<tr>
				<td>
						<%-- ${ item.compliant ... only works with boolean, not Boolean } --%>
						<c:if test="${item.rule.isRequired()}">
							${ item.isCompliant() ? "<img src='images/check.png' border=0/>" : "<img src='images/cross.png' border=0/>"}				
						</c:if>
				</td>
				<td>${item.headerName}</td>
				<td>
					<c:if test="${ !item.isPresent() }">(Missing)</c:if>
					<c:forEach var="value" items="${item.headerValues}">
						${value}</br>
					</c:forEach>					
				</td>
				<td>
					<c:if test="${ ! item.isCompliant() }">
						<c:forEach var="ref" items="${item.rule.references}">
							<a href="${ref.url}" target="_blank">${ref.title}</a> | 
						</c:forEach>
					</c:if>			
				</td>
			</tr>
		</c:forEach>
	</table>
</fieldset>
<fieldset>
	<legend>Raw Response Headers</legend>
	<pre>${report.rawHeaders}</pre>
</fieldset>
</body>
</html>