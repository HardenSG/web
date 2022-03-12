window.onload = function () {
    let index; //下拉框索引
    let headerIndex = 0;
    let flag = 0;

    // tab栏切换顶部
    $(".tabHeader").find("h2").click(function () {

        $(this).addClass("curSelect").siblings().removeClass("curSelect");

        headerIndex = $(this).index() > 1 ? 1 : $(this).index();

        $('.tabBody .item').eq(headerIndex).show().siblings().hide();

        // 切换页面关闭提示信息

        $(".error").hide();

        $(".notice").hide();

    })

    // 下拉框
    $(".content").find(".select").click(function () {
        if (flag == 0) {

            $(this).siblings(".selectList").show();

            flag = 1;
        } else {
            $(this).siblings(".selectList").hide();

            flag = 0;
        }
    })
    // 索引
    $(".content").find(".selectList li").click(function () {

        index = $(this).index();

        $(this).parent().hide();

        flag = 0;

        // 获取索引并将显示到对应旗帜

        let y = index * (-26) ;

        $(".select").find("a").css("backgroundPositionY", y + 'px');

    })

    // 跳转注册超链接
    $(".goToPerson").click(function () {
        $("h2").eq(0).addClass("curSelect").siblings().removeClass("curSelect");

        $('.tabBody .item').eq(0).show().siblings().hide();
    })


    // 点击输入框提示信息
    $("input").focus(function () {
        $(this).siblings(".notice").show();

        $(this).siblings(".error").hide();
    })

    $("input").blur(function () {
        $(this).siblings(".notice").hide();

        $(this).siblings(".error").show();
    })

    // 短信激活码信息

    // 图形验证码

    function change() {
        var code;

        code = "";

        var checkCode = $(".showCode");

        var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); 

        for (var i = 0; i < 4; i++) { 
            let index1 = Math.floor(Math.random() * 36); 

            code += random[index1]; 
            
        }

        checkCode.html(code); //把code值赋给验证码

        console.log(code);

        console.log($(".showCode").val());
    }

    change();

    $(".changeCode").click(function(){
        change();
    })







    // 获取表单数据的函数封装

    function getData(target) {

        let data = target.serializeArray();

        let arr = [];

        for (let i = 0; i < data.length; i++) {
            arr.push(data[i].value);
        }

        return arr;
    }


    //注册页面ajax请求

    $(".register").click(function (e) {

        e.preventDefault();

        switch (headerIndex) {

            case 0: {
                let a = getData($(".personForm"));

                console.log(a);

                break;
            }
            case 1: {

                let a = getData($(".companyForm"));



                break;
            }
        }

        // $(this).attr('disabled', true);

    })





































}