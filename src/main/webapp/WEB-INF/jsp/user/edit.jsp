<%--
  Created by IntelliJ IDEA.
  User: kongyunhui
  Date: 2016/12/13
  Time: 下午4:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tag.jsp" %>
<html>
<head>
    <title>editUser</title>
    <%@ include file="../common/head.jsp" %>
</head>
<body>
<c:if test="${msg != null}">
    <p style="color: red;">${msg}</p>
</c:if>

<!-- [spring] form action默认重定向之前的url：/user/create或者/user/update -->
<form:form method="post" commandName="user">
    <c:if test="${op == '修改'}">
        <form:hidden path="id"/>
    </c:if>

    <div class="form-group">
        <form:label path="username">用户名：</form:label>
        <form:input path="username"/>
    </div>

    <c:if test="${op eq '新增'}">
        <div class="form-group">
            <form:label path="password">密码：</form:label>
            <form:password path="password"/>
        </div>
    </c:if>

    <div class="form-group">
        <form:label path="roleIds">角色列表：</form:label>
        <form:select path="roleIds" items="${roleList}" itemLabel="description" itemValue="id" multiple="true"/>
        (按住shift键多选)
    </div>

    <form:button>${op}</form:button>
</form:form>
</body>
</html>
