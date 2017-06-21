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
    <body class="grey lighten-2" ng-controller="ProfissionalController as controller">
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
                            EDITAR ATENDIMENTO  
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/profissional/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="/profissional/meus-atendimentos"/>" class="breadcrumb link-anterior">Meus atendimentos</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Editar</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                       
                        <div class="row">
                            <div class="row">
                                <input id="idAtendimento" type="hidden" value="${idAtendimento}">
                                <form class="col s12" method="post">
                                    <div class="row">
                                        <div class="input-field col s12 l6 m6">
                                            <input readonly id="paciente" type="text" placeholder="Paciente" name="paciente" class="" value="{{controller.atendimento.paciente.nome}}">
                                            <label for="paciente">Paciente</label>
                                        </div> 
                                        <div class="input-field col s12 l6 m6">
                                            <input readonly id="dataa" type="text" placeholder="Data" name="dataApontamento" class="" value="{{controller.atendimento.dataApontamento}}">
                                            <label for="dataa">Data</label>
                                        </div>
                                    </div>  
                                    <div class="row">
                                        <div class="input-field col s12 l12 m12">
                                            <i class="material-icons prefix">mode_edit</i>
                                            <textarea readonly="" id="apontamentos" name="apontamento" class="materialize-textarea" placeholder="Apontamentos" maxlength="400" data-length="400">{{controller.atendimento.apontamento}}</textarea>
                                            <label for="apontamentos">Apontamentos</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12 l12 m12">
                                            <i class="material-icons prefix">mode_edit</i>
                                            <textarea id="capontamento" name="capontamento" class="materialize-textarea" ng-model="controller.capontamento" maxlength="${lenght}" data-length="${lenght}"></textarea>
                                            <label for="capontamento">Complementar apontamentos</label>
                                        </div>
                                    </div>
                                    <div class="row row-btn">
                                        <div>
                                            <button type="button" ng-click="controller.complementarApontamento()" class="buttons right blue darken-4 btn waves-effect waves-light col s12 l3 m3">Salvar</button>                                
                                            <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/profissional/meus-atendimentos"/>">Cancelar</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
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
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mask.js"/>"></script>
    </body>
</html>
