/**
 * 模块化JavaScript
 **/
var manageExamAnswerBoardPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        examinationAnswers: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, examinationAnswers) {
        manageExamAnswerBoardPage.data.pageNum = pageNum;
        manageExamAnswerBoardPage.data.pageSize = pageSize;
        manageExamAnswerBoardPage.data.totalPageNum = totalPageNum;
        manageExamAnswerBoardPage.data.totalPageSize = totalPageSize;
        manageExamAnswerBoardPage.data.examinationAnswers = examinationAnswers;
        //分页初始化
        manageExamAnswerBoardPage.subPageMenuInit();

        //新增考试，弹出新增窗口
        $("#addAnswerBtn").click(function () {
            //输入框初始化数据
            manageExamAnswerBoardPage.initAddAnswerData();
            $("#addAnswerModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });
        //
        //新增考试，取消考试增加
        $('#cancelAddAnswerBtn').click(function(){
            $("#addContestModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddAnswerBtn').click(function(){
            // manageExamAnswerBoardPage.addContestAction();
        });
        //新增考试，确定增加考试
        $('#cancelAddAnswerBtn').click(function(){
            // manageExamAnswerBoardPage.addContestAction();
        });
        //
        // //编辑考试，取消考试编辑
        // $('#cancelUpdateContestBtn').click(function(){
        //     $("#updateContestModal").modal('hide');
        // });
        //
        // //编辑考试，确定保存考试
        // $('#confirmUpdateContestBtn').click(function(){
        //     manageExamAnswerBoardPage.updateContestAction();
        // });

        //日期时间控件
        $('.form_datetime').datetimepicker({
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1
        });
    },
    firstPage: function () {
        window.location.href = app.URL.manageResultContestListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.manageResultContestListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.manageResultContestListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.manageResultContestListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.manageResultContestListUrl() + '?page=' + manageExamAnswerBoardPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (manageExamAnswerBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageExamAnswerBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageExamAnswerBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageExamAnswerBoardPage.data.pageNum-4 > 0 ? manageExamAnswerBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > manageExamAnswerBoardPage.data.totalPageNum ? manageExamAnswerBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageExamAnswerBoardPage.data.pageNum);
        console.log('totalPageNum = ' + manageExamAnswerBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageExamAnswerBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageExamAnswerBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageExamAnswerBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (manageExamAnswerBoardPage.data.pageNum == manageExamAnswerBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageExamAnswerBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageExamAnswerBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },

    initAddAnswerData: function () {

        $('#addquestion_id').val("");
        $('#adduser_id').val("");
    },
    //编辑考试模态框触发
    // updateContestModalAction: function (index) {
    //     //编辑考试，弹出编辑窗口
    //     //console.log(index);
    //     //输入框初始化数据
    //     manageExamAnswerBoardPage.initUpdateContestData(index);
    //     $("#updateContestModal").modal({
    //         keyboard : false,
    //         show : true,
    //         backdrop : "static"
    //     });
    // },
    // initUpdateContestData: function (index) {
    //     //初始化数据
    //     $('#updateContestIndex').val(index);
    //     $('#updateContestTitle').val(examinationAnswers[index].title);
    //     var selectSubjects = document.getElementById('updateContestSubjectId');
    //     for (var i = 0; i < selectSubjects.length; i++) {
    //         if (selectSubjects[i].value == examinationAnswers[index].subjectId) {
    //             selectSubjects[i].selected = true;
    //         }
    //     }
    //     $('#updateContestStartDatetimepicker').val(app.formatTime(examinationAnswers[index].startTime, "Y-M-D h:m:s"));
    //     $('#updateContestEndDatetimepicker').val(app.formatTime(examinationAnswers[index].endTime, "Y-M-D h:m:s"));
    // },
    // checkUpdateContestData: function (contestTitle, startTime, endTime) {
    //     if (contestTitle == null || contestTitle == ''
    //         || contestTitle.replace(/(^s*)|(s*$)/g, "").length == 0) {
    //         //TODO::信息校验
    //         $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
    //             '                <p>'+'账号不能为空'+'</p>');
    //         $('#loginModalErrorMessage').removeClass('hidden');
    //         return false;
    //     }
    //     if (startTime == null || startTime == ''
    //         || startTime.replace(/(^s*)|(s*$)/g, "").length == 0) {
    //         $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
    //             '                <p>'+'密码不能为空'+'</p>');
    //         $('#loginModalErrorMessage').removeClass('hidden');
    //         return false;
    //     }
    //     return true;
    // },
    // updateContestAction: function () {
    //     var index = $('#updateContestIndex').val();
    //     var contestTitle = $('#updateContestTitle').val();
    //     var subjectId = $('#updateContestSubjectId').val();
    //     var startTimeStr = $('#updateContestStartDatetimepicker').val();
    //     var endTimeStr = $('#updateContestEndDatetimepicker').val();
    //     var startTime = new Date($('#updateContestStartDatetimepicker').val());
    //     var endTime = new Date($('#updateContestEndDatetimepicker').val());
    //
    //     if (manageExamAnswerBoardPage.checkUpdateContestData(contestTitle, startTimeStr, endTimeStr)) {
    //         $.ajax({
    //             url : app.URL.updateContestUrl(),
    //             type : "POST",
    //             dataType: "json",
    //             contentType : "application/json;charset=UTF-8",
    //             <!-- 向后端传输的数据 -->
    //             data : JSON.stringify({
    //                 id: examinationAnswers[index].id,
    //                 title: contestTitle,
    //                 subjectId: subjectId,
    //                 startTime: startTime,
    //                 endTime: endTime,
    //                 totalScore: examinationAnswers[index].totalScore,
    //                 state: examinationAnswers[index].state,
    //             }),
    //             success:function(result) {
    //                 if (result && result['success']) {
    //                     // 验证通过 刷新页面
    //                     window.location.reload();
    //                 } else {
    //                     $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
    //                         '                <p>'+result.message+'</p>');
    //                     $('#loginModalErrorMessage').removeClass('hidden');
    //                 }
    //             },
    //             error:function(result){
    //                 $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
    //                     '                <p>'+result.message+'</p>');
    //                 $('#loginModalErrorMessage').removeClass('hidden');
    //             }
    //         });
    //     }
    // },
    // deleteContestAction: function (index) {
    //     $.ajax({
    //         url : app.URL.deleteContestUrl()+index,
    //         type : "DELETE",
    //         dataType: "json",
    //         contentType : "application/json;charset=UTF-8",
    //         success:function(result) {
    //             if (result && result['success']) {
    //                 // 验证通过 刷新页面
    //                 window.location.reload();
    //             } else {
    //                 $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
    //                     '                <p>'+result.message+'</p>');
    //                 $('#loginModalErrorMessage').removeClass('hidden');
    //             }
    //         },
    //         error:function(result){
    //             $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
    //                 '                <p>'+result.message+'</p>');
    //             $('#loginModalErrorMessage').removeClass('hidden');
    //         }
    //     });
    // },
    // targetResultStudentBoardAction: function (contestId) {
    //     window.location.href = app.URL.manageResultStudentListUrl(
    //         contestId);
    // },


};