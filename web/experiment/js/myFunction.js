function startLinux() {
    $.ajax({
        url:"../UserServlet",
        data:{
            method:"startLinux"
        },
        success:function (result) {
            if(result=="找不到用户"){
                self.location="../login";
            }else if(result=="fail"){
                alert("出错了");
            }else {
                console.log("localhost:"+result);
                self.location="http://118.24.125.75:"+result;
            }
        }
    })
}

function clearCanvas(canvasId)
{
    var canvas_1 = document.getElementById(canvasId);
    canvas_1.height = canvas_1.height;
}


//startIndex从0开始  maxSize从一开始
function drawQueue(canvaId,data) {
    var maxSize = data[2].length;
    var arr = data[2];
    //绘图
    var canvas_1 = document.getElementById(canvaId);
    // 获取在 canvas 上绘图的 canvasRenderingContent2D 对象
    var ctx = canvas_1.getContext("2d");
    //每个格子占的角度
    var rotateAngle = Math.PI*2/maxSize;
    drawBase();
    drawArr();
    //绘制基础的公共的
    function drawBase() {
        ctx.beginPath();
        ctx.arc(500,250,200,0,2*Math.PI);
        ctx.stroke();
        ctx.beginPath();
        ctx.arc(500,250,150,0,2*Math.PI);
        ctx.stroke();
        ctx.translate(500,250);
        var textnum = 0;
        while (textnum<maxSize){
            ctx.beginPath();
            ctx.moveTo(0,-200);
            ctx.lineTo(0,-150);
            ctx.stroke();
            ctx.rotate(rotateAngle*0.5);
            ctx.font="20px Georgia";
            ctx.fillText(textnum,0,-130);
            ctx.rotate(rotateAngle*0.5);
            textnum+=1;
        }
    }
    //垂直向下的箭头
    function drawSArrow(x,y,lengths) {
        ctx.beginPath();
        ctx.moveTo(x,y);
        ctx.lineTo(x,y+lengths);
        ctx.lineTo(x-3,y+lengths-3);
        ctx.lineTo(x,y+lengths);
        ctx.lineTo(x+3,y+lengths-3);
        ctx.stroke();
    }

    function drawArr() {
        //先旋转到开始位置
        ctx.rotate(rotateAngle*0.5);
        for(var key in arr){
            if (key==data[0]){
                ctx.fillText("front",-20,-230);
                drawSArrow(0,-230,30);
            }
            if(key==data[1]){
                ctx.fillText("rear",-20,-215);
                drawSArrow(0,-220,18);
            }
            if(arr[key]!="空") {
                ctx.fillText(arr[key], 0, -170);
            }
            ctx.rotate(rotateAngle);
        }
    }
}

function handleQueueResult(resultStr) {
    var front;
    var rear;
    var arrLists=[];
    var result;
    var reg=/dataStart.*dataEnd\n/g;
    var drawData = resultStr.match(reg);
    result = resultStr.replace(reg,"");
    for(var key in drawData){
        var arrList=[];
        var frontReg =/front=\d+/g;
        var rearReg = /rear=\d+/g;
        var listReg = /list=.*dataEnd/g;
        front = parseInt(drawData[key].match(frontReg)[0].replace("front=",""));
        rear = parseInt(drawData[key].match(rearReg)[0].replace("rear=",""));
        var list = drawData[key].match(listReg)[0].replace("list=","").replace(" dataEnd","").split(" ");
        for(var key2 in list){
           arrList[key2]=list[key2];
        }
        arrLists[key]=[front,rear,arrList];
    }

    return [result,arrLists];
}

//test 指实验
function checkTestIsFinish(testNum,divSelect) {
    $.ajax({
        url:"../TestResultServlet",
        data:{
            method:"findByUsernameAndTestNum",
            testNum:testNum
        },
        success:function (result) {
            console.log(result);
            testResult = $.parseJSON(result);
            if(testResult.finish==0){
                $(divSelect).hide();
            }else{
                $(divSelect).text("已完成");
            }
        }
    });
}











