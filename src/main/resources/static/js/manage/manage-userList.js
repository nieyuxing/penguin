/**
 * 模块化JavaScript
 **/
var manageUserListPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        users: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, users) {
        manageUserListPage.data.pageNum = pageNum;
        manageUserListPage.data.pageSize = pageSize;
        manageUserListPage.data.totalPageNum = totalPageNum;
        manageUserListPage.data.totalPageSize = totalPageSize;
        manageUserListPage.data.users = users;
        //分页初始化
        manageUserListPage.subPageMenuInit();

        //审核通过
        $('#confirmapproveUserBtn').click(function(){
            manageUserListPage.approvedUserAction();
        });

        //审核拒绝
        $('#confirmRejectUserBtn').click(function(){
            manageUserListPage.rejectedUserAction();
        });

        //编辑账号，取消编辑
        $('#cancelUpdateUserBtn').click(function(){
            $("#updateUserModal").modal('hide');
        });

        //编辑账号，确定保存
        $('#confirmUpdateUserBtn').click(function(){
            manageUserListPage.updateUserAction();
        });

    },
    firstPage: function () {
        window.location.href = app.URL.manageUserListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.manageUserListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.manageUserListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.manageUserListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.manageUserListUrl() + '?page=' + manageUserListPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (manageUserListPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageUserListPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageUserListPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageUserListPage.data.pageNum-4 > 0 ? manageUserListPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > manageUserListPage.data.totalPageNum ? manageUserListPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageUserListPage.data.pageNum);
        console.log('totalPageNum = ' + manageUserListPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageUserListPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageUserListPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageUserListPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (manageUserListPage.data.pageNum == manageUserListPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageUserListPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageUserListPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },

    disabledUserAction: function (index) {
        $.ajax({
            url : app.URL.disabledUserUrl()+index,
            type : "POST",
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
    abledUserAction: function (index) {
        $.ajax({
            url : app.URL.abledUserUrl()+index,
            type : "POST",
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

        //编辑考试模态框触发
        updateUserModalAction: function (index) {
            //编辑考试，弹出编辑窗口
            console.log(index);
            //输入框初始化数据
            manageUserListPage.initUpdateUserData(index);
            $("#updateUserModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        },
    initUpdateUserData: function (index) {
        var users = manageUserListPage.data.users;
        //初始化数据
        $('#updateUserIndex').val(users[index].id);
        console.log(users);
        $('#updateName').val(users[index].name);
        $('#updatesex').val(users[index].sex);
        $('#updateismarry').val(users[index].ismarry);
        $('#updateQq').val(users[index].qq);
        $('#updatePhone').val(users[index].phone);
        $('#updateEmail').val(users[index].email);
        $('#updatevchat').val(users[index].vchat);

    },
    checkUpdateUserData: function (name, sex, ismarry, qq, phone, email ,vchat) {
        return true;
    },
    updateUserAction: function () {
        var index = $('#updateUserIndex').val();
        console.log(index);
        var name = $('#updateName').val();
        var sex = $('#updatesex').val();
        var ismarry = $('#updateismarry').val();
        var qq = $('#updateQq').val();
        var phone = $('#updatePhone').val();
        var email = $('#updateEmail').val();
        var vchat = $('#updatevchat').val();

        if (manageUserListPage.checkUpdateUserData(name, sex, ismarry, qq, phone, email ,vchat)) {
            $.ajax({
                url : app.URL.updateUserUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: index,
                    name: name,
                    sex: sex,
                    ismarry: ismarry,
                    qq: qq,
                    phone: phone,
                    email: email,
                    vchat: vchat,
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
};