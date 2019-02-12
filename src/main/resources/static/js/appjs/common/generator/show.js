var prefix = "/common/generator"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
						showRefresh : false,
						showToggle : false,
						showColumns : false,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						search : false, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者
						// "server"
						// queryParams : queryParams,
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
								{
									field : 'tableName', // 列字段名
									title : '表名称' // 列标题
								},
								{
									field : 'engine',
									title : 'engine'
								},
								{
									field : 'tableComment',
									title : '表描述'
								},
								{
									field : 'createTime',
									title : '创建时间'
								},
								]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('destroy');load();
}

function dbBackup() {
	layer.confirm('确定要备份数据库吗？', {
        btn : [ '确定', '取消' ]
    }, function() {
    	layer.closeAll('dialog');
    	layer.load(1, {shade: false});
    	$.ajax({
            url : prefix + '/dbBackup' ,
            type : "get",
            success : function(r) {
            	layer.closeAll('dialog');
                if (r.code==0) {
                    layer.msg(r.msg);
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function dbRecover() {
	layer.confirm('确定要还原到上次备份吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		layer.closeAll('dialog');
    	layer.load(1, {shade: false});
    	$.ajax({
            url : prefix + '/dbRecover' ,
            type : "get",
            success : function(r) {
            	layer.closeAll('dialog');
                if (r.code==0) {
                    layer.msg(r.msg);
                }else{
                    layer.msg('没有可还原的备份');
                }
            }
        });
	})
}