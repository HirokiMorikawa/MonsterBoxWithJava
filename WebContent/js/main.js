/*mainメソッド的関数*/
window.addEventListener("load", function() {
	fixMargin();
	if (typeof account == "function") {

		var check = {
			userid : document.getElementById("s_userid"),
			password : document.getElementById("s_password"),
			result : document.getElementById("resultUser"),
			url : "./check"
		};

		var signin = {
			userid : document.getElementById("s_userid"),
			password : document.getElementById("s_password"),
			button : document.getElementById("s_button"),
			result : document.getElementById("resultSignin"),
			url : "./signin"
		};

		console.log("account_checker.jsが読み込まれました");

		if (check["userid"] != null && check["password"] != null
				&& check["result"] != null) {

			check["userid"].addEventListener("keyup", function() {
				account(check, {
					"userid" : check["userid"].value
				});
			}, false);
		}

		if (signin["result"] != null && signin["button"] != null) {
			signin["button"].addEventListener("click", function() {
				account(signin, {
					"userid" : signin["userid"].value,
					"password" : signin["password"].value
				});
			}, false);
		}
	} else {
		console.log("account.jsが読み込まれていません");
	}

	if (typeof modal_window_actor == "function") {

		var modal = {
			open_modal : document.getElementsByClassName("open_modal"),
			float : document.getElementById("float_box"),
			overlay : document.getElementById("modal-overlay")
		}

		console.log("modal_window.jsが読み込まれました");

		if (modal["open_modal"] != null && modal["float"] != null
				&& modal["overlay"] != null) {
			modal_window_actor(modal);
		}
	} else {
		console.log("modal_window.jsが読み込まれていません");
	}
}, false);


function setCookie(param) {
	document.cookie = param;
}

/**
 *
 */
function getCookie(){
	var result = new Array();
	var allcookies = document.cookie;
	if (allcookies != '') {
		var cookies = allcookies.split('; ');
		for (var i = 0; i < cookies.length; i++) {
			var cookie = cookies[i].split('=');
			// クッキーの名前をキーとして 配列に追加する
			result[cookie[0]] = decodeURIComponent(cookie[1]);
		}
	}
	return result;
}


// リサイズ
window.addEventListener("resize", fixMargin, false);

function fixMargin() {
	var nav = document.getElementById("nav");
	var containers = document.getElementsByClassName("container");
	var navheight = nav.offsetHeight + 20;
	containers[0].style.marginTop = navheight + "px";
}