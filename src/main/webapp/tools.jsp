<%@ page contentType="text/html;charset=gb2312" %>
<head>
    <meta charset="UTF-8">
    <title>Tools</title>
</head>
<body>
<table border="1px" cellspacing="0px">
    <tr>
        <th colspan="2">工具</th>
    </tr>
    <tr>
        <th>名称</th>
        <th>说明</th>
    </tr>
    <c:forEach items="${brands}" var="brand">
        <tr>
            <td>${brand.brandname}</td>
            <td><fmd:formatDate value="${brand.createtime}" pattern="yyyy-HH-mm"></fmd:formatDate></td>
            <td>
                <a class="btn btn-info" href=" " title="修改"><i class="fa fa-pencil"></i></ a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/brand/deleteBrand?id=${brand.id}"
                   title="删除"><i class="fa fa-trash-o"></i></ a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
