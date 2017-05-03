/**
* 主工作区的侧边缩放
*/



// init sidebar
function initSide() {
	var $obj = $('.sidebar .sideNav');
	var $sl = $('.sublist', $obj);
	var $st = $sl.prev('.subtit');
	$sl.hide();
	$st.find('a').append('<em class="spread">&nbsp;</em>');
	var $sprshr = $('.spread', $obj);
	
	$st.toggle(function() {
		$(this).find('.spread').addClass('shrink');
		//$('.sublist', $obj).slideUp(300);
		$(this).next('.sublist').slideDown(300);
		return false;
	}, function(){
		$(this).find('.spread').removeClass('shrink');
		//$('.sublist', $obj).slideUp(300);
		$(this).next('.sublist').slideUp(300);
		return false;
	});
}
// show or hide sidebar
function switchBar() {
	$('.sidebar').after('<a id="sideSwitcher" class="sideSwitcher" href="#" title="收缩侧边栏">&lt;</a>');
	var $switchBtn = $('#sideSwitcher');
	$switchBtn.click(function() {
		$('body').toggleClass('hideSide');
		
		if( $('body').hasClass('hideSide') ) {
			$switchBtn.text('>').attr('title', '打开左边管理菜单')
		}
		else {
			$switchBtn.text('<').attr('title', '打开左边管理菜单');
		}
	});
    return false;
}
// resize content area
function resizeContent() {
	var winH = $(window).outerHeight();
	var topH = $('#header').outerHeight();
	var btmH = $('#footer').outerHeight();
    var framH = winH-topH-btmH;
    $('.sidebar').height(framH);
    $('.content').height(framH);
    $('.mainFrame').height(framH);
}
// comTablList 
function initComTablist() {
	$('.comTabList').each(function() {
		var $listObj = $(this);
		$("tr:first", $listObj).addClass('first').end().find('tr:last').addClass('last');
		$('tr:odd', $listObj).addClass('odd');
		$('tr:even', $listObj).addClass('eve');
		$('tr', $listObj).each(function() {
			$(this).find('th:last, td:last').addClass('last');
		});
		$('tr', $listObj).hover(function() {
			$(this).addClass('hover');
		}, function() {
			$(this).removeClass('hover');
		});
		return false;
	});
}
function initMenu(){
	$.get("pa/lb_menu.s",{},function(data){
		$("#sidebar").html(data);
		initSide();
		switchBar();
	},"html");
}
$(window).resize(function(e) {
	resizeContent();
});
$(document).ready(function(e) {
	resizeContent();
	initMenu();
	
	initComTablist();
});
