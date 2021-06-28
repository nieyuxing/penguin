/**
 * 模块化JavaScript
 **/
var manageDepartmentBoardPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        departments: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, departments) {
        manageDepartmentBoardPage.data.pageNum = pageNum;
        manageDepartmentBoardPage.data.pageSize = pageSize;
        manageDepartmentBoardPage.data.totalPageNum = totalPageNum;
        manageDepartmentBoardPage.data.totalPageSize = totalPageSize;
        manageDepartmentBoardPage.data.departments = departments;
        //分页初始化
        manageDepartmentBoardPage.subPageMenuInit();

        //新增考试，弹出新增窗口
        $("#addDepartmentBtn").click(function () {
            //输入框初始化数据
            manageDepartmentBoardPage.initAddDepartmentData();
            $("#addDepartmentModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增考试，取消考试增加
        $('#cancelAddDepartmentBtn').click(function(){
            $("#addDepartmentModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddDepartmentBtn').click(function(){
            manageDepartmentBoardPage.addDepartmentAction();
        });

        //编辑考试，取消考试编辑
        $('#cancelUpdateDepartmentBtn').click(function(){
            $("#updateDepartmentModal").modal('hide');
        });

        //编辑考试，确定保存考试
        $('#confirmUpdateDepartmentBtn').click(function(){
            manageDepartmentBoardPage.updateDepartmentAction();
        });
    },
    firstPage: function () {
        window.location.href = app.URL.manageDepartmentListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.manageDepartmentListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.manageDepartmentListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.manageDepartmentListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.manageDepartmentListUrl() + '?page=' + manageDepartmentBoardPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (manageDepartmentBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageDepartmentBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageDepartmentBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageDepartmentBoardPage.data.pageNum-4 > 0 ? manageDepartmentBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > manageDepartmentBoardPage.data.totalPageNum ? manageDepartmentBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageDepartmentBoardPage.data.pageNum);
        console.log('totalPageNum = ' + manageDepartmentBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageDepartmentBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageDepartmentBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageDepartmentBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (manageDepartmentBoardPage.data.pageNum == manageDepartmentBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageDepartmentBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageDepartmentBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    initAddDepartmentData: function () {
        //初始化数据
        $('#departmentName').val("");
    },
    checkAddDepartmentData: function (departmentName,departmentCode,departmentState,departmentDesc) {
        var flag = false;
        var msg ='';
        if (departmentName == null || departmentName == '' ) {
            msg = '名称不能为空!'
        }
        if (departmentCode == null || departmentCode == '' ) {
            msg = '编码不能为空!'
        }
        if(msg!=''){
            layer.open({
                title: '温馨提示',
                content: msg
            });
        }else{
            flag = true ;
        }
        return flag;

    },
    addDepartmentAction: function () {
        var departmentName = $('#departmentName').val();
        var departmentCode = $('#departmentCode').val();
        var departmentState = $('#departmentState').val();
        var departmentDesc = $('#departmentDesc').val();

        console.log(app.URL.addDepartmentUrl());

        if (manageDepartmentBoardPage.checkAddDepartmentData(departmentName,departmentCode,departmentState,departmentDesc)) {
            $.ajax({
                url : app.URL.addDepartmentUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    name: departmentName,
                    code:departmentCode,
                    state:departmentState,
                    desc:departmentDesc,
                }),
                success:function(result) {
                    if (result && result['success']) {
                        window.location.reload();
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: result.message
                        });
                    }
                },
                error:function(result){
                    layer.open({
                        title: '温馨提示',
                        content: result.message
                    });
                }
            });
        }
    },
    //编辑考试模态框触发
    updateDepartmentModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        manageDepartmentBoardPage.initUpdateDepartmentData(index);
        $("#updateDepartmentModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdateDepartmentData: function (index) {
        //初始化数据
        var departments = manageDepartmentBoardPage.data.departments;
        $('#updateDepartmentIndex').val(index);
        $('#updatename').val(departments[index].name);
        $('#updatecode').val(departments[index].code);
        $('#updatestate').val(departments[index].state);
        $('#updatedesc').val(departments[index].desc);
    },
    checkUpdateDepartmentData: function (name,state,code,desc) {
        var flag = false;
        var msg ='';
        if (departmentName == null || departmentName == '' ) {
            msg = '名称不能为空!'
        }
        if (departmentCode == null || departmentCode == '' ) {
            msg = '编码不能为空!'
        }
        if(msg!=''){
            layer.open({
                title: '温馨提示',
                content: msg
            });
        }else{
            flag = true ;
        }
        return flag;
    },
    updateDepartmentAction: function () {
        var departments = manageDepartmentBoardPage.data.departments;
        var index = $('#updateDepartmentIndex').val();
        var name = $('#updatename').val();
        var state = $('#updatestate').val();
        var code = $('#updatecode').val();
        var desc = $('#updatedesc').val();

        if (manageDepartmentBoardPage.checkUpdateDepartmentData(name,state,code,desc)) {
            $.ajax({
                url : app.URL.updateDepartmentUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: departments[index].id,
                    name:name,
                    state:state,
                    code:code,
                    desc:desc,
                }),
                success:function(result) {
                    if (result && result['success']) {
                        window.location.reload();
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: result.message
                        });
                    }
                },
                error:function(result){
                    layer.open({
                        title: '温馨提示',
                        content: result.message
                    });
                }
            });
        }
    },
    deleteDepartmentAction: function (index) {
        $.ajax({
            url : app.URL.deleteDepartmentUrl()+index,
            type : "DELETE",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            success:function(result) {
                if (result && result['success']) {
                    window.location.reload();
                } else {
                    layer.open({
                        title: '温馨提示',
                        content: result.message
                    });
                }
            },
            error:function(result){
                layer.open({
                    title: '温馨提示',
                    content: result.message
                });
            }
        });
    }


};