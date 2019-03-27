//展示loading
function g_showLoading(){
	var idx = layer.msg('处理中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;  
	return idx;
}
//salt
var g_passsword_salt="1a2b3c4d"
// 获取url参数
function g_getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
};
//设定时间格式化函数，使用new Date().format("yyyyMMddhhmmss");  
Date.prototype.format = function (format) {  
    var args = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
    };  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var i in args) {  
        var n = args[i];  
        if (new RegExp("(" + i + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));  
    }  
    return format;  
};


function initMenu(menuInfo,id) {
    menuInfo = JSON.parse(menuInfo);
    console.log(menuInfo);
    if (menuInfo == null || menuInfo.length == 0) {
        return;
    }

    var htm = '';
    for (var i = 0; i < menuInfo.length; i++) {
        htm+= '<div class="panel panel-default panel-style">';
        htm+= '<a href="#collapse' + (i+1) + '" data-toggle="collapse" data-parent="#accordion">';
        htm+= '<div class="panel-heading">';
        htm+= '<h4 class="panel-title">';
        htm+= menuInfo[i].name;
        htm+= '<div class="pull-right"><span class="caret"></span></div>';
        htm+= '</h4>';
        htm+= '</div>';
        htm+= '</a>';

        htm+= '<div id="collapse' + (i+1) +'" class="panel-collapse collapse collapseBody">';
        htm+= '<div class="panel-body">';
        htm+= '<ul class="list-group">';

        for (var j = 0; j < menuInfo[i].children.length; j++) {
            htm+= '<li class="list-group-item">';
            htm+= '<a href="javascript:void(0)"  class="menu_item" name="'+ menuInfo[i].children[j].url + '">'
                + menuInfo[i].children[j].name +'</a>';
            htm+= '</li>';
        }

        htm+= '</ul>';
        htm+= '</div>';
        htm+= '</div>';
        htm+= '</div>';
    }
    $('#' + id).html(htm);
}


