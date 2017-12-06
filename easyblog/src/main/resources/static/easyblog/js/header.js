window.onload = function () {
	document.querySelector('#menu_button').addEventListener('click', function () {
		if (getEle("menu").style.display == "none") {
			showdiv("menu", true);
		} else {
			showdiv("menu", false);
		}
	})

}