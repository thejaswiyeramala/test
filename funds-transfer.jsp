<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funds Transfer</title>
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Rubik&display=swap');
	body {
		background-color:cornsilk;
	}
	
	h2 {
		font-family:'Rubik', sans-serif;
		font-size: 20px;
		color:blue;	
	}
	h3 {
		font-family:'Rubik', sans-serif;
		font-size: 16px;	
	}
	
	h4 {
		font-family:'Rubik', sans-serif;
		font-size: 12px;	
	}
		
	p{
		font-family:'Rubik', sans-serif;
		font-size: 14px;
	}
	
	table, th, td {
	  border: 1px solid black;
	  table-layout: fixed;
	  width: 40%; 
	}
	
	.error{
		color:red;
		font-size:16px;
	}
	
	/* Fixed sidenav, full height */
	.sidenav {
	  margin-top:150px;
	  margin-left: 8px;
	
	  font-family: "Lato", sans-serif;
	  height: 60%;
	  width: 200px;
	  position: fixed;
	  z-index: 1;
	  top: 0;
	  left: 0;
	  background-color: #111;
	  overflow-x: hidden;
	  padding-top: 20px;
	}
	
	/* Style the sidenav links and the dropdown button */
	.sidenav a {
	  padding: 6px 8px 6px 16px;
	  text-decoration: none;
	  font-size: 20px;
	  color: #818181;
	  display: block;
	  border: none;
	  background: none;
	  width: 100%;
	  text-align: left;
	  cursor: pointer;
	  outline: none;
	}
	
	/* On mouse-over */
	.sidenav a:hover {
	  color: #f1f1f1;
	}
	
	/* Main content */
	.main {
	  margin-left: 200px; /* Same as the width of the sidenav */
	  font-size: 20px; /* Increased text to enable scrolling */
	  padding: 0px 10px;
	}
	
	/* Some media queries for responsiveness */
	@media screen and (max-height: 450px) {
	  .sidenav {padding-top: 15px;}
	  .sidenav a {font-size: 18px;}
	}
	
	/* Style the main content links */
	.main a {
	  color: darkgreen;
	  text-decoration: none;
	  font-family:'Rubik', sans-serif;
	  font-size: 16px;
	  line-height: 2;
	 }
	 
	/* On mouse-over */
	.main a:hover {
	  color: darkblue;
	}
	label {
		font-family:'Rubik', sans-serif;
		font-size: 14px;
		font-weight:600;	
	}
	
	#btnSubmit{
		background-color: burlywood;
        color:black;
        border-width: medium;
        padding: 2px 24px;
        border-radius: 4px;
        font-family:'Rubik', sans-serif;
        font-size: 14px;
        font-weight: 500;
        margin: 4px 2px;
        cursor: pointer;
	}
</style>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<!-- Side Navigation -->
	<div class="sidenav">
		<a href="${pageContext.request.contextPath}/customer/home">Home</a>
		<a href="${pageContext.request.contextPath}/customer/acct-summary">Account Summary</a>
		<a href="${pageContext.request.contextPath}/customer/loansManagement">Loans</a>
		<a href="${pageContext.request.contextPath}/customer/cardManagement">Cards Management</a>
		<a href="${pageContext.request.contextPath}/customer/beneficiaryhome">Beneficiaries</a>	
		<a href="${pageContext.request.contextPath}/customer/service-prov">Service Provider</a>
		<a href="${pageContext.request.contextPath}/customer/ibslogin">Logout</a>	</div>
<div class="main">
	<h2>Transfer Your Funds</h2>
	<hr/>
	<spring:form action="${pageContext.request.contextPath}/customer/transfer-funds" id="Transfer_Funds_form" method="post" modelAttribute="fundsTransfer">
		<div><spring:label path="acctNum">Account Number</spring:label></div>
	  		<div>
	  			<spring:input type="number" path="acctNum" name="Account number"/>
	  			<div><spring:errors path="acctNum" cssClass="error"/></div>
	  		</div>
	  	<br/>
	  	<div><spring:label for="select bill type" path="billType">Select Bill Type</spring:label></div>
	  		<div>
	  			<spring:select name="select bill type" path="billType" id="billtype">
				  <spring:option value="Funds Transfer">Funds Transfer</spring:option>
				</spring:select>
				<div><spring:errors path="billType" cssClass="error"/></div>
	  		</div>
	  		<br/>
		<div><spring:label for="select beneficiary" path="beneficiary">Select a Beneficiary</spring:label></div>
			<div>
				<spring:select name="select beneficiary" path="beneficiary" id="select beneficiary">
				  <spring:options items="${fundsTransfer.beneficiary}"/>
				  <%--
				  <spring:option value="XX">XX</spring:option>
				  <spring:option value="YY">YY</spring:option>
				  <spring:option value="ZZ">ZZ</spring:option>
				  --%>
				</spring:select>
				<div><spring:errors path="beneficiary" cssClass="error"/></div>
			</div>
		<br/>
		<div><spring:label path="transferAmount">Amount</spring:label></div>
			<div>
				<spring:input type="number" path="transferAmount" name="Amount"/>
				<div><spring:errors path="transferAmount" cssClass="error"/></div>
			</div>
		<br/>
		<div><spring:label path="transferDescription">Description</spring:label></div>
			<div>
				<spring:input type="text" path="transferDescription" name="Description"/>
				<div><spring:errors path="transferDescription" cssClass="error"/></div>
			</div>
		<br/>
		<input id="btnSubmit" type="submit" value="Submit"/>
		&nbsp;
		<input id="btnSubmit" type="button" value="Cancel" onclick="location.href='http://localhost:8080/ibs-bootappsecure/customer/acct-summary';">
	</spring:form>
</div>
<hr/>
<jsp:include page="footer.jsp"/>
</body>
</html>