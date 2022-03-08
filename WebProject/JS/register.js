window.onload = function () {
    let index; //下拉框索引
    let headerIndex;
    let flag = 0;

    // tab栏切换顶部
    $(".tabHeader").find("h2").click(function(){

        $(this).addClass("curSelect").siblings().removeClass("curSelect");

        let headerIndex = $(this).index() > 1 ? 1 : $(this).index();

        $('.tabBody .item').eq(headerIndex).show().siblings().hide();
        
    })

    // 下拉框
    $(".content").find(".select").click(function () {
        if (flag == 0) {

            $(this).siblings(".selectList").show();

            flag = 1;
        }else{
            $(this).siblings(".selectList").hide();

            flag = 0;
        }
    })
    // 索引
    $(".content").find(".selectList li").click(function () {
        
        index = $(this).index();

        $(this).parent().hide();

        flag = 0;
    })

    // 跳转注册超链接
    $(".goToPerson").click(function(){
        $("h2").eq(0).addClass("curSelect").siblings().removeClass("curSelect");

        $('.tabBody .item').eq(0).show().siblings().hide();
    })


    // 点击输入框提示信息
    $("input").focus(function(){
        $(this).siblings(".notice").show();

        $(this).siblings(".error").hide();
    })

    $("input").blur(function(){
        $(this).siblings(".notice").hide();

        $(this).siblings(".error").show();
    })
    // 注册按钮
    $(".register").click(function(){
        console.log(111);
    })
    // 短信激活码信息









































}