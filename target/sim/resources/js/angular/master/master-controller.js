
angular.module("SIM_APP", [])
        .value('urlBase', '/sim/master/rest/')
        .controller("MasterController", function ($http, urlBase, $location) {
            var self = this;
            self.atendimentoList = [];
            self.atendimento = undefined;
            self.capontamento = undefined;
            self.pacienteList = [];
            self.master = undefined;

            self.instituicaoList = [];
            self.nomeInstituicao = "";
            self.configuracaoVersao = "";

            self.count = undefined;
            self.offset = 0;
            self.limit = 10;

            self.senhaA = undefined;
            self.senhaN = undefined;
            self.senhaC = undefined;

            self.isSenhaOk = false;
            self.isEmailOk = true;

            self.changedEmail = false;

            self.profissional = undefined;
            
            self.init = function (){
                self.openModalLoading();
            };

            var configModal = {
                dismissible: false, // Modal can be dismissed by clicking outside of the modal
                opacity: .5, // Opacity of modal background
                in_duration: 1, // Transition in duration
                out_duration: 1, // Transition out duration
                ready: function () {
                    return;
                }, // Callback for Modal open
                complete: function () {
                } // Ca
            };

            self.readInstituicao = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }
                if (self.configuracaoVersao === 'QUALQUER') {
                    self.configuracaoVersao = undefined;
                }

                $http({
                    method: 'GET',
                    url: urlBase + 'instituicao-list',
                    params: {'nomeInstituicao': self.nomeInstituicao, 'confVersao': self.configuracaoVersao,
                        'offset': self.offset}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.instituicaoList = response.data.dados;
                    self.count = response.data.count;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar as instituições cadastradas!");
                });
            };

            self.changeEmail = function () {
                self.changedEmail = true;
            };

            self.salvarInstituicao = function () {
                if (validaInstituicao(self.instituicao, 'POST')) {
                    self.openModalLoading();
                    self.instituicao.senha = md5(self.instituicao.senha);
                    $http({
                        method: 'POST',
                        url: urlBase + 'cadastrar-instituicao',
                        data: self.instituicao
                    }).then(function successCallback(response) {
                        Materialize.toast('Instituição cadastrada com sucesso!', 2000, 'green rounded');
                        self.instituicao = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/master/home';
                        }, 1500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao cadastrar esta instituição.");
                        self.closeModalLoading();
                    });
                }
            };

            self.alterarSenha = function () {
                if (validaAlterarSenha(self.senhaA, self.senhaN, self.senhaC)) {
                    self.openModalLoading();
                    self.master.senha = md5(self.senhaN);
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar-master',
                        data: self.master
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados salvos com sucesso!', 2000, 'green rounded');
                        self.instituicao = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/master/home';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao salvar os dados.");
                        self.closeModalLoading();
                    });
                }
            };
            
            self.alterarSenhaInstituicao = function (){
                if(self.senhaN === self.senhaC){
                    self.instituicao.senha = md5(self.senhaN);
                    self.editarInstituicao();
                }else{
                    self.ocorreuErro("As senhas não são iguais!");
                }
            };

            self.cleanEmailError = function () {
                $('#email').removeClass('error-form');
            };

            self.getMaster = function () {
                self.openModalLoading();
                $http({
                    method: 'GET',
                    url: urlBase + 'master'
                }).then(function successCallback(response) {
                    self.master = response.data;
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar os dados!");
                    self.closeModalLoading();
                });
            };

            function validaInstituicao(instituicao, flag) {
                var isInstituicaoOk = true;
                var isSenhaOk = true;
                if (instituicao !== undefined) {
                    if (instituicao.nome === undefined || instituicao.nome === "") {
                        isInstituicaoOk = false;
                        $('#nome').addClass('error-form');
                    }
                    if (instituicao.email === undefined || instituicao.email === "") {
                        isInstituicaoOk = false;
                        $('#email').addClass('error-form');
                    }
                    if (instituicao.confVersao === undefined || instituicao.confVersao === "") {
                        isInstituicaoOk = false;
                        $('#configuracao').addClass('error-form');
                    }
                    if (instituicao.telefone === undefined || instituicao.telefone === "") {
                        isInstituicaoOk = false;
                        $('#telefone').addClass('error-form');
                    }
                    if (flag === 'POST') {
                        isSenhaOk = validaSenha(instituicao.senha, self.csenha);
                        if (!isSenhaOk) {
                            isInstituicaoOk = false;
                            Materialize.toast('As senhas não coincidem!', 2000, 'amber rounded');
                            $('#senha').addClass('error-form');
                            $('#csenha').addClass('error-form');
                        }
                    }
                } else {
                    isInstituicaoOk = false;
                }
                if (!isInstituicaoOk) {
                    self.ocorreuErro("Inserir os dados corretamente");
                }
                return isInstituicaoOk;
            }

            function validaAlterarSenha(senhaA, senhaN, senhaC, master) {
                var isSenhaOk = false;
                senhaA = md5(senhaA);
                if (senhaA !== self.master.senha) {
                    isSenhaOk = false;
                    Materialize.toast('A senha atual não é válida!', 3000, 'amber rounded');
                    $('#senhaA').addClass('.error-form');
                    self.senhaA = undefined;
                    return isSenhaOk;
                } else {
                    if (senhaN !== undefined && senhaN !== "") {
                        if (senhaC !== undefined && senhaN !== "") {
                            if (senhaN === senhaC) {
                                isSenhaOk = true;
                            }
                        }
                    }
                }
                if (!isSenhaOk) {
                    self.senhaN = undefined;
                    self.senhaC = undefined;
                    Materialize.toast('Preencha corretamente os campos de senha!', 3000, 'amber rounded');
                }
                return isSenhaOk;
            }

            function validaSenha(senha, csenha) {
                var isSenhaOk = false;

                if (senha !== undefined && senha !== "") {
                    if (csenha !== undefined && senha !== "") {
                        if (senha === csenha) {
                            isSenhaOk = true;
                        }
                    } else {
                        //senha vazia
                        isSenhaOk = false;
                        Materialize.toast('É necessário preencher corretamente os campos de senha!', 3000, 'amber rounded');
                    }
                } else {
                    //senha vazia
                    isSenhaOk = false;
                    Materialize.toast('É necessário preencher corretamente os campos de senha!', 3000, 'amber rounded');
                }

                return isSenhaOk;
            }

            self.validaEmail = function (email) {
                if (email !== undefined && email !== "") {
                    self.openModalLoading();
                    $http({
                        method: 'GET',
                        url: urlBase + 'valida-email/',
                        params: {'email': email}
                    }).then(function successCallback(response) {
                        if (response.data === true) {
                            self.isEmailOk = true;
                        } else {
                            self.isEmailOk = false;
                            Materialize.toast("Este e-mail já está cadastrado no sistema!", 4000, 'red rounded');
                            $('#email').addClass('error-form');
                        }
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao tentar validar este email!");
                        self.closeModalLoading();
                    });
                }
            };

            self.instituicaoListEmpty = function () {
                if (self.instituicaoList === undefined || self.instituicaoList.length === 0) {
                    return true;
                } else {
                    return false;
                }
            };

            self.instituicaoListLenght = function () {
                return self.instituicaoList.length;
            };

            self.openModalProfissional = function (modal, profissional) {
                self.profissional = profissional;
                $("#modal-" + modal).modal('open');
            };

            self.getPacienteAssociado = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'paciente-associado/'
                }).then(function successCallback(response) {
                    self.pacienteList = response.data;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar os pacientes associados!");
                });
            };

            self.editarInstituicao = function () {
                if(self.changedEmail){
                    self.validaEmail(self.instituicao.email);
                }
                if (validaInstituicao(self.instituicao, 'PUT')) {
                    self.openModalLoading();
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar-instituicao',
                        data: self.instituicao
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados salvos com sucesso!', 2000, 'green rounded');
                        self.instituicao = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/master/home';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao salvar os dados.");
                        self.closeModalLoading();
                    });
                }
            };

            self.editarMaster = function () {
                if(self.changedEmail){
                    self.validaEmail(self.master.email);
                }
                if (validaMaster(self.master)) {
                    self.openModalLoading();
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar-master',
                        data: self.master
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados salvos com sucesso!', 2000, 'green rounded');
                        self.master = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/master/home';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao salvar os dados.");
                        self.closeModalLoading();
                    });
                }
            };

            self.getInstituicao = function () {
                self.openModalLoading();
                var idInstituicao = $('#idInstituicao').val();
                $http({
                    method: 'GET',
                    url: urlBase + 'instituicao/' + idInstituicao
                }).then(function successCallback(response) {
                    self.instituicao = response.data;
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar este profissional...");
                    self.closeModalLoading();
                });
            };

            self.validarSenha = function () {
                var senha = $('#senha').val();
                var csenha = $('#confirmaSenha').val();

                if (senha !== undefined && senha !== "") {
                    if (csenha !== undefined && csenha !== "") {
                        if (senha !== csenha) {
                            $('#senha').removeClass('validate');
                            $('#senha').addClass('error-form');
                            $('#confirmaSenha').removeClass('validate');
                            $('#confirmaSenha').addClass('error-form');
                            Materialize.toast('As senhas não são iguais!', 4000, 'red rounded');
                            self.isSenhaOk = false;
                        } else {
                            $('#senha').removeClass('error-form');
                            $('#confirmaSenha').removeClass('error-form');
                            self.isSenhaOk = true;
                        }
                    }
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

            function validaMaster(master) {
                var isMasterOk = true;
                if (master.nome === undefined || master.nome === null) {
                    isMasterOk = false;
                    $('#nome').addClass('error-form');
                }
                if (master.email === undefined || master.email === null) {
                    isMasterOk = false;
                    $('#email').addClass('error-form');
                }
                if(!self.isEmailOk){
                    isMasterOk = false;
                }
                if(!isMasterOk){
                    self.ocorreuErro("Erro ao editar os dados. Tente novamente!");
                }
                return isMasterOk;
            }

            self.pagination = function (tipo) {
                self.isPagination = true;
                if (tipo === 'PROXIMO') {
                    if (self.offset !== 0 || self.isFirst === false) {
                        self.offset = (self.offset + self.limit);
                    }
                } else {
                    if (self.offset !== 0 || self.isFirst === false) {
                        self.offset = (self.offset - self.limit);
                    }
                }
                var urlAtual = $location.absUrl();
                if (urlAtual.indexOf("/home") !== -1) {
                    self.readAtendimentosInstituicao();
                } else if (urlAtual.indexOf("/meus-atendimentos") !== -1) {
                    self.readAtendimentosProfissional();
                }
            };

            self.activate = function () {
                self.closeModalLoading();
                var urlAtual = $location.absUrl();
                if (urlAtual.indexOf("/master/home") !== -1) {
                    self.readInstituicao();
                } else if (urlAtual.indexOf("/master/cadastrar-instituicao") !== -1) {

                } else if (urlAtual.indexOf("/master/meus-dados") !== -1) {
                    self.getMaster();
                } else if (urlAtual.indexOf("/master/instituicao/") !== -1) {
                    self.getInstituicao();
                } else if (urlAtual.indexOf("/master/alterar-senha") !== -1) {
                    self.getMaster();
                }
            };
            self.activate();
        });




