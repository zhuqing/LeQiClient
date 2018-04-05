<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<liferay-theme:defineObjects />
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path + "/";
  String currentNav = "";//themeDisplay.getURLCurrent();
  String webType = "liferay";
  String currentNav = themeDisplay.getURLCurrent();
	long userId = themeDisplay.getUserId();
%>
<html>
    <head>
        <title>医疗信息平台</title>
        <SCRIPT src="<%=basePath%>/web-files/dtjava.js">
            
        </SCRIPT>
        <style>
            /* #dataapp-app, */
            html, body, #javafx-app-placeholder, #dataapp-app, #progress, #car {  
                margin: 0;  
                overflow: auto;  
                padding: 0; 
                width: 100%;
                height: 100%; 
            }
            html {
                background-color: #3f4346;
                background-image: url("background.jpg");
                background-size: 100% 100%;
            }
            body {
                background-image: url("noise.png");
                background-repeat: repeat;
            }
            #dataapp-splash {
                background-color: transparent !important;
            }
            #javafx-app-placeholder {
                background-image: url("title.png");
                background-position: 19px 15px;
                background-repeat: no-repeat;
            }
            #progress {
                background-image: url("progress.gif");
                background-position: center;
                background-repeat: no-repeat;
            }
            #car {
                background-image: url("car.png");
                background-position: center;
                background-repeat: no-repeat;
            }
        </style>
        <script>
        	// 获取当前业务导航
					function getWebType() {
						return "<%=webType %>";
					}
					// 获取当前业务导航
					function getCurrentNav() {
						return "<%=currentNav %>";
					}
					// 获取当前登陆用户的UserId
					function getLoginUserId() {
						return  "<%=userId %>";
					}
					function getSplash(app) {
				        var progress = document.createElement('div');
				        progress.id = "progress";
				        var car = document.createElement('div');
				        car.id = "car";
				        progress.appendChild(car);
				        return progress;
				    }
					// JWS方式启动
					function launchApplication(jnlpfile) {
					        dtjava.launch(            {
					                url : '<%=basePath %>HipApp.jnlp'
					            },
					            {
					                javafx : '2.2+'
					            },
					            {}
					        );
					        return false;
					    }
					//  javafx嵌入参数设置
					function javafxEmbed() {
					        dtjava.embed(
					            {
					                url : '<%=basePath %>HipApp.jnlp',
					                placeholder : 'javafx-app-placeholder',
					                width : '100%',
					                height : 380
					            },
					            {
					                javafx : '2.2+'
					            },
					            {
					            	onGetSplash : getSplash
					            	}
					        );
					    }
					    dtjava.addOnloadCallback(javafxEmbed);
	</script>
    </head>
    <body>
    	<div id='javafx-app-placeholder'></div>
    	</body>
</html>