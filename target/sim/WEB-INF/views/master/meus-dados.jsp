<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <!-- Incluindo a p�gina que cont�m os arquivos css comuns para as p�ginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/master/app.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2" ng-controller="MasterController as controller">
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
        <!-- Incluindo a JSP que cont�m a navbar do usu�rio Master -->
        <jsp:include page="/resources/templates/base-navbar-master.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Meus dados                            
                        </h5>                        
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/master/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Meus dados</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12" id="masterForm" name="masterForm">                                  
                                    <div class="form1">                                          
                                        <h5>
                                            INFORMA��ES PESSOAIS                                            
                                        </h5>                                        
                                        <div class="form divider"></div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nome" type="text" name="nome" class="validate" required="true" placeholder="Nome" ng-model="controller.master.nome" ng-class="{'error-form':masterForm.nome.$error.maxlength}"  ng-maxlength="60">
                                                <label for="nome">Nome (Obrigat�rio)</label>
                                                <span class="error" ng-class="error" ng-show="masterForm.nome.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="email" type="email" name="email" ng-change="controller.changeEmail()" class="" required="true" placeholder="Email" ng-model="controller.master.email" ng-class="{'error-form':masterForm.email.$error.maxlength}"  ng-maxlength="60">
                                                <label for="email">E-mail (Obrigat�rio)</label>
                                                <span class="error" ng-class="error" ng-show="masterForm.email.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div>                                                                                   
                                    </div>                                    
                                    <div class="botoes">
                                        <button type="button" ng-click="controller.editarMaster()" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Salvar</button>                                
                                        <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/master/home"/>">Cancelar</a>
                                    </div>
                                </form>
                            </div>                            
                        </div>                       
                    </div>
                </div>
            </div>
        </div>      
        <jsp:include page="/resources/templates/footer.jsp"/>   
        <!--Incluindo a jsp que cont�m os arquivos JS comuns para as p�ginas-->
        <jsp:include page="/resources/templates/base-script.jsp"/>  
        <script type="text/javascript" src="<c:url value="/resources/js/angular.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/angular/master/master-controller.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mask.js"/>"></script>
        <script>
                                                    $(function () {
                                                        $(".button-collapse").sideNav();
                                                    });
        </script>
    </body>
</html>