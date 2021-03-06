
/**
 * 模块JavaScript
 */
var problemListPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        problemsetId: 0,
        questions: [],
    },
    initAddUserData: function () {
    console.log("sdahkdjhf");
            //初始化数据
            $('#questionTitle').val("");
            $('#questionContent').val("");
            $('#optionA').val("");
            $('#optionB').val("");
            $('#optionC').val("");
            $('#optionD').val("");
            $('#questionAnswer').val("");
            $('#questionParse').val("");
            $('#questionScore').val("");
        },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, problemsetId, questions) {
        problemListPage.data.pageNum = pageNum;
        problemListPage.data.pageSize = pageSize;
        problemListPage.data.totalPageNum = totalPageNum;
        problemListPage.data.totalPageSize = totalPageSize;
        problemListPage.data.problemsetId = problemsetId;
        problemListPage.data.questions = questions;
        //分页初始化
        problemListPage.subPageMenuInit();

        $("#addQuestionModal").attr("style","display:none");

        //新增题目，弹出新增窗口
        $("#applyPositionBtn").click(function () {
            //输入框初始化数据
            problemListPage.initAddUserData();

            $("#addQuestionModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //审核通过
        $('#uploadResumeBtn').click(function(){
            problemListPage.uploadResume();
        });

        //编辑账号，取消编辑
        $('#cancelUploadBtn').click(function(){
            $("#addQuestionModal").modal('hide');
        });

    },
    firstPage: function () {
        var problemsetId = problemListPage.data.problemsetId;
        window.location.href = app.URL.problemListUrl(problemsetId) + '?page=1';
    },
    prevPage: function () {
        var problemsetId = problemListPage.data.problemsetId;
        window.location.href = app.URL.problemListUrl(problemsetId) + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        var problemsetId = problemListPage.data.problemsetId;
        window.location.href = app.URL.problemListUrl(problemsetId) + '?page=' + page;
    },
    nextPage: function () {
        var problemsetId = problemListPage.data.problemsetId;
        window.location.href = app.URL.problemListUrl(problemsetId) + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        var problemsetId = problemListPage.data.problemsetId;
        window.location.href = app.URL.problemListUrl(problemsetId) + '?page=' + problemListPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '';
        if (problemListPage.data.pageNum == 1) {
            subPageStr += '<a class="item disabled">首页</a>';
            subPageStr += '<a class="item disabled">上一页</a>';
        } else {
            subPageStr += '<a onclick="problemListPage.firstPage()" class="item">首页</a>';
            subPageStr += '<a onclick="problemListPage.prevPage()" class="item">上一页</a>';
        }
        var startPage = (problemListPage.data.pageNum-4 > 0 ? problemListPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > problemListPage.data.totalPageNum ? problemListPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + problemListPage.data.pageNum);
        console.log('totalPageNum = ' + problemListPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == problemListPage.data.pageNum) {
                subPageStr += '<a onclick="problemListPage.targetPage('+i+')" class="active item">'+i+'</a>';
            } else {
                subPageStr += '<a onclick="problemListPage.targetPage('+i+')" class="item">'+i+'</a>'
            }
        }
        if (problemListPage.data.pageNum == problemListPage.data.totalPageNum) {
            subPageStr += '<a class="item disabled">下一页</a>';
            subPageStr += '<a class="item disabled">末页</a>';
        } else {
            subPageStr += '<a onclick="problemListPage.nextPage()" class="item">下一页</a>';
            subPageStr += '<a onclick="problemListPage.lastPage()" class="item">末页</a>';
        }
        $('#subPageMenu').html(subPageStr);
    },


    /**
     * ajax上传简历文件
     */
    uploadResume: function (){
        var fileName = $('#myfile').val();　　　　　　　　　　　　　//获得文件名称
        var fileType = fileName.substr(fileName.lastIndexOf('.'),fileName.length);
        console.log(fileName.lastIndexOf('.'));　　//截取文件类型,如(.xls)
        console.log("fileName",fileName);
        if(fileType=='.doc' || fileType=='.docx'|| fileType=='.pdf'){　　
            　　　//验证文件类型,此处验证也可使用正则
            console.log("进入上传");
            var formData = new FormData();
            formData.append('file', $('#myfile')[0].files[0]);
            $.ajax({
                url: app.data.contextPath+'/manage/uploadResume',　　　　　　　　　　//上传地址
                type: 'POST',
                cache: false,
                data: formData,　　　　　　　　　　　　　//表单数据
                processData: false,
                contentType: false,
                success:function(result){
                    if (result && result['success']) {
                        window.location.reload();
                    } else {
                        $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>'+result.message+'</p>');
                        $('#updateAccountErrorMessage').removeClass('hidden');
                    }

                }
            });
        }else{
            console.log("失败上传");
            layer.open({
                title: '温馨提示',
                content: "*上传文件类型错误,支持类型: .pdf .doc .docx"
            });
            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>*上传文件类型错误,支持类型: .pdf .doc .docx</p>');
            $('#updateAccountErrorMessage').removeClass('hidden');
        }
    },
    
    /**
     * 登录模态框显示
     */
     showLogin: function() {
        var username = $.cookie('penguinUsername');
        var password = $.cookie('penguinPassword');
        $('#username').val(username);
        $('#password').val(password);
        $('#loginModal').modal({
            /**
             * 必须点击相关按钮才能关闭
             */
            closable  : true,
            /**
             * 模糊背景
             */
            blurring: true,
        }).modal('show');
    },
};