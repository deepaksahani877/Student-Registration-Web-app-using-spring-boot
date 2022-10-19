<%@ page import="java.util.ArrayList" %>
<%@ page import="com.st.studentregistration.models.users.Student" %>
<%@ page import="com.st.studentregistration.models.message.Message" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>StudentRegistration-Home</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%=request.getContextPath()%>">Student Registration</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="<%=request.getContextPath()%>/auth/registration">Registration</a>
                </li>
            </ul>
            <a href="<%=request.getContextPath()%>/auth/logout">
                <button class="btn  btn-success" type="submit">Log out</button>
            </a>
        </div>
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

<div class="container w-75 mt-4">
    <div class="modal" tabindex="-1" id="updateModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Update</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="<%=request.getContextPath()%>/update" method="post">
                        <div class="mb-3">
                            <label for="student-name" class="form-label">Student Name</label>
                            <input type="text" name="name" class="form-control form-control-sm" id="student-name"
                                   aria-describedby="emailHelp">
                        </div>
                        <div class="mb-3">
                            <label for="city-name" class="form-label">City</label>
                            <input type="text" name="city" class="form-control form-control-sm" id="city-name"
                                   aria-describedby="emailHelp">
                        </div>
                        <div class="mb-3">
                            <label for="student-email" class="form-label">Email address</label>
                            <input type="email" name="email" class="form-control form-control-sm" id="student-email"
                                   aria-describedby="emailHelp">
                        </div>
                        <div class="mb-3">
                            <label for="student-phone" class="form-label">Phone number</label>
                            <input type="number" name="phone" class="form-control form-control-sm" id="student-phone">
                        </div>
                        <div hidden class="mb-3">
                            <label for="prevMail" class="form-label">Current email</label>
                            <input type="email" name="currentEmail" class="form-control form-control-sm" id="prevMail">
                        </div>
                        <button type="submit" hidden id="updateBtn" class="btn btn-primary">Update</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="modalCloseBtn" type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close
                    </button>
                    <button id="saveChangesBtn" type="submit" class="btn btn-success">Save changes</button>
                </div>
            </div>
        </div>
    </div>
    <h3>All Student List</h3>
    <hr>
    <div class="table-responsive">
       <table class="table table-success table-striped">
        <thead>
        <tr>
            <th scope="col">Sno</th>
            <th scope="col">Name</th>
            <th scope="col">City</th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <%
            @SuppressWarnings("unchecked")
            ArrayList<Student> students = (ArrayList<Student>) request.getAttribute("studentList");
            int sno = 1;
            for (Student student : students) {%>
        <tr class="tr">
            <th scope="row"><%=sno%>
            </th>
            <td><%=student.getName()%>
            </td>
            <td><%=student.getCity()%>
            </td>
            <td><%=student.getEmail()%>
            </td>
            <td><%=student.getPhone()%>
            </td>
            <td>
                <button id="update" data-index="<%=sno-1%>" type="button" class="btn update btn-outline-success btn-sm"
                        data-bs-toggle="modal" data-bs-target="#updateModal">Update
                </button>
            </td>
            <td>
                <a href = "<%=request.getContextPath()%>/delete?email=<%=student.getEmail()%>"><button type="button" id="delete" class="btn delete btn-outline-danger btn-sm">Delete</button></a>
            </td>
        </tr>
        <% sno++;
        }%>
        </tbody>
        </table>
    </div>
</div>
<script lang="javascript">
    let saveChangesBtn = document.getElementById("saveChangesBtn");
    let deleteBt = document.getElementById("delete");
    let allUpdateBtn = document.querySelectorAll(".update")

    allUpdateBtn.forEach((update) => {
        update.addEventListener("click", () => {
            let index = update.getAttribute("data-index");
            let arr = getAllStudentData();
            let nameField = document.getElementById("student-name");
            let cityField = document.getElementById("city-name");
            let emailField = document.getElementById("student-email");
            let phoneField = document.getElementById("student-phone");
            let prevMail = document.getElementById("prevMail");

            nameField.value = arr[index][0];
            cityField.value = arr[index][1];
            emailField.value = arr[index][2];
            phoneField.value = arr[index][3];
            prevMail.value = arr[index][2];

        })
    })

    function getAllStudentData() {
        let tr = document.querySelectorAll(".tr");
        const arr = [];
        tr.forEach((tr) => {
            let name = tr.children[1].innerHTML.trim();
            let city = tr.children[2].innerHTML.trim();
            let email = tr.children[3].innerHTML.trim();
            let phone = tr.children[4].innerHTML.trim();
            arr.push([name, city, email, phone]);
        })
        return arr;
    }

    saveChangesBtn.addEventListener("click", () => {
        updateBtn();
        closeModal();
    });
    let closeModal = () => {
        let modalCloseBtn = document.getElementById("modalCloseBtn")
        modalCloseBtn.click();
    }
    let updateBtn = () => {
        let updateBtn = document.getElementById("updateBtn")
        updateBtn.click();
    }

    <%
     if(request.getAttribute("msg")!=null){%>
        let alertCloseButton =document.getElementById("alert-close-btn");
        const interval = setInterval(()=>{
            alertCloseButton.click();
            clearInterval(interval);
        },3000);
    <%}%>
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>