/**
 * 模块化JavaScript
 **/
var manageQuestionBoardPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        content: "",
        questions: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, content, questions) {
        manageQuestionBoardPage.data.pageNum = pageNum;
        manageQuestionBoardPage.data.pageSize = pageSize;
        manageQuestionBoardPage.data.totalPageNum = totalPageNum;
        manageQuestionBoardPage.data.totalPageSize = totalPageSize;
        manageQuestionBoardPage.data.content = content;
        manageQuestionBoardPage.data.questions = questions;
        //分页初始化
        manageQuestionBoardPage.subPageMenuInit();

        $('#content').val(content);

        //新增题目，弹出新增窗口
        $("#addQuestionBtn").click(function () {
            //输入框初始化数据
            manageQuestionBoardPage.initAddQuestionData();
            $("#addQuestionModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增题目，取消题目增加
        $('#cancelAddQuestionBtn').click(function(){
            $("#addQuestionModal").modal('hide');
        });

        //新增题目，确定增加题目
        $('#confirmAddQuestionBtn').click(function(){
            manageQuestionBoardPage.addQuestionAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelUpdateQuestionBtn').click(function(){
            $("#updateQuestionModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmUpdateQuestionBtn').click(function(){
            manageQuestionBoardPage.updateQuestionAction();
        });

        //查詢按鈕触发
        $('#queryQuestionBtn').click(function () {
            manageQuestionBoardPage.queryQuestionAction();
        });

    },
    firstPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=1&content='+content;
    },
    prevPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=' + (pageNum-1)
            + '&content='+content;
    },
    targetPage: function (page) {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page='
            + page + '&content='+content;
    },
    nextPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=' + (pageNum+1)
            + '&content='+content;
    },
    lastPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page='
            + manageQuestionBoardPage.data.totalPageNum + '&content='+content;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (manageQuestionBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageQuestionBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageQuestionBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageQuestionBoardPage.data.pageNum-4 > 0 ? manageQuestionBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > manageQuestionBoardPage.data.totalPageNum ? manageQuestionBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageQuestionBoardPage.data.pageNum);
        console.log('totalPageNum = ' + manageQuestionBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageQuestionBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageQuestionBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageQuestionBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (manageQuestionBoardPage.data.pageNum == manageQuestionBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageQuestionBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageQuestionBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    initAddQuestionData: function () {
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
    checkAddQuestionData: function (questionTitle, questionContent, questionType,
                                    optionA, optionB, optionC, optionD,
                                    questionAnswer, questionParse, questionDifficulty,
                                    questionValue) {
        //TODO::校验
        return true;

    },
    addQuestionAction: function () {
        var questionTitle = $('#questionTitle').val();
        var questionContent = $('#questionContent').val();
        var questionType = $('#questionType').val();
        var subjectId = $('#subjectId').val();
        var optionA = $('#optionA').val();
        var optionB = $('#optionB').val();
        var optionC = $('#optionC').val();
        var optionD = $('#optionD').val();
        var questionAnswer = $('#questionAnswer').val();
        var questionParse = $('#questionParse').val();
        var questionDifficulty = $('#questionDifficulty').val();
        var questionScore = $('#questionScore').val();

        if (manageQuestionBoardPage.checkAddQuestionData(questionTitle, questionContent,
                questionType, optionA,optionB, optionC, optionD, questionAnswer, questionParse,
                questionDifficulty, questionScore)) {
            $.ajax({
                url : app.URL.addQuestionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId,
                    contestId: 0,
                    score: questionScore,
                    difficulty: questionDifficulty,
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
    //编辑题目模态框触发
    updateQuestionModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        manageQuestionBoardPage.initUpdateQuestionData(index);
        $("#updateQuestionModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdateQuestionData: function (index) {
        //初始化数据
        $('#updateQuestionIndex').val(index);
        $('#updateQuestionTitle').val(questions[index].title);
        $('#updateQuestionContent').val(questions[index].content);
        var selectQuestionTypes = document.getElementById('updateQuestionType');
        for (var i = 0; i < selectQuestionTypes.length; i++) {
            if (selectQuestionTypes[i].value == questions[index].questionType) {
                selectQuestionTypes[i].selected = true;
            }
        }
        var selectSubjectIds = document.getElementById('updateSubjectId');
        for (var i = 0; i < selectSubjectIds.length; i++) {
            if (selectSubjectIds[i].value == questions[index].subjectId) {
                selectSubjectIds[i].selected = true;
            }
        }
        $('#updateOptionA').val(questions[index].optionA);
        $('#updateOptionB').val(questions[index].optionB);
        $('#updateOptionC').val(questions[index].optionC);
        $('#updateOptionD').val(questions[index].optionD);
        $('#updateQuestionAnswer').val(questions[index].answer);
        $('#updateQuestionParse').val(questions[index].parse);
        var selectQuestionDifficulty = document.getElementById('updateQuestionDifficulty');
        for (var i = 0; i < selectQuestionDifficulty.length; i++) {
            if (selectQuestionDifficulty[i].value == questions[index].difficulty) {
                selectQuestionDifficulty[i].selected = true;
            }
        }
        $('#updateQuestionScore').val(questions[index].score);
    },
    checkUpdateQuestionData: function (questionTitle, questionContent,
                                       questionType, optionA,optionB, optionC, optionD, questionAnswer, questionParse,
                                       questionDifficulty, questionScore) {
        //TODO::校验
        var flag = false;
        var msg ='';
        if (questionTitle == null || questionTitle == '' || questionTitle.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '名称不能为空!'
        }
        if (questionContent == null || questionContent == '' || questionContent.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '试卷类型不能为空!'
        }
        if (questionAnswer == null || questionAnswer == '' || questionAnswer.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '部门不能为空!'
        }
        if (questionParse == null || questionParse == '' || questionParse.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '总分值不能为空!'
        }
        if (questionScore == null || questionScore == '' || questionScore.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '难度设置不能为空!'
        }
        if(!flag){
            layer.open({
                title: '温馨提示',
                content: msg
            });
        }
        flag = true ;
        return flag;

    },
    updateQuestionAction: function () {
        var index = $('#updateQuestionIndex').val();
        var questionTitle = $('#updateQuestionTitle').val();
        var questionContent = $('#updateQuestionContent').val();
        var questionType = $('#updateQuestionType').val();
        var subjectId = $('#updateSubjectId').val();
        var optionA = $('#updateOptionA').val();
        var optionB = $('#updateOptionB').val();
        var optionC = $('#updateOptionC').val();
        var optionD = $('#updateOptionD').val();
        var questionAnswer = $('#updateQuestionAnswer').val();
        var questionParse = $('#updateQuestionParse').val();
        var questionDifficulty = $('#updateQuestionDifficulty').val();
        var questionScore = $('#updateQuestionScore').val();

        if (manageQuestionBoardPage.checkUpdateQuestionData(questionTitle, questionContent,
                questionType, optionA,optionB, optionC, optionD, questionAnswer, questionParse,
                questionDifficulty, questionScore)) {
            $.ajax({
                url : app.URL.updateQuestionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: questions[index].id,
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId,
                    contestId: 0,
                    score: questionScore,
                    difficulty: questionDifficulty,
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
    deleteQuestionAction: function (index) {
        $.ajax({
            url : app.URL.deleteQuestionUrl()+index,
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
    },
    //查询
    queryQuestionAction: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=1&content='+content;
    },


};