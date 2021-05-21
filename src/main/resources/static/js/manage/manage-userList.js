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

        //新增用户，弹出新增窗口
        $("#addAccountBtn").click(function () {
            //输入框初始化数据
            manageUserListPage.initAddAccountData();
            $("#addAccountModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增用户，取消增加
        $('#cancelAddAccountBtn').click(function(){
            $("#addAccountModal").modal('hide');
        });

        //审核通过
        $('#confirmapproveUserBtn').click(function(){
            manageUserListPage.approvedAccountAction();
        });

        //审核拒绝
        $('#confirmRejectAccountBtn').click(function(){
            manageUserListPage.rejectedAccountAction();
        });

        //编辑账号，取消编辑
        $('#cancelUpdateAccountBtn').click(function(){
            $("#updateAccountModal").modal('hide');
        });

        //编辑账号，确定保存
        $('#confirmUpdateAccountBtn').click(function(){
            manageUserListPage.updateAccountAction();
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
    initAddAccountData: function () {
        //初始化数据
        $('#addName').val("");
        $('#addUsername').val("");
        $('#addPassword').val("");
        $('#addQq').val("");
        $('#addPhone').val("");
        $('#addEmail').val("");
    },
    checkAddAccountData: function (name, username, password, qq, phone, email) {
        return true;
    },
    addAccountAction: function () {
        var name = $('#addName').val();
        var username = $('#addUsername').val();
        var password = $('#addPassword').val();
        var qq = $('#addQq').val();
        var phone = $('#addPhone').val();
        var email = $('#addEmail').val();
        var level = $('#addLevel').val();

        if (manageUserListPage.checkAddAccountData(name, username, password, qq, phone, email)) {
            $.ajax({
                url : app.URL.addAccountUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    name: name,
                    username: username,
                    password: password,
                    qq: qq,
                    phone: phone,
                    email: email,
                    level: level,
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
    updateAccountModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        console.log(index);
        //输入框初始化数据
        manageUserListPage.initUpdateAccountData(index);
        $("#updateAccountModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },

    //简历审核框触发
    approvedAccountModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //输入框初始化数据
        manageUserListPage.initApproveAccountData(index);
        $("#approvedAccountModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdateAccountData: function (index) {
        var accounts = manageUserListPage.data.accounts;
        //初始化数据
        $('#updateAccountIndex').val(accounts[index].id);
        $('#updateName').val(accounts[index].name);
        $('#updateUsername').val(accounts[index].username);
        $('#updatePassword').val("");
        $('#updateQq').val(accounts[index].qq);
        $('#updatePhone').val(accounts[index].phone);
        $('#updateEmail').val(accounts[index].email);
        var selectLevels = document.getElementById('updateLevel');
        for (var i = 0; i < selectLevels.length; i++) {
            if (selectLevels[i].value == accounts[index].level) {
                selectLevels[i].selected = true;
            }
        }
    },
    initApproveAccountData: function (index) {
        var accounts = manageUserListPage.data.accounts;
        //初始化数据
        $('#approveAccountIndex').val(accounts[index].id);
        $('#approveName').val(accounts[index].name);
        $('#approveUsername').val(accounts[index].username);
        $('#approveQq').val(accounts[index].qq);
        $('#approvePhone').val(accounts[index].phone);
        $('#approveEmail').val(accounts[index].email);
        var selectLevels = document.getElementById('updateLevel');
        for (var i = 0; i < selectLevels.length; i++) {
            if (selectLevels[i].value == accounts[index].level) {
                selectLevels[i].selected = true;
            }
        }
    },
    checkUpdateAccountData: function (name, username, password, qq, phone, email) {
        return true;
    },
    updateAccountAction: function () {
        var index = $('#updateAccountIndex').val();
        var name = $('#updateName').val();
        var username = $('#updateUsername').val();
        var password = $('#updatePassword').val();
        var qq = $('#updateQq').val();
        var phone = $('#updatePhone').val();
        var email = $('#updateEmail').val();
        var level = $('#updateLevel').val();

        if (manageUserListPage.checkUpdateAccountData(username, phone, qq, password)) {
            $.ajax({
                url : app.URL.updateAccountUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: index,
                    name: name,
                    username: username,
                    password: password,
                    qq: qq,
                    phone: phone,
                    email: email,
                    level: level,
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
    deleteAccountAction: function (index) {
        $.ajax({
            url : app.URL.deleteAccountUrl()+index,
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
    disabledAccountAction: function (index) {
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
    abledAccountAction: function (index) {
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

};