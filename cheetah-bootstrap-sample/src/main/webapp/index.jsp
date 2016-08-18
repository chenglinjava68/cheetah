<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
<c:set var="name" value="yu"/>

<h2>Hello World!${name}</h2>
<c:if test="${name == 'yu'}">
    <h2>不为空</h2>
</c:if>
</body>
</html>
