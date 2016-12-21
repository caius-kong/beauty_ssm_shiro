<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp"%>
<html>
<head>
    <title>beauty_ssm_shiro案例</title>
    <%@ include file="common/head.jsp"%>
    <link rel="stylesheet" href="<%=path %>/static/css/index.css">
</head>
<body>

<div id="head">
    欢迎<shiro:principal/>，<a href="<%=path %>/logout">退出</a>
</div>
<div id="body">
    <div id="nav">
        <ul>
            <c:forEach items="${menus}" var="m">
                <li><a href="<%=path %>${m.url}" target="content">${m.name}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div id="main">
        <iframe name="content" width="800" height="600" src="<%=path %>/welcome" frameborder="0" scrolling="auto"></iframe>
    </div>
</div>
</body>
</html>