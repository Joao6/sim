
angular.module("SIM_APP", [])
        .value('urlBase', '/profissional/rest/')
        .controller("ProfissionalController", function ($http, urlBase, $location) {
            var self = this;
            self.atendimentoList = [];
            self.atendimento = undefined;
            self.capontamento = undefined;
            self.pacienteList = [];

            self.nomePaciente = "";
            self.nomeProfissional = "";
            self.dataAtendimento = "";

            self.count = undefined;
            self.offset = 0;
            self.limit = 10;
            
            self.senhaA = undefined;
            self.senhaN = undefined;
            self.senhaC = undefined;

            self.isSenhaOk = false;
            self.isEmailOk = false;

            self.profissional = undefined;
            
             self.init = function () {
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

            self.readAtendimentosPacienteAssociado = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }
                $http({
                    method: 'GET',
                    url: urlBase + 'atendimentos/paciente-associado',
                    params: {'nomePaciente': self.nomePaciente, 'nomeProfissional': self.nomeProfissional,
                        'dataAtendimento': self.dataAtendimento, 'offset': self.offset}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.atendimentoList = response.data.dados;
                    for (i = 0; i < self.atendimentoList.length; i++) {
                        self.atendimentoList[i].dataApontamento = convertDate(self.atendimentoList[i].dataApontamento);
                    }
                    self.count = response.data.count;
                    self.atendimento = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar os atendimentos!");
                });
            };

            self.readAtendimentosProfissional = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }
                $http({
                    method: 'GET',
                    url: urlBase + 'atendimentos',
                    params: {'nomePaciente': self.nomePaciente, 'dataAtendimento': self.dataAtendimento,
                        'offset': self.offset}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.atendimentoList = response.data.dados;
                    for (i = 0; i < self.atendimentoList.length; i++) {
                        self.atendimentoList[i].dataApontamento = convertDate(self.atendimentoList[i].dataApontamento);
                    }
                    self.count = response.data.count;
                    self.atendimento = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar os atendimentos da instituição!");
                });
            };

            self.readAtendimentoById = function () {
                var idAtendimento = $('#idAtendimento').val();
                self.openModalLoading();
                $http({
                    method: 'GET',
                    url: urlBase + 'atendimento/' + idAtendimento
                }).then(function successCallback(response) {
                    self.atendimento = response.data;
                    self.atendimento.dataApontamento = convertDate(self.atendimento.dataApontamento);
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Ocorreu um erro ao buscar este atendimento!");
                    self.closeModalLoading();
                });
            };

            self.complementarApontamento = function () {
                if (self.capontamento !== undefined && self.capontamento !== "") {
                    self.openModalLoading();
                    var complementado;
                    //verificar se o atendimento já foi complementado antes
                    if (self.atendimento.apontamento.indexOf(self.atendimento.dataApontamento) !== -1) {
                        //já foi complementado
                        complementado = self.atendimento.apontamento + "\n";
                        complementado += getDateCurrent() + ': ' + self.capontamento;
                        self.atendimento.apontamento = complementado;
                    } else {
                        //primeiro complemento
                        complementado = self.atendimento.dataApontamento + ": ";
                        complementado += self.atendimento.apontamento + "\n";
                        complementado += getDateCurrent() + ': ' + self.capontamento;
                        self.atendimento.apontamento = complementado;
                    }
                    //converter a data do paciente
                    self.atendimento.paciente.nascimento = convertDate(self.atendimento.paciente.nascimento);
                    //put em atendimento
                    $http({
                        method: 'PUT',
                        url: urlBase + 'atendimento/',
                        data: self.atendimento
                    }).then(function successCallback(response) {
                        Materialize.toast('Atendimento complementado com sucesso!', 2000, 'green rounded');
                        self.atendimento = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/profissional/meus-atendimentos';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao complementar este atendimento");
                        self.closeModalLoading();
                    });
                }
            };


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

            self.atendimentoListEmpty = function () {
                if (self.atendimentoList === undefined || self.atendimentoList.length === 0) {
                    return true;
                } else {
                    return false;
                }
            };

            self.atendimentoListLenght = function () {
                return self.atendimentoList.length;
            };
            
            self.openModalLoading = function () {
                $("#modal-loading").modal('open');
            };

            self.closeModalLoading = function () {
                window.setTimeout(function () {
                    $("#modal-loading").modal('close');
                }, 500);
            };

            self.openModalProfissional = function (modal, profissional) {
                self.profissional = profissional;
                $("#modal-" + modal).modal('open');
            };

            self.salvarAtendimento = function () {
                if (validaAtendimento(self.atendimento)) {
                    self.openModalLoading();
                    for (var i = 0; i < self.pacienteList.length; i++) {
                        if (self.pacienteList[i].id == self.atendimento.paciente) {
                            self.atendimento.paciente = self.pacienteList[i];
                            break;
                        }
                    }
                    self.atendimento.paciente.nascimento = convertDate(self.atendimento.paciente.nascimento);
                    $http({
                        method: 'POST',
                        url: urlBase + 'cadastrar-atendimento',
                        data: self.atendimento
                    }).then(function successCallback(response) {
                        Materialize.toast('Atendimento cadastrado com sucesso!', 2000, 'green rounded');
                        self.atendimento = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/profissional/meus-atendimentos';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao cadastrar este atendimento");
                        self.closeModalLoading();
                    });
                }
            };

            function validaAtendimento(atendimento) {
                var isAtendimentoOk = true;

                if (atendimento !== undefined && atendimento !== null) {
                    if (atendimento.paciente === undefined || atendimento.paciente === null) {
                        $('#paciente').addClass('error-form');
                        isAtendimentoOk = false;
                    }

                    if (atendimento.dataApontamento === undefined || atendimento.dataApontamento === null) {
                        $('#data').addClass('error-form');
                        isAtendimentoOk = false;
                    }

                    if (atendimento.apontamento === undefined || atendimento.apontamento === null) {
                        $('#apontamentos').addClass('error-form');
                        isAtendimentoOk = false;
                    }
                } else {
                    isAtendimentoOk = false;
                }

                if (!isAtendimentoOk) {
                    self.ocorreuErro("Preencha todos os dados deste atendimento!");
                }
                return isAtendimentoOk;
            }

            self.cleanErrorPaciente = function () {
                $('#paciente').removeClass('error-form');
            };
            self.cleanErrorData = function () {
                $('#data').removeClass('error-form');
            };
            self.cleanErrorApontamento = function () {
                $('#apontamentos').removeClass('error-form');
            };

            self.getPacienteAssociado = function () {
                self.openModalLoading();
                $http({
                    method: 'GET',
                    url: urlBase + 'paciente-associado/'
                }).then(function successCallback(response) {
                    self.pacienteList = response.data;
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar os pacientes associados!");
                    self.closeModalLoading();
                });
            };

            self.getInstituicao = function () {
                $http({
                    method: 'GET',
                    url: urlBase
                }).then(function successCallback(response) {
                    self.instituicao = response.data;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar a instituição...");
                });
            };

            self.getProfissionalSession = function () {
                self.openModalLoading();
                $http({
                    method: 'GET',
                    url: urlBase + 'profissional/'
                }).then(function successCallback(response) {
                    self.profissional = response.data;
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar este profissional...");
                    self.closeModalLoading();
                });
            };

            self.editarProfissional = function () {
                if (validaProfissional(self.profissional)) {
                    self.openModalLoading();
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar/meus-dados',
                        data: self.profissional
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados editados com sucesso!', 2000, 'green rounded');
                        self.profissional = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/profissional/home';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao editar os dados");
                        self.closeModalLoading();
                    });
                } else {
                    self.ocorreuErro("Verifique os dados inseridos e tente novamente!");
                }

            };
            
            self.alterarSenha = function () {
                if (validaAlterarSenha(self.senhaA, self.senhaN, self.senhaC)) {
                    self.openModalLoading();
                    self.profissional.senha = md5(self.senhaN);
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar/meus-dados',
                        data: self.profissional
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados salvos com sucesso!', 2000, 'green rounded');
                        self.instituicao = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/profissional/home';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao salvar os dados.");
                        self.closeModalLoading();
                    });
                }
            };
            
            function validaAlterarSenha(senhaA, senhaN, senhaC) {
                var isSenhaOk = false;
                senhaA = md5(senhaA);
                if (senhaA !== self.profissional.senha) {
                    isSenhaOk = false;
                    Materialize.toast('A senha atual não é válida!', 3000, 'amber rounded');
                    $('#senhaA').addClass('error-form');
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

            self.ocorreuErro = function (erro) {
                if (erro === undefined || erro === "") {
                    erro = "Algum erro inesperado aconteceu!";
                }
                Materialize.toast(erro, 4000, 'red rounded');
            };

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
                    self.readAtendimentosPacienteAssociado();
                } else if (urlAtual.indexOf("/meus-atendimentos") !== -1) {
                    self.readAtendimentosProfissional();
                }
            };

            //date = 1995-11-06 to 06/11/1995
            function convertDate(date) {
                var dia = date.toString().substring(8, 10);
                var mes = date.toString().substring(5, 7);
                var ano = date.toString().substring(0, 4);
                var data = dia + "/" + mes + "/" + ano;
                return data;
            }

            function getDateCurrent() {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!

                var yyyy = today.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }
                var today = dd + '/' + mm + '/' + yyyy;
                return today;
            }

            function validaProfissional(profissional) {
                var isProfissionalOk = true;
                if (profissional.nome === undefined || profissional.nome === "") {
                    $('#nome').addClass("error-form");
                    isProfissionalOk = false;
                }

                if (profissional.email === undefined || profissional.email === "") {
                    $('#email').addClass("error-form");
                    isProfissionalOk = false;
                }

                if (profissional.profissao === undefined || profissional.profissao === "") {
                    $('#profissao').addClass("error-form");
                    isProfissionalOk = false;
                }

                if (profissional.telefone === undefined || profissional.telefone === "") {
                    $('#telefone').addClass("error-form");
                    isProfissionalOk = false;
                }
                return isProfissionalOk;
            }

            self.activate = function () {
                self.closeModalLoading();
                var urlAtual = $location.absUrl();
                if (urlAtual.indexOf("/profissional/home") !== -1) {
                    self.readAtendimentosPacienteAssociado();
                } else if (urlAtual.indexOf("/profissional/meus-atendimentos") !== -1) {
                    if (urlAtual.indexOf("/editar") !== -1) {
                        self.readAtendimentoById();
                    } else if (urlAtual.indexOf("/novo") !== -1) {
                        self.getPacienteAssociado();
                    } else {
                        self.readAtendimentosProfissional();
                    }
                } else if (urlAtual.indexOf("/profissional/meus-dados") !== -1) {
                    self.getProfissionalSession();
                } else if(urlAtual.indexOf("/profissional/alterar-senha") !== -1){
                    self.getProfissionalSession();
                }
            };
            self.activate();
        });


