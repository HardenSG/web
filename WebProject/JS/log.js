window.addEventListener('load', function () {

    var form = document.querySelector('.form-box');
    // 左右滑动
    register.addEventListener('click', function () {
        $('.login-box').hide();
        $('.register-box').show();
        form.style.transform = 'translateX(80%)';
    })

    login.addEventListener('click', function () {
        $('.login-box').show();
        $('.register-box').hide();
        form.style.transform = 'translateX(0%)';
    })
    // 发送验证码
    $(".sendCode").click(function (e) {
        e.preventDefault();
        var s = null;
        var will = null; //将来总毫秒数 + 60000    这个要提前声明，因为每次点击都会获取，要获取当前时间加60秒，
        var timer = null;

        function mes() {
            var now = +new Date(); //现在总毫秒数
            s = Math.ceil((will - now) / 1000); //秒数 ，向上取整
            if (s > 0) { //每次调用进行判断
                $(".sendCode").html(`${s} 秒`);
                $(".sendCode").attr('disabled', true);
            }
            if (s <= 0) {
                clearInterval(timer);
                $(".sendCode").html('发送');
                $(".sendCode").attr('disabled', false);
            }
        }

        will = +new Date() + 60000;
        mes(); //提前调用一次
        timer = setInterval(mes, 1000); //定时器调用计时函数
    });


    

    // 阻止页面的刷新并发送ajax请求
    $(".login").click(function (e) {
        e.preventDefault();
        console.log(11);
    })

    $(".register").click(function (e) {
        e.preventDefault();
    })


})