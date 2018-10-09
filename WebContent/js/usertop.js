function UserTop(url) {
	var url = url;

	var p = UserTop.prototype;
	var xmlHttpRequest = null;

	// getリクエストをおこなう
	var get = function(call) {
		xmlHttpRequest = new XMLHttpRequest();
		xmlHttpRequest.onreadystatechange = function() {
			if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				var response = JSON.parse(xmlHttpRequest.responseText);
				// コールバック関数による終端操作
				call(response);
			}
		}
		xmlHttpRequest.open("GET", url, true);
		xmlHttpRequest.send(null);
	};

	/*
	 * ユーザプロファイルの取得
	 */
	p.getPreUserInfo = function(call) {
		get(call);
	};
}

function setUserTopPage(id, json) {
	var box = document.getElementById(id);
	for (var i = 0; i < json.length; i++) {
		var div = document.createElement("div");
		div.setAttribute("class", "f_item");
		var img = document.createElement("img");
		if (json[i].img == "false") {
			img.setAttribute("src", "./../img/" + json[i].thumbnail);
		} else {
			img.setAttribute("src", "./DorT?param=T&userid=" + json[i].userid + "&filename=" + json[i].filename);
		}
		var a = document.createElement("a");

		var userid = json[i].userid;
		var filename = json[i].filename;

		linkClickEvent(a, userid, filename);

		a.setAttribute("href", "./details.html");
		a.innerHTML = json[i].title + "<br>" + json[i].filename;
		div.appendChild(img);
		div.appendChild(a);
		box.appendChild(div);
	}
}

window.addEventListener("load", function() {
	var t = new UserTop("./info");
	t.getPreUserInfo(function(json) {
		var cardBox = document.getElementsByClassName("card");
		var userBox = document.getElementById("user");
		var img = document.createElement("img");
		var a = document.createElement("a");
		var li = document.createElement("li");

		a.setAttribute("href", "./profile.html");
		a.textContent = "ユーザID：" + json.userid;

		li.appendChild(a);

		a = document.createElement("a");
		a.setAttribute("href", "./profile.html");

		img.setAttribute("class", "avatar");
		img.setAttribute("src", "./../img/" + json.nowAvatar);
		// img.setAttribute("height", "245");
		userBox.appendChild(li);
		a.appendChild(img);
		cardBox[0].insertBefore(a, userBox);

		setUserTopPage("new", json.fileList);
		setUserTopPage("ranking", json.rankingList);

	});
}, false);

/**
 * クリックイベントの登録を行い クッキーにアクセス先の情報を記録する
 */
function linkClickEvent(link, userid, filename) {
	link.addEventListener("click", function() {
		setCookie("userid=" + encodeURIComponent(userid));
		setCookie("filename=" + encodeURIComponent(filename));
	}, false);
}