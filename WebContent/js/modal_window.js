/*
 * モーダルを開く
 */
function openModal(elements) {
	var float = elements["float"];
	var overlay = elements["overlay"];
	float.style.display = "block";
	overlay.style.display = "block";
	// スクロール不可にする
	var main = document.getElementsByTagName("body");
	Array.prototype.slice.call(main).forEach(function(e) {
		e.style.overflow = "hidden";
	});
}
/*
 * モーダルを閉じる
 */
function closeModal(elements) {
	var float = elements["float"];
	var overlay = elements["overlay"];
	float.style.display = "none";
	overlay.style.display = "none";
	// スクロール可能にする
	var main = document.getElementsByTagName("body");
	Array.prototype.slice.call(main).forEach(function(e) {
		e.style.overflow = "auto";
	});
}

function inVisible(elements) {
	var float = elements["float"];
	float.style.display = "none";
	var overlay = elements["overlay"];
	overlay.style.display = "none";
}

/*
 * モーダルの設定
 */
function modal_window_actor(elements) {
	inVisible(elements);
	var open_modal = elements["open_modal"];
	Array.prototype.slice.call(open_modal).forEach(function(mo) {
		mo.addEventListener("click", function() {
			openModal(elements);
		}, false);
	});
	var overlay = elements["overlay"];
	overlay.addEventListener("click", function() {
		closeModal(elements);
	}, false);
}
