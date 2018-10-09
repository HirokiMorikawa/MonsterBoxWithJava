window.addEventListener("load", function() {
	var cookie = getCookie();
	var dfg = new DetailFileGetter("./details");
	// リクエストの送信
	dfg.request();

	var cardBox = document.getElementsByClassName("card");
	var userBox = document.getElementById("user");
	if (userBox != null) {
		userBox.innerHTML = "<li>" + dfg.getUserid() + "</li>";

		var img = document.createElement("img");
		if (dfg.isImg == "false") {
			img.setAttribute("src", "./../img/" + dfg.getThumbnail());
		} else {
			img.setAttribute("src", "./DorT?param=T");
		}

		img.setAttribute("class", "thumb");

		cardBox[0].insertBefore(img, userBox);
	} else {
		console.log("id属性:userがhtmlに定義されていません");
	}

	var fileBox = document.getElementById("file");
	if (fileBox != null) {
		var dayBox = document.createElement("div");
		var titleBox = document.createElement("div");
		var filenameBox = document.createElement("div");
		var ownerCommentBox = document.createElement("div");
		dayBox.innerHTML = "投稿日時 <br>" + dfg.getTime();
		titleBox.innerHTML = "ファイルタイトル<br>" + dfg.getTitle();
		filenameBox.innerHTML = "ファイル名<br>" + dfg.getFilename();
		ownerCommentBox.innerHTML = "一言コメント<br>" + dfg.getOwnerComment();
		fileBox.appendChild(dayBox);
		fileBox.appendChild(titleBox);
		fileBox.appendChild(filenameBox);
		fileBox.appendChild(ownerCommentBox);

		fileBox.setAttribute("class", "box col m6");
	} else {
		console.log("id属性:fileがhtmlに定義されていません");
	}

	var buttonBox = document.getElementById("d_a_f_button");
	if (buttonBox != null) {
		// div要素
		var downloadBox = document.createElement("div");
		var favoriteBox = document.createElement("div");
		// a要素
		var downloadLinkTag = document.createElement("a");
		var favoriteLinkTag = document.createElement("a");
		// img要素
		var downloadImgTag = document.createElement("img");
		var favoriteImgTag = document.createElement("img");

		downloadImgTag.setAttribute("src", "./../img/download_button.png");
		downloadImgTag.setAttribute("alt", "Img");
		downloadImgTag.setAttribute("height", "70");
		downloadImgTag.setAttribute("width", "200");

		favoriteImgTag.setAttribute("src", "./../img/favorite_button.png");
		favoriteImgTag.setAttribute("alt", "Img");
		favoriteImgTag.setAttribute("height", "70");
		favoriteImgTag.setAttribute("width", "200");

		downloadLinkTag.setAttribute("href", "./DorT?param=D");
		favoriteLinkTag.setAttribute("href", "./pushFavorite");

		downloadLinkTag.appendChild(downloadImgTag);
		downloadBox.appendChild(downloadLinkTag);

		favoriteLinkTag.appendChild(favoriteImgTag);
		favoriteBox.appendChild(favoriteLinkTag);

		buttonBox.appendChild(downloadBox);
		buttonBox.appendChild(favoriteBox);

	} else {
		console.log("id属性:d_a_fがhtmlに定義されていません");
	}

	var commentViewBox = document.getElementById("commentView");

	if (commentViewBox != null) {
		for (var i = 0; i < dfg.getComments().length; i++) {
			var commentBox = document.createElement("div");
			commentBox.setAttribute("class", "box");
			commentBox.innerHTML = "投稿者ID:" + dfg.getComments()[i].userid
					+ "<br>" + dfg.getComments()[i].comment;
			commentViewBox.appendChild(commentBox);
		}
	} else {
		console.log("id属性:commentViewがhtmlに定義されていません");
	}

}, false);

/**
 * ユーザがアップロードしたファイルの情報について取得
 */
function DetailFileGetter(url/* , destUserId, destFilename */) {
	this.url = url;
	// this.destUserId = destUserId;
	// this.destFilename = destFilename;
	this.userid = "unknownUser";
	this.title = "unknownTitle";
	this.filename = "unknownFilename";
	this.time = "unknownTime";
	this.ownerComment = "unknownComment";
	this.comments = [ {
		comment : "blank",
		userid : "blank"
	} ];
}

/**
 * リクエストの送信をおこなう
 */
DetailFileGetter.prototype.request = function() {
	var xmlHttpRequest = new XMLHttpRequest();
	var This = this;

	// ajaxの状態基づいた処理をおこなう
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
			var response = JSON.parse(xmlHttpRequest.responseText);
			This.userid = response.userid;
			This.title = response.title;
			This.filename = response.filename;
			This.time = response.time;
			This.ownerComment = response.ownerComment;
			This.comments = response.comments;
			This.thumbnail = response.thumbnail;
			This.isImg = response.img;
		}
	}

	xmlHttpRequest.open("GET", this.url/*
										 * + "?userid=" + this.destUserId +
										 * "&filename=" + this.destFilename
										 */, false);
	xmlHttpRequest.send(null);
}

DetailFileGetter.prototype.getUserid = function() {
	return this.userid;
}
DetailFileGetter.prototype.getTitle = function() {
	return this.title;
}
DetailFileGetter.prototype.getFilename = function() {
	return this.filename;
}
DetailFileGetter.prototype.getTime = function() {
	return this.time
}
DetailFileGetter.prototype.getOwnerComment = function() {
	return this.ownerComment;
}
DetailFileGetter.prototype.getComments = function() {
	return this.comments;
}
DetailFileGetter.prototype.getThumbnail = function() {
	return this.thumbnail;
}
DetailFileGetter.prototype.isImg = function() {
	return this.isImg;
}