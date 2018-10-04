//添加悬浮提示内容
$(".insertBtn").attr("title","请保证光标落在要插入的位置上");

//textarea字体大小
var fontSize = 20;
$(".fontSizeAdd").click(function () {
    $("textarea").css("font-size",fontSize+2);
    fontSize+=2;
});
$(".fontSizeDec").click(function () {
    $("textarea").css("font-size",fontSize-2);
    fontSize-= 2;
});
var changeGreyNum = 0;
//计时起
var timeSpan = document.getElementById("time");
var n= 0, timer=null;
//开始计时
function startTime() {
    console.log("start timer");
    clearInterval(timer);
    timer=setInterval(function () {
        n++;
        var m=parseInt(n/60);
        var s=parseInt(n%60);
        timeSpan.innerHTML=toDub(m)+":"+toDub(s);
    },1000);
};
//暂停并且清空计时器
function stop() {
    clearInterval(timer);
}
//重置
function reSet() {
    timeSpan.innerHTML="00:00";
    n=0;
}
//补零
function toDub(n){
    return n<10?"0"+n:""+n;
}
startTime();

//边框位置
var leftDrag = false;
var centerDrag = false;
var mouseX=0;
$(".leftHandle").mousedown(function (event) {
    $(".left").css("overflow","hidden");
    $(".center").css("overflow","hidden");
    mouseX = event.pageX;
    leftDrag = true;

});
$(".leftHandle").on("mouseup",function () {
    leftDrag = false;
    $(".left").css("overflow","auto");
    $(".center").css("overflow","auto");
});
$(".left").on("mouseup",function () {
    leftDrag = false;
    $(".left").css("overflow","auto");
    $(".center").css("overflow","auto");
});
$(".left").mousemove(function (event) {
    if(leftDrag){
        a=event.pageX-mouseX;
        leftHandleMove(a);
        mouseX=event.pageX;
    }
});
$(".center").on("mouseup",function () {
    leftDrag = false;
    $(".left").css("overflow","auto");
    $(".center").css("overflow","auto");
});
$(".center").mousemove(function (event) {
    if(leftDrag){
        a=event.pageX-mouseX;
        leftHandleMove(a);
        mouseX=event.pageX;
    }
    if(centerDrag){
        a=event.pageX-mouseX;
        centerHandleMove(a);
        mouseX=event.pageX;
    }
});
$(".leftHandle").mousemove(function (event) {
    if(leftDrag){
        a=event.pageX-mouseX;
        leftHandleMove(a);
        mouseX=event.pageX;
    }
});
function leftHandleMove(X) {
    var leftW = $(".left").width();
    console.log("f"+leftW);
    leftW = leftW + X;
    console.log("s"+leftW);
    var centerW = $(".center").width();
    centerW = centerW - X;
    $(".left").width(leftW);
    console.log("gaibianhpu"+$(".left").width());
    $(".center").width(centerW);
}
//照着上面再来一次，有机会再整合
$(".centerHandle").mousedown(function (event) {
    $(".right").css("overflow","hidden");
    $(".center").css("overflow","hidden");
    mouseX = event.pageX;
    centerDrag = true;

});
$(".centerHandle").on("mouseup",function () {
    centerDrag = false;
    $(".right").css("overflow","auto");
    $(".center").css("overflow","auto");
});
$(".right").on("mouseup",function () {
    centerDrag = false;
    $(".right").css("overflow","auto");
    $(".center").css("overflow","auto");
});
$(".right").mousemove(function (event) {
    if(centerDrag){
        a=event.pageX-mouseX;
        centerHandleMove(a);
        mouseX=event.pageX;
    }
});
$(".centerHandle").mousemove(function (event) {
    if(centerDrag){
        a=event.pageX-mouseX;
        centerHandleMove(a);
        mouseX=event.pageX;
    }
});
function centerHandleMove(X) {
    var rightW = $(".right").width();
    rightW = rightW - X;
    var centerW = $(".center").width();
    centerW = centerW + X;
    $(".right").width(rightW);
    $(".center").width(centerW);
}



//自动插入次数
var autoInsert=0;
//提示次数
var cueNum = 0;
$("body").height($(window).height());
//主要是left的代码
$(".left .title").click(function (event) {
    changeGreyNum+=1;
    var checkOpen = $(this).next().css("display");
    console.log("brother display"+checkOpen);
    if(checkOpen!="none"){
        $(this).next().hide();
        $(this).children("img").attr("src","../images/arrow_right.png");
    }else {
        $(this).next().show();
        $(this).children("img").attr("src","../images/arrow_down.png");
    }
});

