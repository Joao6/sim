
angular.module("SIM_APP", [])
        .value('urlBase', '/profissional/rest/')
        .controller("MensagemController", function ($http, urlBase, $location) {
            var self = this;
            self.mensagemList = [];
            self.mensagem = {};
            self.mensagemReply = {};
            self.tipoPesquisa = 'RECEBIDAS';

            self.nomeProfissional = undefined;
            self.profissionalList = [];
            self.destinatario = {};
            self.remetente = {};

            self.count = undefined;
            self.offset = 0;
            self.limit = 10;
            
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

            self.readMensagensRecebidas = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }

                self.tipoPesquisa = 'RECEBIDAS';

                $http({
                    method: 'GET',
                    url: urlBase + 'mensagem/recebidas',
                    params: {'nomeProfissional': self.nomeProfissional, 'offset': self.offset}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.mensagemList = response.data.dados;
                    self.count = response.data.count;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar as mensagens!");
                });
            };

            self.readMensagensEnviadas = function () {
                if (!self.isPagination) {
                    self.offset = 0;
                }

                self.tipoPesquisa = 'ENVIADAS';

                $http({
                    method: 'GET',
                    url: urlBase + 'mensagem/enviadas',
                    params: {'nomeProfissional': self.nomeProfissional, 'offset': self.offset}
                }).then(function successCallback(response) {
                    self.isFirst = false;
                    self.isPagination = false;
                    self.mensagemList = response.data.dados;
                    self.count = response.data.count;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar as mensagens!");
                });
            };

            self.readMensagens = function () {
                if (self.tipoPesquisa === 'RECEBIDAS') {
                    self.readMensagensRecebidas();
                } else {
                    self.readMensagensEnviadas();
                }
            };

            self.enviarMensagem = function () {
                self.openModalLoading();
                self.destinatario.id = self.mensagem.destinatario;
                self.mensagem.destinatario = self.destinatario;
                self.mensagem.remetente = self.remetente;
                self.mensagem.dataEnvio = getCurrentDate();
                $http({
                    method: 'POST',
                    url: urlBase + 'mensagem/nova',
                    data: self.mensagem
                }).then(function successCallback(response) {
                    self.mensagem = undefined;
                    Materialize.toast('Mensagem enviada com sucesso!', 2000, 'green rounded');
                    self.readMensagensEnviadas();
                    self.readMensagensRecebidas();
                    self.closeModalLoading();
                    $("#modal-nova-mensagem").modal('close');
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao enviar esta mensagem!");
                    self.closeModalLoading();
                });
            };

            self.responderMensagem = function () {
                self.openModalLoading();
                self.mensagem.dataEnvio = getCurrentDate();
                $http({
                    method: 'POST',
                    url: urlBase + 'mensagem/nova',
                    data: self.mensagem
                }).then(function successCallback(response) {
                    self.mensagem = undefined;
                    Materialize.toast('Mensagem enviada com sucesso!', 2000, 'green rounded');
                    self.readMensagensEnviadas();
                    self.readMensagensRecebidas();
                    self.closeModalLoading();
                    $("#modal-responder").modal('close');
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao enviar esta mensagem!");
                    self.closeModalLoading();
                });
            };

            self.mensagemLida = function (mensagem) {
                self.openModalLoading();
                mensagem.dataLeitura = getCurrentDate();
                mensagem.isLida = true;
                $http({
                    method: 'PUT',
                    url: urlBase + 'mensagem/lida',
                    data: mensagem
                }).then(function successCallback(response) {
                    self.readMensagensRecebidas();
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro("Um erro inesperado aconteceu!");
                    self.closeModalLoading();
                });
            };

            self.openModalResponder = function (modal, mensagem) {
                $("#modal-" + modal).modal('open');
                self.destinatario = mensagem.remetente;
                self.mensagem.destinatario = self.destinatario;
                self.mensagem.remetente = self.remetente;
            };

            self.readProfissionalList = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'list-profissionais'
                }).then(function successCallback(response) {
                    self.profissionalList = response.data;
                }, function errorCallback(response) {
                    self.ocorreuErro("Erro ao buscar a lista de profissionais!");
                });
            };

            self.isDestinatarioEmpty = function (mensagem) {
                if (mensagem.destinatario === undefined || mensagem.destinatario === null) {
                    return true;
                } else {
                    return false;
                }
            };

            self.isMensagemLida = function (mensagem) {
                if (mensagem.isLida === true) {
                    return true;
                } else {
                    return false;
                }
            };
            
            self.isMensagemListEmpty = function () {
                if (self.mensagemList.length === 0 || self.mensagemList === null) {
                    return true;
                } else {
                    return false;
                }
            };

            self.isRemetenteEmpty = function (mensagem) {
                if (mensagem.remetente === undefined || mensagem.remetente === null) {
                    return true;
                } else {
                    return false;
                }
            };

            self.isLeituraEmpty = function (mensagem) {
                if (mensagem.dataLeitura === undefined || mensagem.dataLeitura === null) {
                    return true;
                } else {
                    return false;
                }
            };

            function getCurrentDate() {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!
                var yyyy = today.getFullYear();
                var hour = today.getHours();
                var minute = today.getMinutes();

                if (dd < 10) {
                    dd = '0' + dd;
                }

                if (mm < 10) {
                    mm = '0' + mm;
                }

                if (hour < 10) {
                    hour = '0' + hour;
                }

                if (minute < 10) {
                    minute = '0' + minute;
                }

                today = mm + '/' + dd + '/' + yyyy + ' ' + hour + ':' + minute + ':00';
                return today;
            }

            self.openModal = function (modal) {
                $("#modal-" + modal).modal('open');
            };

            self.openModalLoading = function () {
                $("#modal-loading").modal('open');
            };

            self.closeModalLoading = function () {
                window.setTimeout(function () {
                    $("#modal-loading").modal('close');
                }, 500);
            };

            self.ocorreuErro = function (erro) {
                if (erro === undefined || erro === "") {
                    erro = "Algum erro inesperado aconteceu!";
                }
                Materialize.toast(erro, 4000, 'red rounded');
            };

            self.mensagemListLenght = function () {
                return self.mensagemList.length;
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
                self.readMensagens();
            };

            self.activate = function () {
                self.closeModalLoading();
                var urlAtual = $location.absUrl();
                if (urlAtual.indexOf("/profissional/mensagens") !== -1) {
                    self.readMensagensRecebidas();
                    self.readProfissionalList();
                } 
            };
            self.activate();
        });




