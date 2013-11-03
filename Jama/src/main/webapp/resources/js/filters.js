/*function setFilters(chiefFilter, companyFilter) {
	setChiefFilter(chiefFilter);
	setCompanyFilter(companyFilter);
	
//	jQuery("#" + tableId + "\\:agrTableChiefColumn\\:filter").val(chiefFilter);
//	jQuery("#" + tableId + "\\:agrTableCompanyColumn\\:filter").val(companyFilter);
}
 */
function setChiefFilter(chiefFilter) {
	jQuery("th[class*=chiefFilterColumn] select.ui-column-filter").val(chiefFilter);
}

function setCompanyFilter(companyFilter) {
	jQuery("th[class*=companyFilterColumn] select.ui-column-filter").val(companyFilter);
}

function setClosedContractsFilter(closedFilter) {
	jQuery("th[class*=closedFilterColumn] select.ui-column-filter").val(closedFilter);
}

function setContractTypeFilter(typeFilter) {
	jQuery("th[class*=contractTypeColumn] select.ui-column-filter").val(typeFilter);
}

function setOpScheduleFilters(chiefFilter, companyFilter, typeFilter) {
	setChiefFilter(chiefFilter);
	setCompanyFilter(companyFilter);
	setContractTypeFilter(typeFilter);
}

function setOpListFilters(chiefFilter, companyFilter, typeFilter) {
	setChiefFilter(chiefFilter);
	setCompanyFilter(companyFilter);
	setContractTypeFilter(typeFilter);
}

function setChiefListFilters(companyFilter, closedFilter, typeFilter) {
	setCompanyFilter(companyFilter);
	setClosedContractsFilter(closedFilter);
	setContractTypeFilter(typeFilter);
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
