<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <!-- Incluindo a página que contém os arquivos css comuns para as páginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/profissional/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/table/responsive-table.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2" ng-controller="ProfissionalController as controller" ng-init="controller.init()">
        <!-- MODAL LOADING -->
        <div class="modal" id="modal-loading">
            <div class="modal-content">
                <div class="preloader-wrapper big active">
                    <div class="spinner-layer spinner-blue-only">
                        <div class="circle-clipper left">
                            <div class="circle"></div>
                        </div><div class="gap-patch">
                            <div class="circle"></div>
                        </div><div class="circle-clipper right">
                            <div class="circle"></div>
                        </div>
                    </div>
                </div>
                <h5>Carregando...</h5>
            </div>            
        </div>
        <!-- Incluindo a JSP que contém a navbar do usuário Master -->
        <jsp:include page="/resources/templates/base-navbar-profissional.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Histórico de atendimentos                            
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/profissional/home"/>" class="breadcrumb link-ativo">Home</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12">
                                    <div class="row">
                                        <div class="input-field inline col s12 l4 m4">
                                            <i class="material-icons prefix">search</i>
                                            <input id="nomePaciente" type="text" name="nomePaciente" ng-change="controller.readAtendimentosPacienteAssociado()" class="validate" ng-model="controller.nomePaciente">
                                            <label for="nomePaciente">Filtrar por paciente</label>
                                        </div>
                                        <div class="input-field col inline s12 l4 m4">
                                            <i class="material-icons prefix">search</i>
                                            <input id="nomeProfissional" type="text" name="nomeProfissional" ng-change="controller.readAtendimentosPacienteAssociado()" class="validate" ng-model="controller.nomeProfissional">
                                            <label for="nomeProfissional">Filtrar por profissional</label>
                                        </div>
                                        <div class="input-field inline col s12 l4 m4">            
                                            <i class="material-icons prefix">search</i>
                                            <input id="data" type="date" name="data" class="datepicker" ng-change="controller.readAtendimentosPacienteAssociado()" ng-model="controller.dataAtendimento">
                                            <label for="data">Filtrar por data</label>                                                                                        
                                        </div>
                                        <!--<button type="submit" class="input-field inline btn waves-effect waves-light col s12 l8 m8 push-l2 push-m2 blue darken-4">Filtrar</button>-->                                
                                    </div>                                
                                </form>
                            </div>
                            <div ng-show="controller.atendimentoListEmpty()"><strong>Não foi encontrado nenhum atendimento!</strong></div>
                            <div ng-hide="controller.atendimentoListEmpty()" class="row">
                                <table class="table bordered striped">
                                    <thead class="thead">
                                        <tr class="tr">
                                            <th class="th">Data</th>
                                            <th class="th">Profissional</th>
                                            <th class="th">Paciente</th>
                                            <th class="th">Apontamentos</th>                                            
                                        </tr>
                                    </thead>
                                    <tbody class="tbody">
                                        <tr ng-repeat="atendimento in controller.atendimentoList">
                                            <td data-label="Data" class="td">{{atendimento.dataApontamento}}</td>
                                            <td data-label="Profissional" class="td">{{atendimento.profissional.nome}}</td>
                                            <td data-label="Paciente" class="td">{{atendimento.paciente.nome}}</td>
                                            <td data-label="Apontamento" class="td">{{atendimento.apontamento}}</td>                                               
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row center row-pagination">
                            <ul class="pagination">
                                <li class="waves-effect">
                                    <a href="#" ng-show="((controller.offset - controller.limit) >= 0)" ng-click="controller.pagination('ANTERIOR')">
                                        <i class="material-icons">chevron_left</i>
                                    </a>
                                </li>                                

                                <li class="waves-effect">
                                    <a href="#" ng-show="((controller.offset + controller.limit) < controller.count)" ng-click="controller.pagination('PROXIMO')">
                                        <i class="material-icons">chevron_right</i>
                                    </a>
                                </li>
                            </ul>
                            <strong ng-hide="controller.atendimentoListEmpty()">Monstrando {{controller.atendimentoListLenght()}} atendimentos de um total de {{controller.count}}.</strong>                            
                        </div>
                    </div>
                </div>
            </div>
        </div>      
        <jsp:include page="/resources/templates/footer.jsp"/>   
        <!--Incluindo a jsp que contém os arquivos JS comuns para as páginas-->
        <jsp:include page="/resources/templates/base-script.jsp"/>  
        <script type="text/javascript" src="<c:url value="/resources/js/angular.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/angular/profissional/profissional-controller.js"/>"></script>
        <script>
                                $(function () {
                                    $(".button-collapse").sideNav();
                                });
        </script>
    </body>
</html>
