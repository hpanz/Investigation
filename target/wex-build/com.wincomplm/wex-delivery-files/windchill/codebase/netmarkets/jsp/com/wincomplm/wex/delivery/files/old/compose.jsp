<%@ page import="com.wincomplm.wex.kernel.api.invoke.WexInvoker" %>
<%@ page import="java.util.logging.Logger" %>

<%  
    Logger logger = Logger.getLogger("MyJSPLogger"); // Create a logger
    String result = (String) WexInvoker.invoke("com.wincomplm.wex-delivery-files", "docker-caller.compose", request, response);

    logger.info("Invocation Result: " + result); // Log the result to server logs
    System.out.println("Invocation Result: " + result); // Log to console (stdout)

    out.println(result); // Output to the browser
%>
