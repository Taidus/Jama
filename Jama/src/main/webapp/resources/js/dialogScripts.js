/**
 * 
 */

function setMenuValue(menu, dialogId, idAttr) {
	var id = $("div[id*=" + dialogId + "]").find("input[id*=" + idAttr+ "]").attr('value');

	if (menu != undefined && menu != null && id != undefined && id != null) {
		menu.selectValue(id);
	}

}