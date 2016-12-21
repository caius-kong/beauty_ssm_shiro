<%--
  Created by IntelliJ IDEA.
  User: kongyunhui
  Date: 2016/12/14
  Time: 下午4:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
    <title>修改密码</title>
    <%@ include file="../common/head.jsp"%>
</head>
<body>
    <!-- 不填写action，默认重定向之前的url -->
    <form method="post">
        新密码：<input type="password" name="newPassword" value="${user.password}">
        <input type="submit" value="${op}">
    </form>
</body>
</html>
