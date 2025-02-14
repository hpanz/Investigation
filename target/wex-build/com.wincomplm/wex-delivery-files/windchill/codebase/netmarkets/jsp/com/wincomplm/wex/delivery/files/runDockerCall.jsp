<%@ page import="com.wincomplm.wex.kernel.api.invoke.WexInvoker" %>

<%  
    WexInvoker.invoke("com.wincomplm.wex-delivery-files", "methods.docker-caller", request, response);
%>