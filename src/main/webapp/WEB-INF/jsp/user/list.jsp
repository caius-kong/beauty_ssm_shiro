<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
    <title>Title</title>
    <%@ include file="../common/head.jsp"%>
</head>
<body>
    <c:if test="${msg != null}">
        <p style="color: red;">${msg}</p>
    </c:if>
    <shiro:hasPermission name="user:create">
        <a href="<%=path%>/user/create">添加用户</a>
    </shiro:hasPermission>
    搜索：<input type="text" name="idOrUsername" id="idOrUsername" onkeydown="findUsers();"/>
    <table border="1">
        <tr>
            <th>用户名</th>
            <th>角色列表</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${userList}" var="u">
            <tr>
                <td>${u.username}</td>
                <td>${zhangfn:roleNames(u.roleIds)}</td>
                <td>
                        <a href="<%=path %>/user/${u.id}/update">修改</a>
                        <a href="<%=path %>/user/${u.id}/delete">删除</a>
                        <a href="<%=path %>/user/${u.id}/changePassword">修改密码</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <script src="<%=path%>/static/js/list.js"></script>
</body>
</html>
