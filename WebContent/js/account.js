/**
 * アカウントの存在チェックを行うJavaScript
 *
 */

function account(elements,data) {
	var xmlHttpRequest = new XMLHttpRequest()
	var query = "";
	xmlHttpRequest.onreadystatechange = function() {
		recieveResult(xmlHttpRequest, elements)
	};
	xmlHttpRequest.open("POST", elements["url"], true);
	xmlHttpRequest.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	for (key in data) {
		query += key + "=" + data[key] + "&";
	}
	query = query.slice(0, -1);
	xmlHttpRequest.send(query);
}

function recieveResult(xmlHttpRequest, elements) {
	var result = elements["result"];
	if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		result.style.color = "red";
		result.textContent = response.result;
	}
}