function insertAfterCursor(area,data){
    area = document.getElementById(area);
    $(area).focus();
    if(document.selection){
        var sel = document.selection().createRange();
        sel.txt = data;
    }else if(typeof area.selectionStart==="number"&&typeof area.selectionEnd==="number"){
        var startPos = area.selectionStart,
            endPos = area.selectionEnd,
            curPos = startPos,
            tempStr = area.value;
        var value = tempStr.substring(0,startPos)+data+tempStr.substring(endPos,tempStr.length);
        area.value=value;
        curPos+=value.length;
        area.selectionStart = area.selectionEnd = curPos;
    }else {
        area.value=area.value+data;
    }
}

$(".actiontBtn1").click(function () {
    changeGreyNum+=1;
    $(".center .utilBar ul li").first().show().click();
    $(this).parent().prev().css("color","#cccccc")
    $(this).hide();
});
$(".actiontBtn2").click(function () {
    changeGreyNum+=1;
    $(".center .utilBar ul li").first().next().show().click();
    $(this).parent().prev().css("color","#cccccc")
    $(this).hide();
});
$("#header").blur(function () {
    $("#header").attr("justBlur","true");
});
$("#code").blur(function () {
    $("#code").attr("justBlur","true");
});
$(".left .insertBtn").click(function () {
    changeGreyNum+=1;
    autoInsert+=1;
    $("#top .insertRest").text(10-cueNum);
    var code = $(this).prev().text();
    console.log("code:"+code);
    var textarea = $(this).prev().attr("aim");
    if($(textarea).attr("justBlur")=="true"){
        $(textarea).attr("justBlur","false");
    }else {
        $(".notFocustips").show();
        return;
    }
    insertAfterCursor(textarea.substring(1,textarea.length),code);
    $(this).prev().css("color","#cccccc");
    $(this).parent().parent().prev().css("color","#cccccc");
    $(this).hide();
});
$(".notFocustips").click(function () {
    console.log("should hide");
    $(this).hide();
});
//弹出的提示狂
var actionItem;
$(".yes").click(function () {
    $(".tips").hide();
    actionItem.prev().show();
    changeGreyNum+=1;
    if(actionItem.prev().find(".insertBtn").text()!="自动插入"){
        var that = actionItem;
        setTimeout(function () {
            console.log("that"+$(that));
            that.prev().css("color","#cccccc");
            that.parent().prev().css("color","#cccccc");
        },3000);
    }else {
        $(this).prev().css("color","#cccccc");
    }
    actionItem.hide();
    cueNum++;
    //标题变灰
    $("#top .cueRest").text(12-cueNum);
});
$(".cancle").click(function () {
    $(".tips").hide();
});


$(".left .cueBtn").click(function () {
    actionItem = $(this);
    console.log($(".notTip").prop("checked"));
    if($(".notTip").prop("checked")){
        $(".yes").click();
    }else {
        $(".tips").show();
    }
});

//根据不同的结果进行展示
function oneCheck() {
    //运行结果目标达成数
    var stepResult = 0;
    if(listarray.length==1){
        stepResult+=1;
        if(listarray[0].length==5){
            stepResult+=1;
            //第三个目标不检查
        }
        stepResult+=1;
    }
    console.log("stepresult"+stepResult);
    if(stepResult==3){
        $(".netxTip").show();
        $(".netxTip").text("成功生成带头结点的单链表，下面调用SqQueue.h文件中你写的插入函数，再然后再遍历一遍，看是否成功");
    }else {
        $(".netxTip").hide();
    }
}

function twoCheck() {
    $(".netxTip").show();
    $(".netxTip").text("成功生成两幅图，是否结束实验？");
    $(".netxTip").append("<br><button class='settle'>结束实验</button>");
    $(".settle").click(function () {
        settlement();
    });
    $(".netxTip").show();
}
$(".settle").click(function () {
    settlement();
});

//对实验进行结算
function settlement() {
    $(".over").hide();
    //运行结果目标达成数
    var stepResult = 0;
    if(listarray!=null){
        if(listarray[1][2]==888){
            stepResult+=1;
        }
    }
    console.log(stepResult);
    var cueRest = parseInt($(".cueRest").text());
    $(".theEndResult").empty();
    $(".theEndResult").append("<h3>实验成功</h3>");
    $(".theEndResult").append("<h4>"+timeSpan.innerHTML+"</h4>");
    $(".theEndResult").append("<h4>剩余提示数"+$(".cueRest").text()+"</h4>");
    //$(".theEndResult").append("<h4>变灰步骤数"+changeGreyNum+"</h4>");
    $(".theEndResult").append("<h4>自动插入数"+autoInsert+"</h4>");
    $(".theEndResult").append("<h4>实验得分"+(100-(13-cueRest)*0.5-(5-autoInsert)*0.5)+"</h4>");
    $(".theEndResult").append("<div class=\"exitDiv\"><a href=\"./testhomepage.html\">退出实验</a></div>");
    $(".theEndWrapper").show();

}

