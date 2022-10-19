<%@ page import="com.st.studentregistration.models.message.Message" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Admin-Login</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/">Admin Login</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>
<%
    Message msg = (Message) request.getAttribute("msg");
    if (msg!= null) {%>
<div class="alert mt-1 alert-<%=msg.getMsgFlag()%> alert-dismissible fade show" role="alert">
    <strong><%=msg.getMsgTitle()%></strong><%=" "+msg.getMsgDescription()%>
    <button type="button" id = "alert-close-btn" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<%}%>

<div class="container w-50 mb-4">
    <h3 class="mt-4">Admin Login</h3>
    <form action = "<%=request.getContextPath()%>/auth/login" method = "post" >
        <div class="mb-3">
            <label for="student-email" class="form-label">Email address</label>
            <input type="email" name = "email" class="form-control form-control-sm" id="student-email" aria-describedby="emailHelp">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" name = "password" class="form-control form-control-sm" id="password">
        </div>
        <button type="submit" class="btn btn-primary">login</button>
    </form>
</div>
<script>
    <%
     if(request.getAttribute("msg")!=null){%>
    let alertCloseButton =document.getElementById("alert-close-btn");
    const interval = setInterval(()=>{
        alertCloseButton.click();
        clearInterval(interval);
    },3000);
    <%}%>
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>