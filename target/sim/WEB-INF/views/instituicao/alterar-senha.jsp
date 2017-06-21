<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <title>SIM</title>
        <!-- Incluindo a página que contém os arquivos css comuns para as páginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/master/app.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2" ng-controller="InstituicaoController as controller">
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
        <!-- Incluindo a JSP que contém a navbar do usuário Instituição -->
        <jsp:include page="/resources/templates/base-navbar-instituicao.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Alterar senha                         
                        </h5>                        
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Alterar senha</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12" id="instForm" name="instForm">                                  
                                    <div class="form1">                                                                                                                                                               
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="senhaA" type="password" name="senhaA" class="validate" required="true"  ng-model="controller.senhaA" ng-class="{'error-form':instForm.senhaA.$error.maxlength}"  ng-maxlength="15">
                                                <label for="senhaA">Senha atual</label>
                                                <span class="error" ng-class="error" ng-show="instForm.nome.$error.maxlength">Não é permitido mais que 15 caracteres!</span>
                                            </div> 
                                        </div> 
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="senhaN" type="password" name="senhaN" class="validate" required="true" ng-model="controller.senhaN" ng-class="{'error-form':instForm.senhaN.$error.maxlength}"  ng-maxlength="15">
                                                <label for="senhaN">Nova senha</label>
                                                <span class="error" ng-class="error" ng-show="instForm.nome.$error.maxlength">Não é permitido mais que 15 caracteres!</span>
                                            </div>   
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="senhaC" type="password" name="senhaC" class="validate" required="true" ng-model="controller.senhaC" ng-class="{'error-form':instForm.senhaC.$error.maxlength}"  ng-maxlength="15">
                                                <label for="senhaC">Confirmar nova senha</label>
                                                <span class="error" ng-class="error" ng-show="instForm.nome.$error.maxlength">Não é permitido mais que 15 caracteres!</span>
                                            </div>  
                                        </div>                                            

                                    </div>                                    
                                    <div class="botoes">
                                        <button type="button" ng-click="controller.alterarSenha()" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Salvar</button>                                
                                        <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/instituicao/home"/>">Cancelar</a>
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
        <script type="text/javascript" src="<c:url value="/resources/js/angular/instituicao/instituicao-controller.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mask.js"/>"></script>
        <script>
                                                    $(function () {
                                                        $(".button-collapse").sideNav();
                                                    });
                                                    
        </script>
    </body>
</html>
