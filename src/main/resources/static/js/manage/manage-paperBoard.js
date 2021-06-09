/**
 * 模块化JavaScript
 **/
var managePaperBoardPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        papers: [],
        departments:[],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, papers,departments) {
        managePaperBoardPage.data.pageNum = pageNum;
        managePaperBoardPage.data.pageSize = pageSize;
        managePaperBoardPage.data.totalPageNum = totalPageNum;
        managePaperBoardPage.data.totalPageSize = totalPageSize;
        managePaperBoardPage.data.papers = papers;
        managePaperBoardPage.data.departments=departments;

        console.log("dshak：",departments);
        //分页初始化
        managePaperBoardPage.subPageMenuInit();

        //新增考试，弹出新增窗口
        $("#addPaperBtn").click(function () {
            //输入框初始化数据
            managePaperBoardPage.initAddPaperData();
            $("#addPaperModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增考试，取消考试增加
        $('#cancelAddPaperBtn').click(function(){
            $("#addPaperModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddPaperBtn').click(function(){
            managePaperBoardPage.addPaperAction();
        });

        //编辑考试，取消考试编辑
        $('#cancelUpdatePaperBtn').click(function(){
            $("#updatePaperModal").modal('hide');
        });

        //编辑考试，确定保存考试
        $('#confirmUpdatePaperBtn').click(function(){
            managePaperBoardPage.updatePaperAction();
        });

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
        window.location.href = app.URL.managePaperListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.managePaperListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.managePaperListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.managePaperListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.managePaperListUrl() + '?page=' + managePaperBoardPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (managePaperBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePaperBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="managePaperBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (managePaperBoardPage.data.pageNum-4 > 0 ? managePaperBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > managePaperBoardPage.data.totalPageNum ? managePaperBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + managePaperBoardPage.data.pageNum);
        console.log('totalPageNum = ' + managePaperBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == managePaperBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="managePaperBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="managePaperBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (managePaperBoardPage.data.pageNum == managePaperBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePaperBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="managePaperBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    initAddPaperData: function () {
        //初始化数据
        $('#exam_name').val("");
        $('#examination_type').val("");
        $('#department_id').val("");
        $('#score').val("0");
        $('#difficulty').val("");
        $('#startDate').val("");
        $('#endDate').val("");
    },
    checkAddPaperData: function (exam_name, examination_type, department_id,  score,difficulty) {
        var flag = false;
        var msg ='';
        if (exam_name == null || exam_name == '' || exam_name.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '试卷名称不能为空!'
        }
        if (examination_type == null || examination_type == '' || examination_type.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '试卷类型不能为空!'
        }
        if (department_id == null || department_id == '' || department_id.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '部门不能为空!'
        }
        if (score == null || score == '' || score.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '总分值不能为空!'
        }
        if (difficulty == null || difficulty == '' || difficulty.replace(/(^s*)|(s*$)/g, "").length == 0) {
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
    addPaperAction: function () {
        var exam_name = $('#addexam_name').val();
        var examination_type = $('#addexamination_type').val();
        var department_id = $('#adddepartment_id').val();
        var score = $('#addscore').val();
        var difficulty = $('#adddifficulty').val();
        var startDate = new Date($('#addstartDate').val());
        var endDate = new Date($('#addendDate').val());
        console.log(exam_name);
        if (managePaperBoardPage.checkAddPaperData(exam_name, examination_type, department_id,  score,difficulty)) {
            $.ajax({
                url : app.URL.addPaperUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    exam_name: exam_name,
                    examination_type: examination_type,
                    department_id: department_id,
                    score: score,
                    difficulty: difficulty,
                    endDate:endDate,
                    startDate:startDate,
                    state: 0,
                    score: score,
                    create_by:1,
                    update_by:1,
                    version:1,
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
    updatePaperModalAction: function (index) {
        //输入框初始化数据
        managePaperBoardPage.initUpdatePaperData(index);
        $("#updatePaperModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdatePaperData: function (index) {
        //初始化数据
        var papers = managePaperBoardPage.data.papers;
        $('#updatePaperIndex').val(papers[index].id);
        console.log(papers[index].id);
        $('#updateexam_name').val(papers[index].exam_name);
        $('#updateexamination_type').val(papers[index].examination_type);
        $('#updatedepartment_id').val(papers[index].department_id);
        $('#updatescore').val(papers[index].score);
        $('#updatedifficulty').val(papers[index].difficulty);
        $('#updatestartDate').val(app.formatTime(papers[index].startDate, "Y-M-D h:m:s"));
        $('#updateendDate').val(app.formatTime(papers[index].endDate, "Y-M-D h:m:s"));
        var states = document.getElementById('updatestate');
        for (var i = 0; i < states.length; i++) {
            if (states[i].value == papers[index].state) {
                states[i].selected = true;
            }
        }
        var departments = document.getElementById('updatedepartment_id');
        for (var i = 0; i < departments.length; i++) {
            if (departments[i].value == papers[index].department_id) {
                departments[i].selected = true;
            }
        }




    },
    checkUpdatePaperData: function (exam_name, examination_type, department_id,  score,difficulty) {
        var flag = false;
        var msg ='';
        if (exam_name == null || exam_name == '' || exam_name.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '试卷名称不能为空!'
        }
        if (examination_type == null || examination_type == '' || examination_type.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '试卷类型不能为空!'
        }
        if (department_id == null || department_id == '' || department_id.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '部门不能为空!'
        }
        if (score == null || score == '' || score.replace(/(^s*)|(s*$)/g, "").length == 0) {
            msg = '总分值不能为空!'
        }
        if (difficulty == null || difficulty == '' || difficulty.replace(/(^s*)|(s*$)/g, "").length == 0) {
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
    updatePaperAction: function () {

        var index = $('#updatePaperIndex').val();
        var exam_name = $('#updateexam_name').val();
        var examination_type = $('#updateexamination_type').val();
        var department_id = $('#updatedepartment_id').val();
        var score = $('#updatescore').val();
        var state = $('#updatestate').val();
        var difficulty = $('#updatedifficulty').val();
        var startDate = new Date($('#updatestartDate').val());
        var endDate = new Date($('#updateendDate').val());
        console.log(index,exam_name, examination_type, department_id,  score,difficulty);
        if (managePaperBoardPage.checkUpdatePaperData(exam_name, examination_type, department_id,  score,difficulty)) {

            console.log("22");
            $.ajax({
                url : app.URL.updatePaperUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id:index,
                    exam_name: exam_name,
                    examination_type: examination_type,
                    department_id: department_id,
                    score: score,
                    difficulty: difficulty,
                    endDate:endDate,
                    startDate:startDate,
                    state: state,
                    score: score,
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
    deletePaperAction: function (index) {
        $.ajax({
            url : app.URL.deletePaperUrl()+index,
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
    },
    disabledAction: function (index) {
        $.ajax({
            url : app.URL.disabledPaperUrl()+index,
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
    abledAction: function (index) {
        $.ajax({
            url : app.URL.abledPaperUrl()+index,
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