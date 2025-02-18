<html>
    <body>
        <h1>Server Response</h1>
        <textarea id="result-area" style="resize: both; width: 500px; height: 200px;"></textarea>

        <div>
            <h3>Send docker compose flag (Up or Down) </h3>
            <%-- <input type="text" id="action"/> --%>
            <button id="dockerUp" onclick="callDockerCaller('up')">Docker Up</button>
            <button id="dockerDown" onclick="callDockerCaller('down')">Docker Down</button>
            <button id="browser" onclick="window.open(window.location.protocol + '//' + window.location.hostname + ':8080')">Open Localhost</button>
        </div>
        <script type="text/javascript">
            
            function callDockerCaller(action) {
                console.log("click");
                const txtArea = document.getElementById("result-area")
                const urlLocal = window.location.protocol + '//' + window.location.hostname + ':8080'
                var url = "${urlFactory.baseHREF}netmarkets/jsp/com/wincomplm/wex/delivery/files/runDockerCall.jsp";
                var myWindow;
                var body = {
                    action: action
                };

                txtArea.value = "Processing...";

                fetch(url, {
                    method: "POST",
                    body: JSON.stringify(body)
                })
                        .then(response => response.json())
                        .then(data => {
                            txtArea.value = JSON.stringify(data, null, 4);
                            if (data.data.received === "up") {
                                setTimeout(() => {
                                    myWindow = window.open(urlLocal);
                                }, 5000); 
                                txtArea.value = "Starting containers...";
                            }
                        }
                        )
                        .catch(error => {
                            console.error("Error:", error)
                            txtArea.value = "Error: " + error.message;
                        });
            }
        </script>
    </body>
</html>