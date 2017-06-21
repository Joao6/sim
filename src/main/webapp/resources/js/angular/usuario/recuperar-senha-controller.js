
angular.module("SIM_APP", [])
        .value('urlBase', '/sim/recuperar-senha/rest')
        .controller("RecuperarSenhaController", function ($http, urlBase, $location) {
            var self = this;
            self.email = undefined;
            self.senha = undefined;
            self.senhaC = undefined;
            self.objetoRecuperacao = {};

            self.init = function () {
                self.openModalLoading();
            };

            self.enviarEmailRecuperacao = function () {
                self.openModalLoading();
                var email = self.email;
                self.email = undefined;

                $http({
                    method: 'GET',
                    url: urlBase + '/',
                    params: {'email': email}
                }).then(function successCallback(response) {
                    Materialize.toast('Email enviado com sucesso!', 2000, 'green rounded');                        
                        window.setTimeout(function () {
                            window.location.href = '/sim/login';
                        }, 500);
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Ocorreu um erro ao processar esta solicitação!");
                    self.closeModalLoading();
                });
            };

            self.alterarSenha = function () {
                if (self.senha === self.senhaC) {                    
                    self.openModalLoading();
                    self.objetoRecuperacao.senha = md5(self.senha);
                    self.objetoRecuperacao.idUsuario = $('#idUsuario').val();
                    self.objetoRecuperacao.idRecuperacao = $('#idRecuperacao').val();
                    self.senha = undefined;
                    self.senhaC = undefined;                    
                    $http({
                        method: 'PUT',
                        url: urlBase + '/',
                        data: self.objetoRecuperacao
                    }).then(function successCallback(response) {
                        Materialize.toast('Senha alterada com sucesso!', 2000, 'green rounded');                        
                        window.setTimeout(function () {
                            window.location.href = '/sim/login';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao salvar os dados.");
                        self.closeModalLoading();
                    });
                }else{
                    self.ocorreuErro("As senhas não estão iguais!");
                }
            };

            self.openModalLoading = function () {
                $("#modal-loading").modal('open');
            };

            self.closeModalLoading = function () {
                window.setTimeout(function () {
                    $("#modal-loading").modal('close');
                }, 500);
            };

            self.openModal = function (modal) {
                $("#modal-" + modal).modal('open');
            };

            self.ocorreuErro = function (erro) {
                if (erro === undefined || erro === "") {
                    erro = "Algum erro inesperado aconteceu!";
                }
                Materialize.toast(erro, 4000, 'red rounded');
            };


//            self.activate = function () {
//                self.closeModalLoading();
//                var urlAtual = $location.absUrl();
//                if (urlAtual.indexOf("/master/home") !== -1) {
//                    self.readInstituicao();
//                } else if (urlAtual.indexOf("/master/cadastrar-instituicao") !== -1) {
//
//                } else if (urlAtual.indexOf("/master/meus-dados") !== -1) {
//                    self.getMaster();
//                } else if (urlAtual.indexOf("/master/instituicao/") !== -1) {
//                    self.getInstituicao();
//                } else if (urlAtual.indexOf("/master/alterar-senha") !== -1) {
//                    self.getMaster();
//                }
//            };
//            self.activate();
        });




