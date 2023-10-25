$(document).ready(function(){
	
	$("#loginBtn").on("click", function(){
		$("#loginFrm").attr("action", "/main")
		$("#loginFrm").submit();
	});
	
})