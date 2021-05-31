/**
 * 模块JavaScript
 */
var app = {
    data:{
        nowTime: null,
        contextPath: null,
    },
    // 封装相关的ajax的url
    URL: {
        now: function () {
            return app.data.contextPath+"/time/now";
        },
        checkLoginUrl: function () {
            return app.data.contextPath+"/account/api/login";
        },
        logoutUrl: function () {
            return app.data.contextPath+"/account/logout";
        },
        homeUrl: function () {
            return app.data.contextPath+"/";
        },
        problemsetUrl: function () {
            return app.data.contextPath+"/problemset/list";
        },
        updateAccountUrl: function () {
            return app.data.contextPath+"/account/api/updateAccount";
        },
        updatePasswordUrl: function () {
            return app.data.contextPath+"/account/api/updatePassword";
        },
        manageContestListUrl: function () {
            return app.data.contextPath+"/manage/contest/list";
        },
        addContestUrl: function () {
            return app.data.contextPath+"/contest/api/addContest"
        },
        updateContestUrl: function () {
            return app.data.contextPath+"/contest/api/updateContest"
        },
        deleteContestUrl: function () {
            return app.data.contextPath+"/contest/api/deleteContest/";
        },
        finishContestUrl: function () {
            return app.data.contextPath+"/contest/api/finishContest/";
        },
        addQuestionUrl: function () {
            return app.data.contextPath+"/question/api/addQuestion";
        },
        updateQuestionUrl: function () {
            return app.data.contextPath+"/question/api/updateQuestion"
        },
        deleteQuestionUrl: function () {
            return app.data.contextPath+"/question/api/deleteQuestion/";
        },
        manageQuestionUrl: function () {
            return app.data.contextPath+"/manage/question/list"
        },
        manageResultContestListUrl: function () {
            return app.data.contextPath+"/manage/result/contest/list";
        },
        manageResultStudentListUrl: function (contestId) {
            return app.data.contextPath+"/manage/result/contest/"+contestId+"/list";
        },
        finishGradeUrl: function () {
            return app.data.contextPath+"/grade/api/finishGrade"
        },
        manageAccountListUrl: function () {
            return app.data.contextPath+"/manage/account/list";
        },
        addAccountUrl: function () {
            return app.data.contextPath+"/account/api/addAccount";
        },
        updateAccountUrl: function () {
            return app.data.contextPath+"/account/api/updateManegeAccount";
        },
        deleteAccountUrl: function () {
            return app.data.contextPath+"/account/api/deleteAccount/";
        },
        abledAccountUrl: function () {
            return app.data.contextPath+"/account/api/abledAccount/";
        },
        disabledAccountUrl: function () {
            return app.data.contextPath+"/account/api/disabledAccount/";
        },
        managepositionListUrl: function () {
            return app.data.contextPath+"/manage/position/list"
        },
        addpositionUrl: function () {
            return app.data.contextPath+"/position/api/addposition";
        },
        updatepositionUrl: function () {
            return app.data.contextPath+"/position/api/updateposition"
        },
        deletepositionUrl: function () {
            return app.data.contextPath+"/position/api/deleteposition/";
        },
        managePostListUrl: function () {
            return app.data.contextPath+"/manage/post/list"
        },
        updatePostUrl: function () {
            return app.data.contextPath+"/post/api/updatePost"
        },
        deletePostUrl: function () {
            return app.data.contextPath+"/post/api/deletePost/";
        },
        manageCommentListUrl: function () {
            return app.data.contextPath+"/manage/comment/list"
        },
        deleteCommentUrl: function () {
            return app.data.contextPath+"/comment/api/deleteComment/";
        },

        //考卷管理
        addPaperUrl: function () {
            return app.data.contextPath+"/paper/api/addPaper/";
        },
        deletePaperUrl: function () {
            return app.data.contextPath+"/paper/api/deletePaper/";
        },
        updatePaperUrl: function () {
            return app.data.contextPath+"/paper/api/updatePaper/";
        },
        abledPaperUrl: function () {
            return app.data.contextPath+"/paper/api/abledPaper/";
        },
        disabledPaperUrl: function () {
            return app.data.contextPath+"/paper/api/disabledPaper/";
        },

        //考卷试题编辑
        addPaperDetailUrl: function () {
            return app.data.contextPath+"/paperDetail/api/addExaminationPaperDetail";
        },
        deletePaperDetailUrl: function () {
            return app.data.contextPath+"/paperDetail/api/deleteExaminationPaperDetail/";
        },
        updatePaperDetailUrl: function () {
            return app.data.contextPath+"/paperDetail/api/updateExaminationPaperDetail";
        },

        //用户管理
        addUserUrl: function () {
            return app.data.contextPath+"/user/api/addUser";
        },
        deleteUserUrl: function () {
            return app.data.contextPath+"/user/api/deleteUser/";
        },
        updateUserUrl: function () {
            return app.data.contextPath+"/user/api/updateUser";
        },
        abledUserUrl: function () {
            return app.data.contextPath+"/user/api/abledUser/";
        },
        disabledUserUrl: function () {
            return app.data.contextPath+"/user/api/disabledUser/";
        },
        rejectedUserUrl: function () {
            return app.data.contextPath+"/user/api/rejectedUser/";
        },
        approvedUserUrl: function () {
            return app.data.contextPath+"/user/api/approvedUser/";
        },

        //考试成绩管理
        addAnswerUrl: function () {
            return app.data.contextPath+"/answer/api/addAnswer";
        },
        deleteAnswerUrl: function () {
            return app.data.contextPath+"/answer/api/deleteAnwser/";
        },
        updateAnswerUrl: function () {
            return app.data.contextPath+"/answer/api/updateAnswer";
        },
        manageAnswerListUrl: function () {
            return app.data.contextPath+"/manage/answer/list";
        },
        manageAnswerDetailListUrl : function () {
            return app.data.contextPath+"/manage/answerDetail/list/";
        },
        finishAnswerUrl: function () {
            return app.data.contextPath+"/answer/api/finishExaminationAnswer/";
        },

        //部门管理
        addDepartmentUrl: function () {
            return app.data.contextPath+"/department/api/addDepartment";
        },
        deleteDepartmentUrl: function () {
            return app.data.contextPath+"/department/api/deleteDepartment/";
        },
        updateDepartmentUrl: function () {
            return app.data.contextPath+"/department/api/updateDepartment";
        },
        manageDepartmentListUrl: function () {
            return app.data.contextPath+"/manage/department/list";
        },

        //考卷试题编辑
        addAnswerDetailUrl: function () {
            return app.data.contextPath+"/answerDetail/api/addAnswerDetaill";
        },
        deleteAnswerDetailUrl: function () {
            return app.data.contextPath+"/answerDetail/api/deleteAnswerDetail/";
        },
        updateAnswerDetailUrl: function () {
            return app.data.contextPath+"/answerDetail/api/updateAnswerDetail";
        },


    },
    /**
     * 全局初始化:服务器时间获取,登录功能,退出登录
     */
    init: function (contextPath) {
        app.data.contextPath = contextPath;
        /**
         * 退出登录
         */
        $('#logout').click(function (e) {
            window.location.href = app.URL.logoutUrl();
        });
    },
    convertTime: function (localDateTime) {
        var year = localDateTime.year;
        var monthValue = localDateTime.monthValue;
        var dayOfMonth = localDateTime.dayOfMonth;
        var hour = localDateTime.hour;
        var minute = localDateTime.minute;
        var second = localDateTime.second;
        return year + "-" + monthValue + "-" + dayOfMonth + " " + hour + ":" + minute + ":" + second;
    },
    toTimeStamp: function (localDateTime) {
        var currTime = localDateTime.year + "-" + localDateTime.monthValue
            + "-" + localDateTime.dayOfMonth + " " + localDateTime.hour
            + ":" + localDateTime.minute + ":" + localDateTime.second;
        //console.log("currTime = " + currTime);
        //console.log("new Date(currTime).valueOf() = " + new Date(currTime).valueOf());
        return new Date(currTime).valueOf();
    },
    /**
     * 时间戳转化为年 月 日 时 分 秒
     * number: 传入时间戳
     * format：返回格式，支持自定义，但参数必须与formateArr里保持一致
     */
    formatTime: function (number, format) {

        var formateArr = ['Y', 'M', 'D', 'h', 'm', 's'];
        var returnArr = [];

        var date = new Date(number);
        returnArr.push(date.getFullYear());
        returnArr.push(app.formatNumber(date.getMonth() + 1));
        returnArr.push(app.formatNumber(date.getDate()));

        returnArr.push(app.formatNumber(date.getHours()));
        returnArr.push(app.formatNumber(date.getMinutes()));
        returnArr.push(app.formatNumber(date.getSeconds()));

        for (var i in returnArr) {
            format = format.replace(formateArr[i], returnArr[i]);
        }
        return format;
    },
    formatNumber: function (n) {
        n = n.toString();
        return n[1] ? n : '0' + n;
    },
};