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
            return app.data.contextPath+"/user/api/userlogin";
        },
        checkRegisterUrl: function () {
                    return app.data.contextPath+"/user/api/addUser";
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
        problemListUrl: function (problemsetId) {
            return app.data.contextPath+"/problemset/"+problemsetId+"/problems";
        },
        contestIndexUrl: function () {
            return app.data.contextPath+"/paper/index";
        },
        contestDetailUrl: function () {
            return app.data.contextPath+"/answer/";
        },
        updateAccountUrl: function () {
            return app.data.contextPath+"/account/api/updateAccount";
        },
        updatePasswordUrl: function () {
            return app.data.contextPath+"/account/api/updatePassword";
        },
        submitGradeUrl: function () {
            return app.data.contextPath+"/grade/api/submitContest"
        },
        addPostUrl: function () {
            return app.data.contextPath+"/post/api/addPost"
        },
        updatePostUrl: function () {
            return app.data.contextPath+"/post/api/updatePost"
        },
        deletePostUrl: function () {
            return app.data.contextPath+"/post/api/deletePost/";
        },
        discussUrl: function () {
            return app.data.contextPath+"/discuss";
        },
        addCommentUrl: function () {
            return app.data.contextPath+"/comment/api/addComment";
        },
        addReplyUrl: function () {
            return app.data.contextPath+"/reply/api/addReply";
        },
        myDiscussPostUrl: function () {
            return app.data.contextPath+"/account/myDiscussPost";
        },
        myExamUrl: function () {
            return app.data.contextPath+"/account/myExam";
        },
        uploadAvatarUrl: function () {
            return app.data.contextPath+'/account/api/uploadAvatar' ;
        },
        uploadImageUrl: function () {
            return app.data.contextPath+'/upload/images/';
        },
        uploadResume: function () {
            return app.data.contextPath+'/manage/upload';
        },
    },
    /**
     * 全局初始化:服务器时间获取,登录功能,退出登录
     */
    init: function (contextPath) {
        app.data.contextPath = contextPath;
        $.get(app.URL.now(), {}, function (result) {
            if (result && result['success']) {
                //console.log(result['data']);
                app.data.nowTime = app.toTimeStamp(result['data']);
                console.log("服务器当前的时间==========" + app.data.nowTime);
            } else {
                console.log('结果:' + result);
                console.log('result' + result);
            }
        });
        /**
         * 服务器时间动态显示
         */
        window.setInterval("app.run();", 1000);
        /**
         * 记住登录开关效果
         */
        $('.ui.toggle.checkbox').checkbox();
        /**
         * 登录模态框关闭按钮触发
         */
        $('#loginModalCloseButton').click(function (e) {
            // console("nidaye");
            $('#loginModal').modal('hide');
        });

        $('#cancelAddAccountBtn').click(function (e) {
            $('#registerModal').modal('hide');
        });
        /**
         * 登录模态框登录按钮触发
         */
        $('#loginModalSubmitButton').click(function (e) {
            app.checkLogin();
        });
        $('#confirmAddAccountBtn').click(function (e) {
                    app.checkRegister();
        });
        /**
         * 退出登录
         */
        $('#logout').click(function (e) {
            window.location.href = app.URL.logoutUrl();
        });
        /**
         * 登录错误提示消息可关闭
         */
        $('#loginModalErrorMessage,.close').on('click', function() {
            $(this).closest('#loginModalErrorMessage').transition('fade');
            //$('#loginModalErrorMessage').addClass('hidden');
        });
        /**
         * 注册错误提示消息可关闭
         */
        $('#registeredErrorMessage,.close').on('click', function() {
            $(this).closest('#registeredErrorMessage').transition('fade');
            //$('#registeredErrorMessage').addClass('hidden');
        });

        

        $('.ui.form').form({
            fields: {
                name: {
                    identifier: 'name',
                    rules: [{type   : 'empty',}]
                },
                password: {
                    identifier: 'password',
                    rules: [{type   : 'empty',}]
                },
                vchat: {
                    identifier: 'vchat',
                    rules: [{type   : 'empty',}]
                },
                qq: {
                    identifier: 'qq',
                    rules: [{type   : 'empty',}]
                },
                phone: {
                    identifier: 'phone',
                    rules: [{type   : 'empty',}]
                },
                email: {
                    identifier: 'email',
                    rules: [{type   : 'empty',}]
                },
            }
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
    /**
     * 服务器时间
     */
    run: function () {
        this.data.nowTime = this.data.nowTime + 1000;
        //console.log("this.data.nowTime = " + this.data.nowTime);
        document.getElementById("current_server_timer").innerHTML =
            app.formatTime(this.data.nowTime, "Y-M-D h:m:s");
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

    showRegister: function () {        
        $('#addUsername').val("");
        $('#addPassword').val("");
        $('#registerModal').modal({
            /**
             * 必须点击相关按钮才能关闭
             */
            closable  : false,
            /**
             * 模糊背景
             */
            blurring: false,
        }).modal('show');
    },
    /**
     * 验证用户名和密码是否合法
     */
    checkUsernameAndPassword: function (phone, password) {
        if (phone == null || phone == ''
            || phone.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>'+'手机号不能为空'+'</p>');
            $('#loginModalErrorMessage').removeClass('hidden');
            return false;
        }
        if (password == null || password == ''
            || password.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>'+'密码不能为空'+'</p>');
            $('#loginModalErrorMessage').removeClass('hidden');
            return false;
        }
        return true;
    },
    /**
     * 验证登录
     */
    checkLogin: function (phoneNumber,passwordValue) {
        var phone = $('#phoneNum').val() || phoneNumber;
        console.log("phoneNum",phone);
        var password = $('#pwd').val() || passwordValue;
        if (app.checkUsernameAndPassword(phone, password)) {
            //调用后端API
            $.post(app.URL.checkLoginUrl(), {
                phone: phone,
                password: password
            }, function (result) {
                // console.log("result.success = " + result.success);
                // console.log("result.success = " + result['success']);
                // console.log(result);
                if (result && result['success']) {
                    if ($('#rememberMe').is(":checked")) {
                        // 把账号信息记入cookie
                        $.cookie('penguinUsername', phone, {expires: 7, path: '/'});
                        $.cookie('penguinPassword', password, {expires: 7, path: '/'});
                    }
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            }, "json");
        }
    },
    /**
     * 验证注册
     */
    checkRegister: function () {
        var name = $('#name').val();
        var password = $('#password').val();
        var vchat = $('#vchat').val();
        var qq = $('#qq').val();
        var phone = $('#phone').val();
        var email = $('#email').val();
        if (app.checkUsernameAndPassword(phone, password)) {
            //调用后端API
            $.post(app.URL.checkRegisterUrl(), {
                name: name,
                password: password,
                vchat: vchat,
                qq: qq,
                phone: phone,
                email: email,

            }, function (result) {
                if (result.data === 1 && result.success) {
                    app.checkLogin(phone, password);
                    $('#registerModal').modal('hide');
                    // window.location.reload();
                } else {
                    $('#registeredErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +'<p>'+result.message+'</p>');
                    $('#registeredErrorMessage').removeClass('hidden');
                }
                console.log(result);
            }, "json");
        }
    }
};