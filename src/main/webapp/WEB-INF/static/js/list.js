/**
 * Created by kongyunhui on 2016/11/24.
 */
function findUsers(){
    if(window.event.keyCode == 13){
        $.ajax({
            dataType: 'json',
            url: '/user/findUsers',
            data: {
                idOrUsername: $('#idOrUsername').val()
            },
            success: function (data) {
                if(data.success){
                    alert("查询到的用户数：" + data.data.length);
                } else {
                    alert(data.error);
                }
            }
        });
    }
}
