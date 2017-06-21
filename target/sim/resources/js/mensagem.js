mensagem = {
    verificaMensagem: function () {
        var me = this;
        $.ajax({
            method: 'GET',
            url: '/sim/profissional/rest/mensagem/verifica',
            success: function (data) {
                if(data > 0){
                    $('.menuConversa').text('+' + data);
                }                
                setTimeout(function () {
                    me.verificaMensagem();
                }, 15000);
            },
            failure: function (data) {
                setTimeout(function () {
                    me.verificaMensagem();
                }, 15000);
            }
        });
    }
};