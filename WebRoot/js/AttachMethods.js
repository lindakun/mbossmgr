function fromCurNav(objStr){
	return $(objStr, navTab.getCurrentPanel());
}

function fromCurDlg(objStr){
	return $(objStr, $.pdialog.getCurrent());
}