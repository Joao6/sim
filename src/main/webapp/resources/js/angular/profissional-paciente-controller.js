
angular.module("SIM_APP", [])
        .value('urlBase', '/instituicao/associar/')
        .controller("ProfissionalPacienteController", function ($http, urlBase) {
            var self = this;

            self.chamados = [];
            self.profissionalList = [];
            self.pacienteList = [];

            self.paciente = undefined;
            self.profissional = undefined;

            self.pacientes = [];

            self.pacientesID = [];

            self.pacienteFiltrado = [];

            self.associacaoProfissionalPaciente = {};

            self.init = function () {
                self.openModalLoading();
            };

            self.getProfissionalList = function () {

                $http({
                    method: 'GET',
                    url: urlBase + 'profissionais/'
                }).then(function successCallback(response) {
                    self.profissionalList = response.data;
                    self.profissional = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.getPacienteList = function () {

                $http({
                    method: 'GET',
                    url: urlBase + 'pacientes/'
                }).then(function successCallback(response) {
                    self.pacienteList = response.data;
                    self.paciente = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.getPacienteAssociado = function () {
                if (self.profissional !== null && self.profissional !== undefined) {
                    self.openModalLoading();
                    $http({
                        method: 'GET',
                        url: urlBase + 'paciente-associado/' + self.profissional
                    }).then(function successCallback(response) {
                        self.pacientes = response.data;
                        self.paciente = undefined;
                        self.filtraPacientes();
                        self.closeModalLoading();
                    }, function errorCallback(response) {
                        self.ocorreuErro();
                        self.closeModalLoading();
                    });
                }
            };

            self.filtraPacientes = function () {
                self.pacienteFiltrado = [];
                var encontrou = false;
                for (i = 0; i < self.pacienteList.length; i++) {
                    for (j = 0; j < self.pacientes.length; j++) {
                        if (self.pacienteList[i].id === self.pacientes[j].id) {
                            encontrou = true;
                            break;
                        }
                    }
                    if (encontrou !== true) {
                        self.pacienteFiltrado.push(self.pacienteList[i]);
                    }
                    encontrou = false;
                }
            };


            self.addPaciente = function (paciente) {
                if (paciente.id !== "" && paciente.id !== undefined && paciente.id !== null) {
                    // Remover do select
                    self.pacienteFiltrado = self.pacienteFiltrado.filter(function (a) {
                        if (a.id != paciente.id) {
                            return a;
                        } else {
                            self.pacientes.push(angular.copy(a));
                        }
                    });
                }

            };

            self.removePaciente = function (id) {
                if (id !== "" && id !== undefined) {
                    self.pacientes = self.pacientes.filter(function (a) {
                        if (a.id !== id) {
                            return a;
                        } else {
                            self.pacienteFiltrado.push(angular.copy(a));
                        }
                    });
                }
            };

            self.associar = function () {
                self.openModalLoading();

                self.associacaoProfissionalPaciente.idProfissional = self.profissional;
//                var idProfissional = self.profissional;
                for (i = 0; i < self.pacientes.length; i++) {
                    self.pacientesID.push(self.pacientes[i].id);
                }
                self.associacaoProfissionalPaciente.pacienteList = self.pacientesID;

                $http({
                    method: 'POST',
                    url: urlBase + 'associar/',
                    data: self.associacaoProfissionalPaciente
                }).then(function successCallback(response) {
                    Materialize.toast('Associação cadastrada com suceeso!', 2000, 'green rounded');
                    setTimeout(function () {
                        window.location.href = '/instituicao/home';
                    }, 1500);
                    self.closeModalLoading();
                }, function errorCallback(response) {
                    self.ocorreuErro();
                    self.closeModalLoading();
                });
            };

            self.ocorreuErro = function () {
                alert("Ocorreu um erro inesperado!");
            };

            self.openModalLoading = function () {
                $("#modal-loading").modal('open');
            };

            self.closeModalLoading = function () {
                window.setTimeout(function () {
                    $("#modal-loading").modal('close');
                }, 500);
            };

            self.activate = function () {
                self.closeModalLoading();
                self.getProfissionalList();
                self.getPacienteList();
            };
            self.activate();
        });