//上传实验数据
function upLoadData(isfinish){
    var testData = {"time":timeSpan.innerHTML,"cueRest": $(".cueRest").text(),"changGreyNum":changeGreyNum,"autoInsertNum":autoInsert};
    console.log("uploaddata!!");
    console.log(JSON.stringify(testData));
    $.ajax({
        type:"post",
        url:"../TestResultServlet",
        data: {
            method:"record",
            testNumber:"1",
            resultJSON:JSON.stringify(testData),
            finish:isfinish
        },
        async:false,
        success:function(data,status){
            if(data=="success"){
                console.log(data+"upload success")
            }else {
                console.log("upload fail")
                //self.location="../login.html";
            }
        }
    });
}
uploadtimer = setInterval("upLoadData(1)",3000);

$("textarea").keydown(function (e) {
    event = e||window.event;
    console.log(event.which);
    if(event.keyCode===9){
        event.preventDefault();
        var position=this.selectionStart+4;//此处我用了两个空格表示缩进，其实无所谓几个，只要和下面保持一致就好了。
        this.value=this.value.substr(0,this.selectionStart)+'    '+this.value.substr(this.selectionStart);
        this.selectionStart=position;
        this.selectionEnd=position;
        this.focus();
        event.returnValue=false;
    }
});
$(".bigLeft").click(function (event) {
    clearCanvas("myCanvas");
    console.log("left");
    event.stopPropagation();
    var picNum = $("#myCanvas").attr("picNum");
    var currSrcIndex = picNum;
    if(currSrcIndex==1){
        $("#myCanvas").attr("picNum",listarray.length);
    }else{
        $("#myCanvas").attr("picNum",currSrcIndex-1);
    }
    console.log("left picNum:"+$("#myCanvas").attr("picNum"))
    drawQueue("myCanvas",listarray[$("#myCanvas").attr('picNum') - 1]);
});
$(".over .bigRight").click(function (event) {
    event.stopPropagation();
    clearCanvas("myCanvas");
    console.log("right");
    var picNum = $("#myCanvas").attr("picNum");
    var currSrcIndex = parseInt(picNum);
    if(currSrcIndex==listarray.length){
        $("#myCanvas").attr("picNum",1);
    }else{
        $("#myCanvas").attr("picNum",currSrcIndex+1)
    }
    drawQueue("myCanvas",listarray[$("#myCanvas").attr('picNum') - 1]);
});
$(".over").click(function () {
    $(this).hide();
});
//存储链表数据
var listarray;

$(".run").click(runClick);
function runClick(){
    checkIsLogin();
    listarray=null;
    $("#result").val("");
    $(".right ul").empty();
    $.ajax({
        type:"post",
        url:"../test",data: {
            "code":$("#code").val(),
            "SqQueue.h":$("#header").val(),
            "headerName":"SqQueue.h"
        },
        async:false,
        success:function (data,status) {
            if(data=="code error"){
                alert(data);
            }
            console.log(data);
            data = $.parseJSON(data);
            handledData = handleQueueResult(data.result);
            $("#result").val(handledData[0]);
            listarray = handledData[1];
            console.log(listarray);
            $(".right ul").empty();
            var i = 1;
            for (i = 1; i <= listarray.length; i++) {
                $(".right ul").append("<li><img src='../images/pic.png' picNum='" + i + "' alt=\"\"></li>")
            }
            $(".right ul li").click(function (event) {
                $(".over").show();
                $("#myCanvas").attr("picNum", $(this).children().attr("picNum"));
                clearCanvas("myCanvas");
                drawQueue("myCanvas",listarray[$("#myCanvas").attr('picNum') - 1]);
            });
            // if(listarray.length==1){
            //     oneCheck();
            // }
            // if(listarray.length==2){
            //     twoCheck();
            // }
        }});
    upLoadData();
}
$(".center .utilBar ul li").click(function () {
    $(this).removeClass("unselected").siblings().addClass("unselected");
})


//下线验证
function checkIsLogin() {
    $.ajax({
        type:"post",
        url:"../UserServlet",
        data: {
            method:"checkLogin"
        },
        async:false,
        success:function(data,status){
            if(data=="isIn"){
                console.log(data+" is isIn")
            }else {
                self.location="../login.html";
            }
        }
    });
}