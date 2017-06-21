<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <title>SIM</title>
        <!-- Incluindo a página que contém os arquivos css comuns para as páginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/instituicao/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/table/responsive-table.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2" ng-controller="ProfissionalPacienteController as controller" ng-init="controller.init()">
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
        <jsp:include page="/resources/templates/base-navbar-instituicao.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Associar profissionais à pacientes
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>  
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Associar</a>  
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <h5>Selecione o profissional</h5>
                            <select class="browser-default col s12 m6 l6 select" ng-change="controller.getPacienteAssociado()" ng-model="controller.profissional">
                                <option disabled selected value="">Selecione o profissional</option>
                                <option ng-repeat="profissional in controller.profissionalList" value="{{profissional.id}}" 
                                        >{{profissional.nome}}</option>
                            </select>
                        </div>
                        <div class="row">
                            <h5>E logo depois os seus pacientes</h5>
                            <div class="div-pacientes">
                                <form>
                                    <p ng-repeat="paciente in controller.pacienteFiltrado" ng-model="paciente.id">
                                        <input type="checkbox" id="paciente-{{paciente.id}}" ng-click="controller.addPaciente(paciente)"/>
                                        <label for="paciente-{{paciente.id}}">{{paciente.nome}}</label>
                                    </p>                                    
                                </form>
                            </div>
                        </div>
                        <span class="row">Os pacientes abaixo serão associados ao profissional selecionado {{controller.profissional.nome}}</span>
                        <div class="row">                            
                            <div class="chip pratica-chip" ng-repeat="paciente in controller.pacientes"> 
                                <span>
                                    <img src="<c:url value="/resources/img/ico-paciente.png"/>" alt="{{paciente.nome}}">
                                    <!--<p class="black-text">{{paciente.nome}}</p>-->
                                    <a ng-href="#"  class="black-text">{{paciente.nome}}</a>
                                </span>
                                <a ng-href="#" ng-click="controller.removePaciente(paciente.id)">
                                    <i class="material-icons">close</i>
                                </a>
                            </div>
                        </div>
                        <div class="row">
                            <a class="btn waves-effect waves-light blue darken-4 right col s12 l3 m3 btn-up" ng-href="#" ng-click="controller.associar()">Associar</a>
                            <a class="btn waves-effect waves-light grey darken-3 right col s12 l3 m3 botoes" ng-href="<c:url value="/instituicao/home"/>">Cancelar</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>      
        <jsp:include page="/resources/templates/footer.jsp"/>   
        <!--Incluindo a jsp que contém os arquivos JS comuns para as páginas-->
        <jsp:include page="/resources/templates/base-script.jsp"/>  
        <script type="text/javascript" src="<c:url value="/resources/js/angular.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/angular/profissional-paciente-controller.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.maskedinput.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mask.js"/>"></script>
        <script>
                                        $(function () {
                                            $(".button-collapse").sideNav();
                                        });
        </script>
    </body>
</html>
