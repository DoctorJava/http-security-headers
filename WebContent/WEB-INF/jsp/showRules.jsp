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
	<legend>Active Policy: ${policy.name}</legend>
	<table>
		<tr>
			<th></th><th>Header</th><th>Required</th><th>Contains</th><th>References</th>
		</tr>
		<c:forEach var="rule" items="${policy.rules}">
			<tr>
				<td>

				</td>
				<td>${rule.name}</td>
				<td>${rule.required}</td>
				<td>
					<c:if test="${rule.contains != null}">${rule.contains}</c:if>
					<c:if test="${rule.containsAny != null}">Any: ${rule.containsAny}</c:if>
					<c:if test="${rule.containsAll != null}">All: ${rule.containsAll}</c:if>
				</td>
				<td>
					<c:forEach var="ref" items="${rule.references}">
						<a href="${ref.url}" target="_blank">${ref.title}</a> | 
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table>
</fieldset>

<fieldset>
	<legend>Saved Policies</legend>
	<form action="MaintainRules" method=POST>
		<button type="submit">Save Rules</button>
	</form>
</fieldset>

	
<fieldset>
	<legend>Saved Rules</legend>
</fieldset>



</body>
</html>