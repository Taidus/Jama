function setFilters(chiefFilter, companyFilter) {
	jQuery("th[class*=chiefFilterColumn] select.ui-column-filter").val(chiefFilter);
	jQuery("th[class*=companyFilterColumn] select.ui-column-filter").val(companyFilter);

//	jQuery("#" + tableId + "\\:agrTableChiefColumn\\:filter").val(chiefFilter);
//	jQuery("#" + tableId + "\\:agrTableCompanyColumn\\:filter").val(companyFilter);
}

function calendarFilter(event, table) {
	event.stopPropagation();
	table.filter();
	return false;
}

function calendarReset(event, firstDate, lastDate, table) {
	firstDate.setDate(null);
	lastDate.setDate(null);
	return calendarFilter(event, table);
}


