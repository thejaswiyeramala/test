<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display All Userstories</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div align=center>
	<h2>Userstories</h2>
	<table border="1" cellpadding="5">
        <tr>
            <th>userstoryID</th>
            <th>appName</th>
            <th>description</th>
            <th>domain</th>
            <th>duration</th>
        </tr>
       
        <c:forEach var="UserStory" items="${UserStory}">
            <tr>
                <td style="text-align:center"><c:out value="${UserStory.userstoryID}"/></td>
                <td style="text-align:center"><c:out value="${UserStory.appName}" /></td>
                <td style="text-align:center"><c:out value="${UserStory.description}" /></td>
                <td style="text-align:center"><c:out value="${UserStory.domain}" /></td>
                <td style="text-align:center"><c:out value="${UserStory.duration}" /></td>
            </tr>
        </c:forEach>
	</table>
	<br>
	<br>
		<div>
			<input type="button" value="Return to Home" onclick="location.href='http://localhost:8080/home.jsp';">
		</div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>