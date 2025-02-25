<%
   String csspath = request.getContextPath() + "/netmarkets/css";
   //String jspath = request.getContextPath() + "/netmarkets/javascript/com/wincomplm/wex/delivery/files/docker.js"; 
   //System.out.println(jspath);
%>
<html>
    <head>
        <title>Docker Control</title>
        <link href="<%=csspath%>/com/wincomplm/wex/delivery/files/css.css" rel="stylesheet" type="text/css">
        <%@ include file="/netmarkets/jsp/com/wincomplm/wex/delivery/files/scriptDocker.jspf"%>  

    </head>
    <body>


        <h1>Server Response</h1>
        <textarea id="result-area" style="resize: both; width: 500px; height: 50px;"></textarea>
        <div>
            <!--        <h3>Send docker compose flag (Up or Down) </h3>-->
            <%-- <input type="text" id="action"/> --%>
            <button class="dockerUp" onclick="callDockerCaller('up')">Docker Up</button>
            <button class="dockerDown" onclick="callDockerCaller('down')">Docker Down</button>
            <button id = "brwButton" class="browser" onclick="openLocalhost()">Open Localhost</button>
        </div>

    </body>
</html>