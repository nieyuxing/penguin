/**
 * 模块化JavaScript
 **/
var managePositionBoardPage = {
    data:{
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        positions: [],
        departments:[],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, positions,departments) {
        managePositionBoardPage.data.pageNum = pageNum;
        managePositionBoardPage.data.pageSize = pageSize;
        managePositionBoardPage.data.totalPageNum = totalPageNum;
        managePositionBoardPage.data.totalPageSize = totalPageSize;
        managePositionBoardPage.data.positions = positions;
        managePositionBoardPage.data.departments = departments;
        //分页初始化
        managePositionBoardPage.subPageMenuInit();

        //新增考试，弹出新增窗口
        $("#addPositionBtn").click(function () {
            //输入框初始化数据
            managePositionBoardPage.initAddPositionData();
            $("#addPositionModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增考试，取消考试增加
        $('#cancelAddPositionBtn').click(function(){
            $("#addPositionModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddPositionBtn').click(function(){
            managePositionBoardPage.addPositionAction();
        });

        //编辑考试，取消考试编辑
        $('#cancelUpdatePositionBtn').click(function(){
            $("#updatePositionModal").modal('hide');
        });

        //编辑考试，确定保存考试
        $('#confirmUpdatePositionBtn').click(function(){
            managePositionBoardPage.updatePositionAction();
        });
    },
    firstPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + (pageNum-1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + (pageNum+1);
    },
    lastPage: function () {
        window.location.href = app.URL.managePositionListUrl() + '?page=' + managePositionBoardPage.data.totalPageNum;
    },
    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (managePositionBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePositionBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="managePositionBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (managePositionBoardPage.data.pageNum-4 > 0 ? managePositionBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > managePositionBoardPage.data.totalPageNum ? managePositionBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + managePositionBoardPage.data.pageNum);
        console.log('totalPageNum = ' + managePositionBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == managePositionBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="managePositionBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="managePositionBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (managePositionBoardPage.data.pageNum == managePositionBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePositionBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="managePositionBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    initAddPositionData: function () {
        //初始化数据
        $('#addname').val("");
        $('#adddepartment_id').val("");
        $('#addposi_code').val("");
        $('#addplace').val("");
        $('#addposi_type').val("");
        $('#adddegree').val("");
        $('#adddepth').val("");
        $('#addpositionNum').val("");
        $('#adddescr').val("");
        $('#addsourceType').val("");

    },
    checkAddPositionData: function (name,department_id,posi_code,place,posi_type,degree,depth,positionNum,descr) {
        var flag = false;
        var msg ='';
        if (name == null || name == '' ) {
            msg = '名称不能为空!'
        }
        if (department_id == null || department_id == '' ) {
            msg = '部门不能为空!'
        }
        if (posi_code == null || posi_code == '' ) {
            msg = '编码不能为空!'
        }
        if (place == null || place == '' ) {
            msg = '招聘地点不能为空!'
        }
        if (posi_type == null || posi_type == '' ) {
            msg = '职位类型不能为空!'
        }
        if (degree == null || degree == '' ) {
            msg = '学历不能为空!'
        }
        if (depth == null || depth == '' ) {
            msg = '工作年限不能为空!'
        }
        if (positionNum == null || positionNum == '' ) {
            msg = '招聘人数不能为空!'
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
    addPositionAction: function () {
        var name =$('#addname').val();
        var department_id =$('#adddepartment_id').val();
        var posi_code =$('#addposi_code').val();
        var place =$('#addplace').val();
        var posi_type =$('#addposi_type').val();
        var degree =$('#adddegree').val();
        var depth =$('#adddepth').val();
        var positionNum =$('#addpositionNum').val();
        var descr =$('#adddescr').val();
        var sourceType =$('#addsourceType').val();

        if (managePositionBoardPage.checkAddPositionData(name,department_id,posi_code,place,posi_type,degree,depth,positionNum,descr)) {
            $.ajax({
                url : app.URL.addPositionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    name: name,
                    department_id:department_id,
                    posi_code:posi_code,
                    place:place,
                    posi_type:posi_type,
                    degree:degree,
                    deepth:depth,
                    positionNum:positionNum,
                    descr:descr,
                    sourceType:sourceType,

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
    updatePositionModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        managePositionBoardPage.initUpdatePositionData(index);
        $("#updatePositionModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdatePositionData: function (index) {
        //初始化数据
        var positions = managePositionBoardPage.data.positions;
        $('#updatePositionIndex').val(index);
        $('#updatename').val(positions[index].name);
        $('#updatedepartment_id').val(positions[index].department_id);
        $('#updatesourceType').val(positions[index].sourceType);
        $('#updateposi_code').val(positions[index].posi_code);
        $('#updateplace').val(positions[index].place);
        $('#updateposi_type').val(positions[index].posi_type);
        $('#updatedegree').val(positions[index].degree);
        $('#updatedepth').val(positions[index].deepth);
        $('#updatepositionNum').val(positions[index].positionNum);
        $('#updatedescr').val(positions[index].descr);
        var department_ids = document.getElementById('updatedepartment_id');
        for (var i = 0; i < department_ids.length; i++) {
            if (department_ids[i].value == positions[index].department_id) {
                department_ids[i].selected = true;
            }
        }
        var updateposi_types = document.getElementById('updateposi_type');
        for (var i = 0; i < updateposi_types.length; i++) {
            if (updateposi_types[i].value == positions[index].posi_type) {
                updateposi_types[i].selected = true;
            }
        }
        var updatesourceTypes = document.getElementById('updatesourceType');
        for (var i = 0; i < updatesourceTypes.length; i++) {
            if (updatesourceTypes[i].value == positions[index].sourceType) {
                updatesourceTypes[i].selected = true;
            }
        }
    },
    checkUpdatePositionData: function (name,department_id,posi_code,place,posi_type,degree,depth,positionNum,descr) {
        var flag = false;
        var msg ='';
        if (name == null || name == '' ) {
            msg = '名称不能为空!'
        }
        if (department_id == null || department_id == '' ) {
            msg = '部门不能为空!'
        }
        if (posi_code == null || posi_code == '' ) {
            msg = '编码不能为空!'
        }
        if (place == null || place == '' ) {
            msg = '招聘地点不能为空!'
        }
        if (posi_type == null || posi_type == '' ) {
            msg = '职位类型不能为空!'
        }
        if (degree == null || degree == '' ) {
            msg = '学历不能为空!'
        }
        if (depth == null || depth == '' ) {
            msg = '工作年限不能为空!'
        }
        if (positionNum == null || positionNum == '' ) {
            msg = '招聘人数不能为空!'
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
    updatePositionAction: function () {
        var positions = managePositionBoardPage.data.positions;
        var index = $('#updatePositionIndex').val();
        var name =$('#updatename').val();
        var department_id =$('#updatedepartment_id').val();
        var posi_code =$('#updateposi_code').val();
        var place =$('#updateplace').val();
        var posi_type =$('#updateposi_type').val();
        var degree =$('#updatedegree').val();
        var depth =$('#updatedepth').val();
        var positionNum =$('#updatepositionNum').val();
        var descr =$('#updatedescr').val();
        var sourceType =$('#updatesourceType').val();

        if (managePositionBoardPage.checkUpdatePositionData(name,department_id,posi_code,place,posi_type,degree,depth,positionNum,descr)) {
            $.ajax({
                url : app.URL.updatePositionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: positions[index].id,
                    name: name,
                    department_id:department_id,
                    posi_code:posi_code,
                    place:place,
                    posi_type:posi_type,
                    degree:degree,
                    deepth:depth,
                    positionNum:positionNum,
                    descr:descr,
                    sourceType:sourceType,

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
    deletePositionAction: function (index) {
        $.ajax({
            url : app.URL.deletePositionUrl()+index,
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
    }


};