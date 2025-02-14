<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.wincomplm.wex.kernel.api.invoke.WexInvoker"%>
<html>
    <head>
        <script>
            function callDockerComposeMethod(action) {
                const url = "${urlFactory.baseHREF}netmarkets/jsp/com/wincomplm/wex/delivery/files/edkDockerCaller.jsp";

                if (action) {
                    const body = new URLSearchParams({ data: action }); // Match 'data' parameter in backend

                    fetch(url, {
                        method: 'POST',
                        body: body,
                        headers: { "Content-Type": "application/x-www-form-urlencoded" }
                    })
                    .then(response => response.json()) // Expecting a JSON response
                    .then(json => {
                        if (json && json.text) {
                            document.getElementById("result").innerHTML = json.text;
                        } else {
                            document.getElementById("result").innerHTML = "Unexpected response from server";
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        document.getElementById("result").innerHTML = "FAIL";
                    });
                }
            }
        </script>
    </head>
    <body style="font-family: Arial, Helvetica, sans-serif;">
        <button onclick="callDockerComposeMethod('up')">UP</button>
        <button onclick="callDockerComposeMethod('down')">DOWN</button>
        <div id="result"></div>
    </body>
</html>
