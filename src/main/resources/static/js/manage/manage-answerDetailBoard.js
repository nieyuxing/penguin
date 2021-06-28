/**
 * 模块化JavaScript
 **/
var manageAnswerDetailBoardPage = {
    data:{
        answer: null,
        answerDetails: [],
    },
    init: function (answer, answerDetails) {
        manageAnswerDetailBoardPage.data.answer = answer;
        manageAnswerDetailBoardPage.data.answerDetails = answerDetails;

        //评分，取消分数编辑
        $('#cancelUpdateAnswerDetailBtn').click(function(){
            $("#updateAnswerDetailModal").modal('hide');
        });

        //评分，确定保存分数
        $('#confirmUpdateAnswerDetailBtn').click(function(){
            manageAnswerDetailBoardPage.updateAnswerDetailAction();
        });
    },
    initUpdateAnswerDetailData: function (index) {
        var answerDetails = manageAnswerDetailBoardPage.data.answerDetails;
        var answer = manageAnswerDetailBoardPage.data.answer;
        console.log(answerDetails);

        $('#gradeTitle').html(answer.user.name+'的主观题答题卡');

        var gradeBodyStr = '<input type="hidden" id="updateAnswerDetailIndex" value="'+index+'"/>';
        gradeBodyStr += '<div class="row">\n' +
            '                    <form class="form-horizontal" role="form" onsubmit="return false;">\n' +
            '                        <div class="form-group">\n' +
            '                            <label class="col-sm-2 control-label">主观题得分</label>\n' +
            '                            <div class="col-sm-8">\n' +
            '                                <input id="gradeManulResult" type="text" class="form-control" placeholder="" />\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </form>\n' +
            '                </div>';
        for (var i = 0; i < answerDetails.length; i++) {
            if (answerDetails[i].question.questionType >= 2) {
                console.log(answerDetails[i].question.questionType);
                gradeBodyStr += '<div class="row">\n' +
                    '                    <form class="form-horizontal" role="form" onsubmit="return false;">\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 control-label">题号</label>\n' +
                    '                            <div class="col-sm-8">\n' +
                    '                                <input value="'+(i+1)+'" readonly="readonly" type="text" class="form-control" placeholder="" />\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 control-label">考生答案</label>\n' +
                    '                            <div class="col-sm-8">\n' +
                    '                                <textarea onscroll="this.rows++;" class="form-control" readonly="readonly">'+answerDetails[i].answer+'</textarea>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 control-label">参考答案</label>\n' +
                    '                            <div class="col-sm-8">\n' +
                    '                                <textarea onscroll="this.rows++;" class="form-control" readonly="readonly">'+answerDetails[i].question.answer+'</textarea>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div class="form-group">\n' +
                    '                            <label class="col-sm-2 control-label">分值</label>\n' +
                    '                            <div class="col-sm-8">\n' +
                    '                                <input value="'+answerDetails[i].score+'" id = "'+answerDetails[i].id+'" type="text" required="true" class="form-control" placeholder="" />\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </form>\n' +
                    '                    <h5 class="page-header"></h5>\n' +
                    '                </div>';
            }
        }

        $('#gradeBody').html(gradeBodyStr);
    },
    //编辑成绩模态框触发
    updateAnswerDetailModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        manageAnswerDetailBoardPage.initUpdateAnswerDetailData(index);
        $("#updateAnswerDetailModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },

    checkAnswerDetailData: function (index,score) {
        var flag = false;
        var msg ='';
        if (index == null || index == '' ) {
            msg = '题号不能为空!'
        }
        if (score == null || score == '' ) {
            msg = '得分不能为空!'
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
    updateAnswerDetailAction: function () {
        var answerDetails =  manageAnswerDetailBoardPage.data.answerDetails;
        var fiscore = '';
        for (var i = 0; i < answerDetails.length; i++) {
            var index = answerDetails[i].id;
            var score = $('#'+index+'').val();
            console.log(index ,score);

            if(manageAnswerDetailBoardPage.checkAnswerDetailData(index ,score)){
                fiscore += '#'+index + '@' +score;
            }

        }
        console.log(fiscore);

        $.ajax({
            url : app.URL.updateAnswerDetailUrl(),
            type : "POST",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            <!-- 向后端传输的数据 -->
            data : JSON.stringify({
                upJson: fiscore,
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
    },

    finishAnswerAction: function (contestId) {
        $.ajax({
            url : app.URL.finishAnswerUrl()+contestId,
            type : "POST",
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
};