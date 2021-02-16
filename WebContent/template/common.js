// 유튜브 플러그인 시작
function youtubePlugin() {
  toastui.Editor.codeBlockManager.setReplacer('youtube', youtubeId => {
    // Indentify multiple code blocks
    const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

    // Avoid sanitizing iframe tag
    setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

    return `<div id="${wrapperId}"></div>`;
  });
}

function renderYoutube(wrapperId, youtubeId) {
  const el = document.querySelector(`#${wrapperId}`);

  el.innerHTML = `<div class="toast-ui-youtube-plugin-wrap"><iframe src="https://www.youtube.com/embed/${youtubeId}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>`;
}
// 유튜브 플러그인 끝

// codepen 플러그인 시작
function codepenPlugin() {
  toastui.Editor.codeBlockManager.setReplacer('codepen', url => {
    const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

    // Avoid sanitizing iframe tag
    setTimeout(renderCodepen.bind(null, wrapperId, url), 0);

    return `<div id="${wrapperId}"></div>`;
  });
}

function renderCodepen(wrapperId, url) {
  const el = document.querySelector(`#${wrapperId}`);

  var urlParams = new URLSearchParams(url.split('?')[1]);
  var height = urlParams.get('height');

  el.innerHTML = `<div class="toast-ui-codepen-plugin-wrap"><iframe height="${height}" scrolling="no" src="${url}" frameborder="no" loading="lazy" allowtransparency="true" allowfullscreen="true"></iframe></div>`;
}
// codepen 플러그인 끝

function Editor__init() {

  $('.toast-ui-editor').each(function(index, node) {
    var initialValue = $(node).prev().html().trim().replace(/t-script/gi, 'script');
 // 토스트 UI에
	// <br/> 두개 들어가는 버그를 없애기 위한 궁여지책
	if ( initialValue.length == 0 ) {
		initialValue = " ";
	}
  let height = 600;

	if ( $(node).attr('data-height') ) {
		height = parseInt($(node).attr('data-height'));
	}

	let previewStyle = 'vertical';

	if ( $(node).attr('data-previewStyle') ) {
		previewStyle = $(node).attr('data-previewStyle');
	}
	else {
		if ( $(window).width() < 600 ) {
			previewStyle = 'tab';
		}
	}
	
	var editor = new toastui.Editor({
      el: node,	
      previewStyle: previewStyle,
      initialValue: initialValue,
      height:height,
      plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin, codepenPlugin]
    });

	$(node).data('data-toast-editor', editor);
  });
}

function EditorViewer__init() {
  $('.toast-ui-viewer').each(function(index, node) {
    var initialValue = $(node).prev().html().trim().replace(/t-script/gi, 'script');
    var viewer = new toastui.Editor.factory({
      el: node,
      initialValue: initialValue,
      viewer:true,
      plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin, codepenPlugin]
    });
  });
}

function MobileTopBar__init() {
	$('.mobile-top-bar__btn-toggle-mobile-side-bar').click(function() {
		if ( $(this).hasClass('active') ) {
			MobileTopBar__hide();
		}
		else {
			MobileTopBar__show();
		}
	});
}

function MobileTopBar__show() {
	$('.mobile-top-bar__btn-toggle-mobile-side-bar').addClass('active');
	$('html').addClass('mobile-side-bar-actived');
}

function MobileTopBar__hide() {
	$('.mobile-top-bar__btn-toggle-mobile-side-bar').removeClass('active');
	$('html').removeClass('mobile-side-bar-actived');
}

console.clear();

// getLastItemIndex
// get => 반환한다.
// LastItemIndex => 마지막 아이템 index
// 역할 : 마지막 아이템 index을 알려준다.
function Sbs2021Slider__getLastItemIndex(no) {
  return $('.sbs-2021-slider-' + no + ' .items > div:last-child').index();
}

// getCurrentItemIndex
// get => 반환한다.
// CurrentItemIndex => 현재 아이템 index
// 역할 : 현재아이템의 index을 알려준다.
function Sbs2021Slider__getCurrentItemIndex(no) {
  return $('.sbs-2021-slider-' + no + ' .items > div.active').index();
}

// 역할 : 기준점(현재아이템)을 기준으로 좌나 우로 step 만큼 움직인다.
// 역할 : 상대이동
function Sbs2021Slider__move(no, step) {
  var currentIndex = Sbs2021Slider__getCurrentItemIndex(no);
  var lastIndex = Sbs2021Slider__getLastItemIndex(no);
  var postIndex = currentIndex + step;
  
  if ( postIndex < 0 ) {
    postIndex = lastIndex;
  }
  else if ( postIndex > lastIndex ) {
    postIndex = 0;
  }
  
  Sbs2021Slider__show(no, postIndex);
}

// 역할 : 좌, 우 버튼에 클릭 이벤트를 건다.
// 역할 : 좌, 우 버튼을 클릭시, move(-1) 이나 move(1) 이 실행되도록 한다.
function Sbs2021Slider__init(no) {
  var $arrow = $('.sbs-2021-slider-' + no + ' .arrows > div');
  
  $arrow.click(function() {
    var $this = $(this);
    var index = $this.index();
    var isLeft = index == 0;
    
    //console.log("isLeft : " + isLeft);
    
    if ( isLeft ) {
      Sbs2021Slider__move(no, -1);
    }
    else {
      Sbs2021Slider__move(no, 1);
    }
  });
  
  var $pageBtn = $('.sbs-2021-slider-' + no + ' .pages > div');
  
  $pageBtn.click(function() {
    var $this = $(this);
    var index = $this.index();
    
    Sbs2021Slider__show(no, index);
  });
}

// 역할 : 현재 아이템 선택
function Sbs2021Slider__show(no, index) {
  var $selItem = $('.sbs-2021-slider-' + no + ' .items > div').eq(index);
  
  var $siblings = $selItem.siblings('.active');
  $siblings.removeClass('active');
  $selItem.addClass('active');
  
  var $selPage = $('.sbs-2021-slider-' + no + ' .pages > div').eq(index);
  $selPage.addClass('active');
  $selPage.siblings().removeClass('active');
}

//console.log(Sbs2021Slider__getCurrentItemIndex(1));
//Sbs2021Slider__show(1, 2);
//Sbs2021Slider__move(1, -2);
//Sbs2021Slider__move(1, -2);

Sbs2021Slider__init(1);

MobileTopBar__init(); 

EditorViewer__init();
Editor__init(); 
Editor__init_comment()