<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<html>
<head>
    <title>登录</title>
    <%@ include file="common/head.jsp"%>
    <style>.error{color:red;}</style>
</head>
<body>

<div class="error">
    <c:if test="${not empty param.kickout}">该账号在其他地方登录，您被强制下线！</c:if>
    ${error}
</div>
<form action="" method="post">
    用户名：<input type="text" name="username" value="<shiro:principal/>"><br/>
    密码：<input type="password" name="password"><br/>
    自动登录：<input type="checkbox" name="rememberMe" value="true"><br/>
    <input type="submit" value="登录">
</form>

</body>
</html>