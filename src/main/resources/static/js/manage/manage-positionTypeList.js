/**
 * 模块化JavaScript
 **/
var managePositionTypeListPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        positionTypes: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, positionTypes) {
        managePositionTypeListPage.data.pageNum = pageNum;
        managePositionTypeListPage.data.pageSize = pageSize;
        managePositionTypeListPage.data.totalPageNum = totalPageNum;
        managePositionTypeListPage.data.totalPageSize = totalPageSize;
        managePositionTypeListPage.data.positionTypes = positionTypes;
        //分页初始化
        managePositionTypeListPage.subPageMenuInit();

        //新增用户，弹出新增窗口
        $("#addPositionTypeBtn").click(function () {
            //输入框初始化数据
            managePositionTypeListPage.initAddPositionTypeData();
            $("#addPositionTypeModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增用户，取消增加
        $('#canceladdPositionTypeBtn').click(function(){
            $("#addPositionTypeModal").modal('hide');
        });

        //新增用户，确定增加
        $('#confirmaddPositionTypeBtn').click(function(){
            managePositionTypeListPage.addPositionTypeAction();
        });

        //编辑账号，取消编辑
        $('#cancelUpdatePositionTypeBtn').click(function(){
            $("#updatePositionTypeModal").modal('hide');
        });

        //编辑账号，确定保存
        $('#confirmUpdatePositionTypeBtn').click(function(){
            managePositionTypeListPage.updatePositionTypeAction();
        });
    },
    firstPage: function () {
        window.location.href = app.URL.managePositionTypeListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.managePositionTypeListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.managePositionTypeListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.managePositionTypeListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.managePositionTypeListUrl() + '?page=' + managePositionTypeListPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (managePositionTypeListPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePositionTypeListPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="managePositionTypeListPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (managePositionTypeListPage.data.pageNum-4 > 0 ? managePositionTypeListPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > managePositionTypeListPage.data.totalPageNum ? managePositionTypeListPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + managePositionTypeListPage.data.pageNum);
        console.log('totalPageNum = ' + managePositionTypeListPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == managePositionTypeListPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="managePositionTypeListPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="managePositionTypeListPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (managePositionTypeListPage.data.pageNum == managePositionTypeListPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePositionTypeListPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="managePositionTypeListPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    initAddPositionTypeData: function () {
        //初始化数据
        $('#addName').val("");
    },
    checkAddPositionTypeData: function (name) {
        return true;
    },
    addPositionTypeAction: function () {
        var name = $('#addName').val();
        if (managePositionTypeListPage.checkAddPositionTypeData(name)) {
            $.ajax({
                url : app.URL.addPositionTypeUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    name: name,

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
    updatePositionTypeModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        managePositionTypeListPage.initUpdatePositionTypeData(index);
        $("#updatePositionTypeModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdatePositionTypeData: function (index) {
        var positionTypes = managePositionTypeListPage.data.positionTypes;
        //初始化数据
        $('#updatePositionTypeIndex').val(positionTypes[index].id);
        $('#updateName').val(positionTypes[index].name);
    },
    checkUpdatePositionTypeData: function (name) {
        return true;
    },
    updatePositionTypeAction: function () {
        var index = $('#updatePositionTypeIndex').val();
        var name = $('#updateName').val();

        if (managePositionTypeListPage.checkUpdatePositionTypeData(name)) {
            $.ajax({
                url : app.URL.updatePositionTypeUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: index,
                    name: name,

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
    deletePositionTypeAction: function (index) {
        $.ajax({
            url : app.URL.deletePositionTypeUrl()+index,
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
    },

};