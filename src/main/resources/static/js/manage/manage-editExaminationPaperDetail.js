/**
 * 模块化JavaScript
 **/
var manageEditExaminationPaperDetailPage = {
    data:{
        examinationPaper: [],
        examinationPaperDetails: [],
        questions: [],
    },
    init: function (examinationPaper, examinationPaperDetails,questions) {
        manageEditExaminationPaperDetailPage.data.examinationPaper = examinationPaper;
        manageEditExaminationPaperDetailPage.data.examinationPaperDetails = examinationPaperDetails;
        manageEditExaminationPaperDetailPage.data.questions = questions;

        //新增题目，弹出新增窗口
        $("#addBtn").click(function () {
            //输入框初始化数据
            manageEditExaminationPaperDetailPage.initAddData();
            $("#addModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增题目，取消题目增加
        $('#cancelAddBtn').click(function(){
            $("#addModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddBtn').click(function(){
            manageEditExaminationPaperDetailPage.addAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelUpdateBtn').click(function(){
            $("#updateModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmUpdateBtn').click(function(){
            manageEditExaminationPaperDetailPage.updateAction();
        });

    },
    initAddData: function () {

        $('#question_id').val("");
        $('#detailScore').val();
    },
    checkAddData: function (question_id,questionScore,contestId) {
        //TODO::校验
        return true;

    },
    addAction: function () {

        var question_id = $('#question_id').val();
        var questionScore = $('#detailScore').val();
        var contestId = manageEditExaminationPaperDetailPage.data.examinationPaper.id;
        console.log(question_id,questionScore,contestId)
        if (manageEditExaminationPaperDetailPage.checkAddData(question_id,questionScore,contestId)) {
            $.ajax({
                url : app.URL.addPaperDetailUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({

                    question_id: question_id,
                    paper_id: contestId,
                    score: questionScore,
                }),
                success:function(result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>'+result.message+'</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error:function(result){
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },
    //编辑题目模态框触发
    updateModalAction: function (index) {


        //编辑考试，弹出编辑窗口
        console.log(index);
        //输入框初始化数据
        manageEditExaminationPaperDetailPage.initUpdateData(index);
        $("#updateModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdateData: function (index) {
        //初始化数据
        var examinationPaperDetails = manageEditExaminationPaperDetailPage.data.examinationPaperDetails;
        console.log(examinationPaperDetails);
        $('#updatePaperDetailIndex').val(examinationPaperDetails[index].id);
        $('#updatequestion_id').val( examinationPaperDetails[index].question_id);
        $('#updatescore').val(examinationPaperDetails[index].score);
        $('#updatePaperIndex').val(examinationPaperDetails[index].paper_id);
        var question_ids = document.getElementById('updatequestion_id');
        for (var i = 0; i < question_ids.length; i++) {
            if (question_ids[i].value == examinationPaperDetails[index].question_id) {
                question_ids[i].selected = true;
            }
        }

    },
    checkUpdateData: function (index,paper_id,question_id,score) {
        //TODO::校验
        return true;

    },
    updateAction: function () {
        var index = $('#updatePaperDetailIndex').val();
        var paper_id = $('#updatePaperIndex').val();
        var question_id = $('#updatequestion_id').val();
        var score = $('#updatescore').val();


        if (manageEditExaminationPaperDetailPage.checkUpdateData(index,paper_id,question_id,score)) {
            $.ajax({
                url : app.URL.updatePaperDetailUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: index,
                    question_id: question_id,
                    paper_id: paper_id,
                    score: score,
                }),
                success:function(result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>'+result.message+'</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error:function(result){
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },
    deleteAction: function (index) {
        $.ajax({
            url : app.URL.deletePaperDetailUrl()+ index,
            type : "DELETE",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            success:function(result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            },
            error:function(result){
                $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                    '                <p>'+result.message+'</p>');
                $('#loginModalErrorMessage').removeClass('hidden');
            }
        });
    }






};