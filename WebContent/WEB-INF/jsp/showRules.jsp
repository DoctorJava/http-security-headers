<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HTTP Security Headers: Rules</title>
</head>
<body>
<fieldset>
	<legend>Selected Rules: ${test.name}</legend>
	<table>
		<tr>
			<th></th><th>Header</th><th>Required</th>
		</tr>
		<c:forEach var="rule" items="${test.rules}">
			<tr>
				<td>

				</td>
				<td>${rule.name}</td>
				<td>${rule.required}</td>
			</tr>
		</c:forEach>
	</table>
</fieldset>

<fieldset>
	<legend>Save Test Configuration</legend>
	<form action="MaintainRules" method=POST>
		<button type="submit">Save Rules</button>
	</form>
</fieldset>

	
<fieldset>
	<legend>Saved Rules</legend>
</fieldset>



</body>
</html>