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
	<legend>HTTP Security Header Report</legend>
	<pre>${report}</pre>
</fieldset>
<fieldset>
	<legend>Raw Response Headers</legend>
	<pre>${raw_headers}</pre>
</fieldset>
</body>
</html>