<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>KYC Confirmation</title>
<style>
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
		<a href="${pageContext.request.contextPath}/admin/home">Home</a>
		<a href="${pageContext.request.contextPath}/admin/getkyc-details">KYC Details</a>
		<a href="${pageContext.request.contextPath}/admin/cardsManagement">Cards Management</a>
		<a href="${pageContext.request.contextPath}/admin/loansManagement">Loans</a>
		<a href="${pageContext.request.contextPath}/admin/getsp-details">Service Provider</a>
		<a href="${pageContext.request.contextPath}/customer/ibslogin">Logout</a>
	</div>

<!-- Main Content -->
<div class="main" align=center>
	<h2 id="successMsg">KYC Declined!</h2>
	<hr/>
	<h4 id="detailsLabel">KYC Details:</h4>
	<h3>KYC ID: ${kycDetails.kycId}</h3>
	<h3>First Name: ${kycDetails.firstName}</h3>
	<h3>Last Name: ${kycDetails.lastName}</h3>
	<h3>Contact Number: ${kycDetails.contactNumber}</h3>
	<h3>Email ID: ${kycDetails.emailID}</h3>
	<h3>National ID Type: ${kycDetails.nationalIDType}</h3>
	<h3>National ID Number: ${kycDetails.nationalIDNum}</h3>
	<h3>KYC Status: ${kycDetails.kycStatus}</h3>
</div>
<hr/>
<jsp:include page="footer.jsp"/>
</body>
</html>