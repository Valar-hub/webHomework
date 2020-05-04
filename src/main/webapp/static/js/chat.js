function chat(element,imgSrc,leftTextContent){
    //判断传入的值，是医生还是患者
    var $user = element;
    var $leftHead = imgSrc;
    //获取输入的值
    var $textContent = $('.chat-info').val();
    //获取传入的输入的内容
    var $leftTextContent =  leftTextContent;
    //获取容器
    var $box = $('.bubbleDiv');
    var $boxHeght = $box.height();
    var $sectionHeght = $(".chat-box").height();
    var $elvHeght = Math.abs($boxHeght-$sectionHeght);
    //医生
    if($user === "leftBubble") {
        $box.append(createdLeft(imgSrc,$leftTextContent)).animate({scrollTop: $(document).height()}, 300);
    }else if($user === "rightBubble") {
        $box.append(createRight(imgSrc,$textContent)).animate({scrollTop: $(document).height()}, 300);
    }
}
//
function createdLeft(imgSrc, $leftTextContent ) {
    var $textContent = $leftTextContent;
    var $imgSrc = imgSrc;
    var block;
    if($textContent == ''|| $textContent == 'null'){
        alert('亲！别太着急，先说点什么～');
        return;
    }
    block = '<div class="bubbleItem">' +
            '<div class="left-head">' +
            '<img src="'+ imgSrc +'" alt="doctor"/>' +
            '</div>' +
            '<span class="bubble leftBubble">' + $textContent + '<span class="topLevel"></span></span>' +
            '</div>';
    return block;
};
//
function createRight(imgSrc,$textContent ) {
    var $textContent = $textContent;
    var block;
    if($textContent == ''|| $textContent == 'null'){
        alert('亲！别太着急，先说点什么～');
        return;
    }
    block = '<div class="bubbleItem clearfix">' +
            '<span class="bubble rightBubble">' + $textContent + '<span class="topLevel"></span></span>' +
            '<div class="right-head">' +
            '<img src="'+ imgSrc +'" alt="doctor"/>' +
            '</div>' +
            '</div>';
    return block;
};
