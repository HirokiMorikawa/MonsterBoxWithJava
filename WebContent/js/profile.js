function Profile(url) {
	var url = url;
	var p = Profile.prototype;
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

	// サーバとの通信を行い，profileページのレンダリングをおこなう
	p.setProfilePage = function(call) {
		get(call)
	};

}

function setProfileBox(id, json) {
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
	var profile = new Profile("./profile");
	profile.setProfilePage(function(json) {
		var userBox = document.getElementById("user");
		userBox.innerHTML = "<li> ユーザID：" + json.userid + "</li>";
		setProfileBox("upload", json.uploadHistory);
		setProfileBox("watch", json.watchHistory);
		setProfileBox("favorite", json.favoriteList);
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