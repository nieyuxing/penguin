/**
 * 模块化JavaScript
 **/
var managePositionBoardPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        positions: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, positions) {
        managePositionBoardPage.data.pageNum = pageNum;
        managePositionBoardPage.data.pageSize = pageSize;
        managePositionBoardPage.data.totalPageNum = totalPageNum;
        managePositionBoardPage.data.totalPageSize = totalPageSize;
        managePositionBoardPage.data.positions = positions;
        //分页初始化
        managePositionBoardPage.subPageMenuInit();

        //新增考试，弹出新增窗口
        $("#addPositionBtn").click(function () {
            //输入框初始化数据
            managePositionBoardPage.initAddPositionData();
            $("#addPositionModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增考试，取消考试增加
        $('#cancelAddPositionBtn').click(function(){
            $("#addPositionModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddPositionBtn').click(function(){
            managePositionBoardPage.addPositionAction();
        });

        //编辑考试，取消考试编辑
        $('#cancelUpdatePositionBtn').click(function(){
            $("#updatePositionModal").modal('hide');
        });

        //编辑考试，确定保存考试
        $('#confirmUpdatePositionBtn').click(function(){
            managePositionBoardPage.updatePositionAction();
        });
    },
    firstPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + managePositionBoardPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (managePositionBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePositionBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="managePositionBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (managePositionBoardPage.data.pageNum-4 > 0 ? managePositionBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > managePositionBoardPage.data.totalPageNum ? managePositionBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + managePositionBoardPage.data.pageNum);
        console.log('totalPageNum = ' + managePositionBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == managePositionBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="managePositionBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="managePositionBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (managePositionBoardPage.data.pageNum == managePositionBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePositionBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="managePositionBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    initAddPositionData: function () {
        //初始化数据
        $('#positionName').val("");
    },
    checkAddPositionData: function (positionName) {
        return true;
    },
    addPositionAction: function () {
        var positionName = $('#positionName').val();

        if (managePositionBoardPage.checkAddPositionData(positionName)) {
            $.ajax({
                url : app.URL.addPositionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    name: positionName,
                }),
                success:function(result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        console.log(result.message);
                    }
                },
                error:function(result){
                    console.log(result.message);
                }
            });
        }
    },
    //编辑考试模态框触发
    updatePositionModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        managePositionBoardPage.initUpdatePositionData(index);
        $("#updatePositionModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdatePositionData: function (index) {
        //初始化数据
        var positions = managePositionBoardPage.data.positions;
        $('#updatePositionIndex').val(index);
        $('#updatePositionName').val(positions[index].name);
    },
    checkUpdatePositionData: function (positionName) {
        return true;
    },
    updatePositionAction: function () {
        var positions = managePositionBoardPage.data.positions;
        var index = $('#updatePositionIndex').val();
        var subjejctName = $('#updatePositionName').val();

        if (managePositionBoardPage.checkUpdatePositionData(positionName)) {
            $.ajax({
                url : app.URL.updatePositionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: positions[index].id,
                    name: subjejctName,
                    questionNum: positions[index].questionNum,
                    imgUrl: positions[index].imgUrl,

                }),
                success:function(result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        console.log(result.message);
                    }
                },
                error:function(result){
                    console.log(result.message);
                }
            });
        }
    },
    deletePositionAction: function (index) {
        $.ajax({
            url : app.URL.deletePositionUrl()+index,
            type : "DELETE",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            success:function(result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    console.log(result.message);
                }
            },
            error:function(result){
                console.log(result.message);
            }
        });
    }


};