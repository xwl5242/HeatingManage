<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp"></jsp:include>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                	<!-- 页面头像处功能 -->
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" src="<%=path %>/static/Hplus/img/profile_small.jpg" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${sessionScope.user.userCode }</strong></span>
                                <span class="text-muted text-xs block">${userName }<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_menuItem" href="<%=path %>/static/Hplus/form_avatar.html">修改头像</a>
                                </li>
                                <li><a class="J_menuItem" href="<%=path %>/static/Hplus/profile.html">个人资料</a>
                                </li>
                                <li><a class="J_menuItem" href="<%=path %>/static/Hplus/contacts.html">联系我们</a>
                                </li>
                                <li><a class="J_menuItem" href="<%=path %>/static/Hplus/mailbox.html">信箱</a>
                                </li>
                                <li class="divider"></li>
                                <li><a href="<%=path %>/loginOut">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">xx
                        </div>
                    </li>
                    
                    <!-- 页面左侧菜单 -->
					<c:set var="rightList" value="${sessionScope.right }"></c:set>
                    <c:forEach items="${rightList }" var="right">
                   		<li>
                   			<a href="#">
	                            <i class="fa fa-home"></i>
	                            <span class="nav-label">${right.right_name }</span>
	                            <span class="fa arrow"></span>
	                        </a>
                    		<c:choose>
                    			<c:when test="${not empty right.childNode}">
                    				<ul class="nav nav-second-level">
                    					<c:forEach var="cright" items="${right.childNode }" >
                    						<li>
			                                	<c:choose>
                   									<c:when test="${not empty cright.childNode}">
						                                <a href="#">${cright.right_name }<span class="fa arrow"></span></a>
						                                <ul class="nav nav-third-level">
						                                	<c:forEach var="ccright" items="${ cright.childNode}">
							                                    <li><a class="J_menuItem" href="<%=path %>/${ccright.right_url}">${ccright.right_name }</a>
							                                    </li>
						                                	</c:forEach>
					                                    </ul>
                   									</c:when>
                   									<c:otherwise>
                   										<li>
							                                <a class="J_menuItem" href="<%=path %>/${cright.right_url}">${cright.right_name }</a>
							                            </li>
                   									</c:otherwise>
                   								</c:choose>
				                            </li>
                    					</c:forEach>
                    				</ul>
                    			</c:when>
                    		</c:choose>
                   		</li>
                    </c:forEach>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            
            <!-- 页面顶部的tab -->
            <jsp:include page="tab.jsp"></jsp:include>
            
            <!-- 右侧主页面 -->
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="<%=path %>/static/Hplus/index_v1.html?v=4.0" frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
            
            <!-- 页面底部 -->
            <div class="footer">
                <div class="pull-right">&copy; 2014-2015 <a href="http://www.zi-han.net/" target="_blank"></a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
        
        <!--主题设置-->
        <div id="right-sidebar">
            <div class="sidebar-container">

                <ul class="nav nav-tabs navs-3">

                    <li class="active">
                        <a data-toggle="tab" href="#tab-1">
                            <i class="fa fa-gear"></i> 主题
                        </a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="tab-1" class="tab-pane active">
                        <div class="sidebar-title">
                            <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                            <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                        </div>
                        <div class="skin-setttings">
                            <div class="title">主题设置</div>
                            <div class="setings-item">
                                <span>收起左侧菜单</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                        <label class="onoffswitch-label" for="collapsemenu">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>固定顶部</span>

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                        <label class="onoffswitch-label" for="fixednavbar">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>
                        固定宽度
                    </span>

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                        <label class="onoffswitch-label" for="boxedlayout">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="title">皮肤选择</div>
                            <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                            </div>
                            <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                            </div>
                            <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</body>
</html>
