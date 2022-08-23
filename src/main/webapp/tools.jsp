<%@ page contentType="text/html;charset=gb2312" %>
<head>
    <meta charset="UTF-8">
    <title>Tools</title>
</head>
<body>
<table border="1px" cellspacing="0px">
    <tr>
        <th colspan="2">����</th>
    </tr>
    <tr>
        <th>����</th>
        <th>˵��</th>
    </tr>
    <c:forEach items="${brands}" var="brand">
        <tr>
            <td>${brand.brandname}</td>
            <td><fmd:formatDate value="${brand.createtime}" pattern="yyyy-HH-mm"></fmd:formatDate></td>
            <td>
                <a class="btn btn-info" href=" " title="�޸�"><i class="fa fa-pencil"></i></ a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/brand/deleteBrand?id=${brand.id}"
                   title="ɾ��"><i class="fa fa-trash-o"></i></ a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
