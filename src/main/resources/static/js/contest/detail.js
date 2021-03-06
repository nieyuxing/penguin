/**
 * 模块JavaScript
 */
var contestDetailPage = {
    data:{
        currentQuestionIndex: 0,
        contest: null,
        answer: null,
        questions: [],
    },
    init: function (contest, questions,answer) {
        contestDetailPage.data.contest = contest;
        contestDetailPage.data.questions = questions;
        contestDetailPage.data.answer = answer;

        //console.log(contestDetailPage.data.questions);

        //TODO::考试时间倒计时
        $("#contestTimeCountdown").countdown(new Date(contestDetailPage.data.contest.endTime), function (event) {
            // 事件格式
            var format = event.strftime("%D:%H:%M:%S");
            //console.log(format);
            $("#contestTimeCountdown").html(format);
        }).on('finish.countdown', function () {
            // TODO::交卷事件触发
            contestDetailPage.finishContestAction();
        });

        if (contestDetailPage.data.questions[0].questionType == 0) {
            var tmpHtml = '(单选)'+contestDetailPage.data.questions[0].content+'('+contestDetailPage.data.questions[0].score+'分)\n';
            var imgUrl = contestDetailPage.data.questions[0].imgUrl;
            if(imgUrl != null && imgUrl!=undefined){
                tmpHtml += '\n<img class="ui" src="'+contestDetailPage.data.questions[0].imgUrl + '" width="600" height="320" alt="" />'
            }
            $('#currentQuetionTitle').html(tmpHtml);
            var expHtml = '温馨提示:'+contestDetailPage.data.questions[0].tips+'\n';
            if(contestDetailPage.data.questions[0].tips != null && contestDetailPage.data.questions[0].tips!= undefined){
                $('#currentTips').html(expHtml);
            }
            var selectOptionStr =
                    '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionA+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionB+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionC+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionD+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
        } else if (contestDetailPage.data.questions[0].questionType == 1) {
            var tmpHtml = '(多选)'+contestDetailPage.data.questions[0].content+'('+contestDetailPage.data.questions[0].score+'分)\n';
            var imgUrl = contestDetailPage.data.questions[0].imgUrl;
            if(imgUrl != null && imgUrl!=undefined){
                tmpHtml += '<img class="ui" src="'+contestDetailPage.data.questions[0].imgUrl + '" width="600" height="320" alt="" />'
            }
            $('#currentQuetionTitle').html(tmpHtml);
            var expHtml = '温馨提示:'+contestDetailPage.data.questions[0].tips+'\n';
            if(contestDetailPage.data.questions[0].tips != null && contestDetailPage.data.questions[0].tips!= undefined){
                $('#currentTips').html(expHtml);
            }
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionA+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionB+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionC+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;'+contestDetailPage.data.questions[0].optionD+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
        } else {
            var tmpHtml = '(问答)'+contestDetailPage.data.questions[0].content+'('+contestDetailPage.data.questions[0].score+'分)\n';
            var imgUrl = contestDetailPage.data.questions[0].imgUrl;
            if(imgUrl != null && imgUrl!=undefined){
                tmpHtml += '<img class="ui" src="'+contestDetailPage.data.questions[0].imgUrl + '" width="600" height="320" alt="" />'
            }
            $('#currentQuetionTitle').html(tmpHtml);
            var expHtml = '温馨提示:'+contestDetailPage.data.questions[0].tips+'\n';
            if(contestDetailPage.data.questions[0].tips != null && contestDetailPage.data.questions[0].tips!= undefined){
                $('#currentTips').html(expHtml);
            }
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  id="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
        }
        var currentQuestionButtonStr = '';
        for (var i = 0; i < questions.length; i++) {
            var buttonStr = '';
            if (contestDetailPage.data.currentQuestionIndex == i) buttonStr = '<button class="mini ui positive button" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            else buttonStr = '<button class="mini ui button" onclick="contestDetailPage.targetQuestionAction('+i+')" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            currentQuestionButtonStr += buttonStr;
        }
        $('#currentQuestionButton').html(currentQuestionButtonStr);
    },
    targetQuestionAction: function (index) {
        var preIndex = contestDetailPage.data.currentQuestionIndex;
        contestDetailPage.data.currentQuestionIndex = index;

        //记录答案
        if (contestDetailPage.data.questions[preIndex].questionType == 0) {
            contestDetailPage.data.questions[preIndex].answer = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                contestDetailPage.data.questions[preIndex].answer += $(this).val();
            });
        } else if (contestDetailPage.data.questions[preIndex].questionType == 1) {
            contestDetailPage.data.questions[preIndex].answer = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                //console.log($(this).val());
                contestDetailPage.data.questions[preIndex].answer += $(this).val();
            });
        } else {
            //console.log($("#questionAnswer").val());
            contestDetailPage.data.questions[preIndex].answer = $("#questionAnswer").val();
        }

        if (contestDetailPage.data.questions[index].questionType == 0) {
            var tmpHtml = '(单选)'+contestDetailPage.data.questions[index].content+'('+contestDetailPage.data.questions[index].score+'分)\n';
            var imgUrl = contestDetailPage.data.questions[index].imgUrl;
            console.log("imgUrl:",imgUrl);
            if(imgUrl != null && imgUrl!=undefined){
                tmpHtml += '<img class="ui" src="'+contestDetailPage.data.questions[index].imgUrl + '" width="600" height="320" alt="" />'
            }
            $('#currentQuetionTitle').html(tmpHtml);
            var expHtml = '温馨提示:'+contestDetailPage.data.questions[index].tips+'\n';
             $('#currentTips').html(expHtml);
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionA+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionB+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionC+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionD+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questions[index].answer != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questions[index].answer.indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[index].questionType == 1) {
            var tmpHtml = '(多选)'+contestDetailPage.data.questions[index].content+'('+contestDetailPage.data.questions[index].score+'分)\n';
            var imgUrl = contestDetailPage.data.questions[index].imgUrl;
            console.log("imgUrl:",imgUrl);
            if(imgUrl != null && imgUrl!=undefined){
                tmpHtml += '<img class="ui" src="'+contestDetailPage.data.questions[index].imgUrl + '" width="600" height="320" alt="" />'
            }
            $('#currentQuetionTitle').html(tmpHtml);
            var expHtml = '温馨提示:'+contestDetailPage.data.questions[index].tips+'\n';
                         $('#currentTips').html(expHtml);
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionA+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionB+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionC+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;'+contestDetailPage.data.questions[index].optionD+'</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questions[index].answer != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questions[index].answer.indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else {
            var tmpHtml = '(问答)'+contestDetailPage.data.questions[index].content+'('+contestDetailPage.data.questions[index].score+'分)\n';
            var imgUrl = contestDetailPage.data.questions[index].imgUrl;
            console.log("imgUrl:",imgUrl);
            if(imgUrl != null && imgUrl!=undefined){
                tmpHtml += '<img class="ui" src="'+contestDetailPage.data.questions[index].imgUrl + '" width="600" height="320" alt="" />'
            }
            $('#currentQuetionTitle').html(tmpHtml);
            var expHtml = '温馨提示:'+contestDetailPage.data.questions[index].tips+'\n';
                         $('#currentTips').html(expHtml);
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  id="questionAnswer" name="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questions[index].answer != '') {
                $('#questionAnswer').val(contestDetailPage.data.questions[index].answer);
            }
        }


        var currentQuestionButtonStr = '';
        for (var i = 0; i < contestDetailPage.data.questions.length; i++) {
            var buttonStr = '';
            if (contestDetailPage.data.currentQuestionIndex == i) {
                buttonStr = '<button class="mini ui positive button" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            } else if (contestDetailPage.data.questions[i].answer != '') {
                // console.log(i);
                // console.log(contestDetailPage.data.questions[i]);
                buttonStr = '<button class="mini ui green basic button" onclick="contestDetailPage.targetQuestionAction('+i+')" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            } else {
                buttonStr = '<button class="mini ui button" onclick="contestDetailPage.targetQuestionAction('+i+')" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            }
            currentQuestionButtonStr += buttonStr;
        }
        $('#currentQuestionButton').html(currentQuestionButtonStr);
    },
    //交卷事假触发
    finishContestAction: function () {
        var index = contestDetailPage.data.currentQuestionIndex;
        //记录答案
        if (contestDetailPage.data.questions[index].questionType == 0) {
            contestDetailPage.data.questions[index].answer = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                contestDetailPage.data.questions[index].answer += $(this).val();
            });
        } else if (contestDetailPage.data.questions[index].questionType == 1) {
            contestDetailPage.data.questions[index].answer = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                //console.log($(this).val());
                contestDetailPage.data.questions[index].answer += $(this).val();
            });
        } else {
            //console.log($("#questionAnswer").val());
            contestDetailPage.data.questions[index].answer = $("#questionAnswer").val();
        }
        //TODO::交卷
        contestDetailPage.submittingContestAction();
    },
    //正在交卷
    submittingContestAction: function () {
        $('#waitSubmitModal').modal({
            /**
             * 必须点击相关按钮才能关闭
             */
            closable  : false,
            /**
             * 模糊背景
             */
            blurring: true,
        }).modal('show');
        for (var i = 0; i < contestDetailPage.data.questions.length; i++) {
            console.log(contestDetailPage.data.questions[i].answer);
        }

        var answerJsonStr = '';
        for (var i = 0; i < contestDetailPage.data.questions.length; i++) {
            if(contestDetailPage.data.questions[i].answer == null || contestDetailPage.data.questions[i].answer ==''){
                answerJsonStr +='-'
            }else{
                answerJsonStr += contestDetailPage.data.questions[i].answer;

            }

            if (i < contestDetailPage.data.questions.length-1) {
                answerJsonStr += '_~_';
            }
        }
        console.log("answerJson = " + answerJsonStr);

        //向后端API发送答题卡
        $.ajax({
            url : app.URL.submitGradeUrl(),
            type : "POST",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            <!-- 向后端传输的数据 -->
            data : JSON.stringify({
                contestId: contestDetailPage.data.contest.id,
                answerJson: answerJsonStr,
                answerId: contestDetailPage.data.answer.id,
            }),
            success:function(result) {
                if (result && result['success']) {
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

        setTimeout(function () {
            $("#waitSubmitModal").modal("hide");
            window.location.href = app.URL.homeUrl();
        }, 5000);
    },

};