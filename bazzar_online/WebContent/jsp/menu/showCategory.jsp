<%@include file="../../tags/taglib_includes.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="App.Title"></spring:message> </title>
<script type="text/javascript" src="js/contacts.js"></script>
</head>
<body style="font-family: Arial; font-size:smaller;">
	
	<form action="searchContacts.do" method="post">		
			<table style="border-collapse: collapse;" style="border:0" style="border-color: 006" style="width: 500">
			<tr>
				<td>Enter Contact Name</td> 
				<td><input type="text" name="name"/>
					&nbsp;&nbsp;<input type="submit" value="Search"/>
					&nbsp;&nbsp;<input type="button" value="New Contact" onclick="javascript:go('saveContact.do');"/>
			</td></tr>
		</table>
	</form>
	
	<table style="border-collapse: collapse;" style="border:1" style="border-color: 006" style="width: 500">
		<tr bgcolor="lightblue">
			<th>Id</th>
			<th>Name</th>			
			<th>Address</th>	
			<th></th>
		</tr>
		<c:if test="${empty SEARCH_CATEGORY_RESULTS_KEY}">
		<tr>
			<td colspan="4">No Results found</td>
		</tr>
		</c:if>
		<c:if test="${! empty SEARCH_CATEGORY_RESULTS_KEY}">
			<c:forEach var="contact" items="${SEARCH_CATEGORY_RESULTS_KEY}">		
		    <tr>
		    	<td><c:out value="${contact.id}"></c:out></td>
				<td><c:out value="${contact.value}"></c:out></td>
				<c:if test="${ empty SEARCH_SUBCATEGORY_RESULTS_KEY}}">
					<td>No Results found</td>
				</c:if>
				<c:if test="${! empty SEARCH_SUBCATEGORY_RESULTS_KEY}}">
					<c:forEach var="sub" items="${SEARCH_SUBCATEGORY_RESULTS_KEY}">
						<td><c:out value="${sub.value}"></c:out></td>
					</c:forEach>
					
				</c:if>

				<td>
					&nbsp;<a href="updateContact.do?id=${contact.id}">Edit</a>
					&nbsp;&nbsp;<a href="javascript:deleteContact('deleteContact.do?id=${contact.attribute}');">Delete</a>
				</td>
			</tr>
			</c:forEach>
		</c:if>				
	</table>	

		
</body>
</html>