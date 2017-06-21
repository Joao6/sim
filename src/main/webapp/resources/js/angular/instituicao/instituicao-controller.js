
angular.module("SIM_APP", [])
        .value('urlBase', '/sim/instituicao/rest/')
        .controller("InstituicaoController", function ($http, urlBase, $location) {
            var self = this;
            self.atendimentoList = [];
            self.atendimento = undefined;

            self.instituicao = undefined;

            self.nomePaciente = "";
            self.nomeProfissional = "";
            self.dataAtendimento = "";

            self.count = undefined;
            self.offset = 0;
            self.limit = 10;

            self.isSenhaOk = false;
            self.isEmailOk = false;

            self.profissionalList = [];
            self.pacienteList = [];

            self.paciente = undefined;
            self.profissional = undefined;

            self.senhaA = undefined;
            self.senhaN = undefined;
            self.senhaC = undefined;

            self.pacientes = [];

            self.pacientesID = [];

            self.pacienteFiltrado = [];
            self.changedEmail = false;

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

            self.readAtendimentosByCriteria = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }
                $http({
                    method: 'GET',
                    url: urlBase + 'atendimentos/',
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
                    self.ocorreuErro("Erro ao buscar os atendimentos da instituição!");
                });
            };

            self.changeEmail = function () {
                self.changedEmail = true;
            };

            self.getProfissionalList = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }

                $http({
                    method: 'GET',
                    url: urlBase + 'profissionalList/',
                    params: {'offset': self.offset, 'nomeProfissional': self.nomeProfissional}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.profissionalList = response.data.dados;
                    self.count = response.data.count;
                    self.profissional = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar a lista de profissionais...");
                });
            };

            self.validaEmail = function (email) {
                if (email !== undefined && email !== "") {
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
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao tentar validar este email!");
                    });
                }
            };

            self.alterarSenha = function () {
                if (validaAlterarSenha(self.senhaA, self.senhaN, self.senhaC)) {
                    self.openModalLoading();
                    self.instituicao.senha = md5(self.senhaN);
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar',
                        data: self.instituicao
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados salvos com sucesso!', 2000, 'green rounded');
                        self.instituicao = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/instituicao/home';
                        }, 1500);
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
                if (senhaA !== self.instituicao.senha) {
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

            self.cleanEmailError = function () {
                $('#email').removeClass('error-form');
            };

            self.atendimentoListEmpty = function () {
                if (self.atendimentoList === undefined || self.atendimentoList.length === 0) {
                    return true;
                } else {
                    return false;
                }
            };

            self.pacienteListEmpty = function () {
                if (self.pacienteList === undefined || self.pacienteList.length === 0) {
                    return true;
                } else {
                    return false;
                }
            };

            self.profissionalListEmpty = function () {
                if (self.profissionalList === undefined || self.profissionalList.length === 0) {
                    return true;
                } else {
                    return false;
                }
            };

            self.atendimentoListLenght = function () {
                return self.atendimentoList.length;
            };

            self.pacienteListLenght = function () {
                return self.pacienteList.length;
            };

            self.profissionalListLenght = function () {
                return self.profissionalList.length;
            };

            self.getPacienteList = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }

                $http({
                    method: 'GET',
                    url: urlBase + 'pacienteList/',
                    params: {'offset': self.offset, 'nomePaciente': self.nomePaciente}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.pacienteList = response.data.dados;
                    for (i = 0; i < self.pacienteList.length; i++) {
                        self.pacienteList[i].nascimento = convertDate(self.pacienteList[i].nascimento);
                    }
                    self.count = response.data.count;
                    self.paciente = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar a lista de pacientes...");
                });
            };

            self.openModalPaciente = function (modal, paciente) {
                self.paciente = paciente;
                $("#modal-" + modal).modal('open');
            };

            self.openModalProfissional = function (modal, profissional) {
                self.profissional = profissional;
                $("#modal-" + modal).modal('open');
            };

            self.deletarPaciente = function (paciente) {
                self.openModalLoading();
                self.paciente = paciente;

                $http({
                    method: 'DELETE',
                    url: urlBase + 'excluir/paciente/' + self.paciente.id + '/'
                }).then(function successCallback(response) {
                    Materialize.toast('Paciente excluído com sucesso!', 2000, 'green rounded');
                    self.getPacienteList();
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao excluir o paciente!");
                    self.closeModalLoading();
                });
            };

            self.deletarProfissional = function (profissional) {
                self.openModalLoading();
                self.profissional = profissional;

                $http({
                    method: 'DELETE',
                    url: urlBase + 'excluir/profissional/' + self.profissional.id + '/'
                }).then(function successCallback(response) {
                    Materialize.toast('Profissional excluído com sucesso!', 2000, 'green rounded');
                    self.getProfissionalList();
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao excluir o profissional!");
                    self.closeModalLoading();
                });
            };

            self.getInstituicao = function () {
                self.openModalLoading();
                $http({
                    method: 'GET',
                    url: urlBase
                }).then(function successCallback(response) {
                    self.instituicao = response.data;
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar a instituição...");
                    self.closeModalLoading();
                });
            };


            self.getPaciente = function () {
                self.openModalLoading();
                var idPaciente = $('#idPaciente').val();
                $http({
                    method: 'GET',
                    url: urlBase + 'paciente/' + idPaciente
                }).then(function successCallback(response) {
                    self.paciente = response.data;
                    self.paciente.nascimento = convertDate(self.paciente.nascimento);
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar este paciente...");
                    self.closeModalLoading();
                });
            };

            self.salvarPaciente = function () {
                if (validaPaciente(self.paciente)) {
                    self.openModalLoading();
                    $http({
                        method: 'POST',
                        url: urlBase + 'pacientes/salvar',
                        data: self.paciente
                    }).then(function successCallback(response) {
                        Materialize.toast('Paciente cadastrado com sucesso!', 2000, 'green rounded');
                        self.paciente = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/instituicao/pacientes';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao cadastrar este paciente");
                        self.closeModalLoading();
                    });
                } else {
                    self.ocorreuErro("Verifique os dados inseridos e tente novamente!");
                }
            };

            self.salvarProfissional = function () {
                if (validaProfissional(self.profissional)) {
                    self.openModalLoading();
                    self.profissional.senha = md5(self.profissional.senha);
                    $http({
                        method: 'POST',
                        url: urlBase + 'profissionais/salvar',
                        data: self.profissional
                    }).then(function successCallback(response) {
                        Materialize.toast('Profissional cadastrado com sucesso!', 2000, 'green rounded');
                        self.profissional = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/instituicao/profissionais';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao cadastrar este profissional");
                        self.closeModalLoading();
                    });
                } else {
                    self.ocorreuErro("Verifique os dados inseridos e tente novamente!");
                }
            };

            self.editarInstituicao = function () {
                if(self.changedEmail){
                    self.validaEmail(self.instituicao.email);
                }
                if (validaInstituicao(self.instituicao)) {
                    self.openModalLoading();
                    $http({
                        method: 'PUT',
                        url: urlBase + 'editar',
                        data: self.instituicao
                    }).then(function successCallback(response) {
                        Materialize.toast('Dados alterados com sucesso!', 2000, 'green rounded');
                        self.paciente = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/instituicao/home';

                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao editar esta instituição.");
                        self.closeModalLoading();
                    });
                } else {
                    self.ocorreuErro("Verifique os dados inseridos e tente novamente!");
                }
            };

            function validaInstituicao(instituicao) {
                var isInstituicaoOk = true;
                if (instituicao !== undefined) {
                    if (instituicao.nome === undefined || instituicao.nome === "") {
                        isInstituicaoOk = false;
                        $('#nome').addClass('error-form');
                    }
                    if (instituicao.email === undefined || instituicao.email === "") {
                        isInstituicaoOk = false;
                        $('#email').addClass('error-form');
                    }
                    if (instituicao.telefone === undefined || instituicao.telefone === "") {
                        isInstituicaoOk = false;
                        $('#telefone').addClass('error-form');
                    }
                } else {
                    isInstituicaoOk = false;
                }
                if (!isInstituicaoOk) {
                    self.ocorreuErro("Inserir os dados corretamente");
                }
                return isInstituicaoOk;
            }

            self.editarPaciente = function () {
                if (validaPaciente(self.paciente)) {
                    self.openModalLoading();
                    $http({
                        method: 'PUT',
                        url: urlBase + 'pacientes/editar',
                        data: self.paciente
                    }).then(function successCallback(response) {
                        Materialize.toast('Paciente editado com sucesso!', 2000, 'green rounded');
                        self.paciente = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/instituicao/pacientes';

                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao editar este paciente");
                        self.closeModalLoading();
                    });
                } else {
                    self.ocorreuErro("Verifique os dados inseridos e tente novamente!");
                }
            };

            self.getProfissional = function () {
                var idProfissional = $('#idProfissional').val();
                self.openModalLoading();
                $http({
                    method: 'GET',
                    url: urlBase + 'profissional/' + idProfissional
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
                        url: urlBase + 'profissionais/editar',
                        data: self.profissional
                    }).then(function successCallback(response) {
                        Materialize.toast('Profissional editado com sucesso!', 2000, 'green rounded');
                        self.paciente = undefined;
                        window.setTimeout(function () {
                            window.location.href = '/sim/instituicao/profissionais';
                        }, 500);
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro("Erro ao editar este profissional");
                        self.closeModalLoading();
                    });
                } else {
                    self.ocorreuErro("Verifique os dados inseridos e tente novamente!");
                }

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

            self.ocorreuErro = function (erro) {
                if (erro === undefined || erro === "") {
                    erro = "Algum erro inesperado aconteceu!";
                }
                Materialize.toast(erro, 4000, 'red rounded');
            };

            self.openModalLoading = function () {
                $("#modal-loading").modal('open');
            };

            self.closeModalLoading = function () {
                window.setTimeout(function () {
                    $("#modal-loading").modal('close');
                }, 500);
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
                if (urlAtual.indexOf("/pacientes") !== -1) {
                    self.getPacienteList();
                } else if (urlAtual.indexOf("/profissionais") !== -1) {
                    self.getProfissionalList();
                } else if (urlAtual.indexOf("/home") !== -1) {
                    self.readAtendimentosByCriteria();
                }
            };

            //date = 1995-11-06
            function convertDate(date) {
                var dia = date.toString().substring(8, 10);
                var mes = date.toString().substring(5, 7);
                var ano = date.toString().substring(0, 4);
                var data = dia + "/" + mes + "/" + ano;
                return data;
            }

            function validaPaciente(paciente) {
                var isPacienteOk = true;
                if (paciente.nome === undefined || paciente.nome === "") {
                    $('#nome').addClass("error-form");
                    isPacienteOk = false;
                }

                if (paciente.nascimento === undefined || paciente.nascimento === "") {
                    $('#nascimento').addClass("error-form");
                    isPacienteOk = false;
                }

                return isPacienteOk;
            }

            function validaInstituicao(instituicao) {
                var isInstituicaoOk = true;
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
                } else {
                    isInstituicaoOk = false;
                }
                if (!isInstituicaoOk) {
                    self.ocorreuErro("Inserir os dados corretamente");
                }
                return isInstituicaoOk;
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
                if (urlAtual.indexOf("/instituicao/home") !== -1) {
                    self.readAtendimentosByCriteria();
                } else if (urlAtual.indexOf("/editar") !== -1) {
                    if (urlAtual.indexOf("/pacientes") !== -1) {
                        self.getPaciente();
                    } else if (urlAtual.indexOf("profissionais") !== -1) {
                        self.getProfissional();
                    }
                } else if (urlAtual.indexOf("/instituicao/pacientes") !== -1) {
                    self.getPacienteList();
                } else if (urlAtual.indexOf("/instituicao/profissionais") !== -1) {
                    self.getProfissionalList();
                } else if (urlAtual.indexOf("/instituicao/meus-dados") !== -1) {
                    self.getInstituicao();
                } else if (urlAtual.indexOf("/instituicao/alterar-senha") !== -1) {
                    self.getInstituicao();
                }
            };
            self.activate();
        });


