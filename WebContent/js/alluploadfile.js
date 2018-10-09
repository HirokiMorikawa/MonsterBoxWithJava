window.addEventListener("load", function() {
	var o = new AllUploadFile("./view");
	o.request(document.getElementById("view"));
}, false);

/**
 * 全てのユーザがアップロードしたファイルのリストを取得するためのjavascript
 */
function AllUploadFile(url) {
	this.url = url;
}

AllUploadFile.prototype.request = function(e) {
	var xmlHttpRequest = new XMLHttpRequest();

	// ajaxの状態基づいた処理をおこなう
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
			var response = JSON.parse(xmlHttpRequest.responseText);
			for (var i = 0; i < response.length; i++) {
				var div = document.createElement("div");
				div.setAttribute("class", "f_item");
				var img = document.createElement("img");
				if (response[i].img == "false") {
					img.setAttribute("src", "./../img/" + response[i].thumbnail);
				} else {
					img.setAttribute("src", "./DorT?param=T&userid=" + response[i].userid + "&filename=" + response[i].filename);
				}
				var a = document.createElement("a");

				linkClickEvent(a, response[i].userid, response[i].filename);

				a.setAttribute("href", "./details.html");
				a.innerHTML = response[i].title + "<br>" + response[i].userid;
				div.appendChild(img);
				div.appendChild(a);
				e.appendChild(div);
			}
		}
	}
	xmlHttpRequest.open("GET", this.url, true);
	xmlHttpRequest.send(null);
};

/**
 * クリックイベントの登録を行い クッキーにアクセス先の情報を記録する
 */
function linkClickEvent(link, userid, filename) {
	link.addEventListener("click", function() {
		setCookie("userid=" + encodeURIComponent(userid));
		setCookie("filename=" + encodeURIComponent(filename));
	}, false);
}
