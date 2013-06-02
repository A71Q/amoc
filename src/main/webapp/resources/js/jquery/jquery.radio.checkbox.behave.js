/*
*
*jquery.radio.checkbox.js starts
*
*/
jQuery.fn.extend({
behaveLikeCheckbox: function(){
	var radioStatus	=	false;		
	$(this).mouseover(function(){
		this.radioStatus = $(this).attr("checked");
	});
	$(this).click(function(){
		var blnNewStatus = false;
		if(this.radioStatus != true)
			blnNewStatus = true;
		$(this).attr("checked",blnNewStatus);
		this.radioStatus = blnNewStatus;
	});
}
});
/*
*
*jquery.radio.checkbox.js ends
*
*/