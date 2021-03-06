<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>基于Layui及SSM的Web项目</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css"  media="all">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">基于Layui及SSM的Web项目</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">控制台</a></li>
      <li class="layui-nav-item"><a href="">商品管理</a></li>
      <li class="layui-nav-item"><a href="">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          admin
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/adm/loginPage">退出</a></li>

    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">系统管理</a>
          <dl class="layui-nav-child">
            <dd><a target="mains" href="${pageContext.request.contextPath}/user/list">用户管理</a></dd>
            <dd><a target="mains" href="${pageContext.request.contextPath}/product/list">商品管理</a></dd>
            <dd><a target="mains" href="${pageContext.request.contextPath}/inventory/list">库存管理</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">销售管理</a>
          <dl class="layui-nav-child">
            <dd><a target="mains" href="${pageContext.request.contextPath}/sales/Flist">零售</a></dd>
            <dd><a target="mains" href="${pageContext.request.contextPath}/sales/Slist">批发</a></dd>
            <dd><a target="mains" href="${pageContext.request.contextPath}/sales/Tlist">福利</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;" height="100%">
    	<iframe name="mains" allowtransparency="true" border="0" frameborder="0" width="100%" height="900px"></iframe>
    </div>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
   	 版权所有
  </div>
</div>
<script src="${pageContext.request.contextPath}/layui.js"></script>
<script src="${pageContext.request.contextPath}/jquery.js"></script>
<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
});
</script>
</body>
</html>