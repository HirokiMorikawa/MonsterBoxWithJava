var xmlHttpRequest;
/**
 * avatar設定
 */
function sendWithGetMethod() {
	var nowAvatarNumElement = document.getElementById("nowAvatarNum");// 設定するアバター番号入力
	var url = "avatarSet?nowAvatarNum=" + nowAvatarNumElement.value;
	xmlHttpRequest = new XMLHttpRequest;
	xmlHttpRequest.onreadystatechange = receive;
	xmlHttpRequest.open("GET", url, true);
	xmlHttpRequest.send(null);
	// alert("sendTes");
}
/**
 * ユーザプロファイルに登録されているアバターの情報を取得する
 *
 * @returns
 */
function getWithGetMethod() {
	var url = "avatarSet?nowAvatarNum=";
	xmlHttpRequest = new XMLHttpRequest;
	xmlHttpRequest.onreadystatechange = receive;
	xmlHttpRequest.open("GET", url, true);
	xmlHttpRequest.send(null);
}

function receive() {
	if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		var nowAvatarElement = document.getElementById("nowAvatar");
		nowAvatarElement.setAttribute("src", "./../img/" + response.nowAvatar);
		// alert("アバターを設定しました");
		// alert("receiveTes");
	}
}
window.addEventListener("load", function() {
	getWithGetMethod();
	var nowAvatarNumElement = document.getElementById("nowAvatarNum");
	nowAvatarNumElement.addEventListener("change", sendWithGetMethod, false);
}, false);
