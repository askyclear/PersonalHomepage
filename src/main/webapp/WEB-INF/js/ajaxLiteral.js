/**
 * 생성날짜 : 2018.06.13 최종수정날짜 : 2018.06.13
 * 작성자 : 김대선 
 */
/*
 * ajaxLiteral로 요처알 URL, METHOD, callback function을 입력한후, sendAjax하면 됨 
 */
//var ajaxLiteral = {
//		xReq : new XMLHttpRequest(),
//		URL : "",
//		METHOD : "",
//		callback : function(func){
//			func(this.xReq.responseText);
//		}.bind(ajaxLiteral),
//		sendAjax : function(data){
//			if(this.METHOD === 'GET'){
//				this.xReq.addEventListener("load", this.callback);
//			    this.xReq.open(this.method, this.url + data);
//			    this.xReq.send();
//			}
//		}
//}