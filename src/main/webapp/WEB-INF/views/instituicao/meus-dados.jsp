<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <title>SIM</title>
        <!-- Incluindo a p�gina que cont�m os arquivos css comuns para as p�ginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/instituicao/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/table/responsive-table.css"/>" rel="stylesheet">
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
        <!-- Incluindo a JSP que cont�m a navbar do usu�rio Master -->
        <jsp:include page="/resources/templates/base-navbar-instituicao.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Meus Dados                            
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#"/>" class="breadcrumb link-ativo">Meus dados</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <form class="col s12 m12 l12" id="instituicaoForm" name="instituicaoForm">
                                <div class="form1">
                                    <h5>
                                        INFORMA��ES
                                    </h5>
                                    <div class="form divider"></div>
                                    <div class="row">
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="nome" type="text" name="nome" class="validate" placeholder="Nome" ng-model="controller.instituicao.nome" ng-class="{'error-form':instituicaoForm.nome.$error.maxlength}"  ng-maxlength="60">
                                            <label for="nome">Nome</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.nome.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="email" type="email" name="email" placeholder="Email" ng-change="controller.changeEmail()" ng-model="controller.instituicao.email" ng-class="{'error-form':instituicaoForm.email.$error.maxlength}"  ng-maxlength="60">
                                            <label for="email">E-mail</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.email.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                        </div> 
                                    </div> 
                                    <div class="row">
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="telefone" type="text" name="telefone" class="validate phone_with_ddd" placeholder="Ex.: 35998741536" ng-model="controller.instituicao.telefone" ng-class="{'error-form':instituicaoForm.telefone.$error.maxlength}"  ng-maxlength="20">
                                            <label for="telefone">Telefone</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.telefone.$error.maxlength">N�o � permitido mais que 20 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="versao" type="text" name="versao" readonly placeholder="Vers�o" ng-model="controller.instituicao.confVersao">
                                            <label for="versao">Vers�o</label>                                           
                                        </div> 
                                    </div> 
                                </div>
                                <div class="form2">
                                    <h5>
                                        ENDERE�O
                                    </h5>
                                    <div class="form divider"></div>
                                    <div class="row">
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="endereco" type="text" name="endereco" class="validate" placeholder="Rua" ng-model="controller.instituicao.endereco" ng-class="{'error-form':instituicaoForm.endereco.$error.maxlength}"  ng-maxlength="60">
                                            <label for="endereco">Rua</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.endereco.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="numero" type="text" name="numero" class="validate" placeholder="N�mero" ng-model="controller.instituicao.numero" ng-class="{'error-form':instituicaoForm.numero.$error.maxlength}"  ng-maxlength="15">
                                            <label for="numero">N�mero</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.numero.$error.maxlength">N�o � permitido mais que 15 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="bairro" type="text" name="bairro" class="validate" placeholder="Bairro" ng-model="controller.instituicao.bairro" ng-class="{'error-form':instituicaoForm.bairro.$error.maxlength}"  ng-maxlength="60">
                                            <label for="bairro">Bairro</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.bairro.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                        </div> 
                                    </div>
                                    <div class="row">
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="cidade" type="text" name="cidade" class="validate" placeholder="Cidade" ng-model="controller.instituicao.cidade" ng-class="{'error-form':instituicaoForm.cidade.$error.maxlength}"  ng-maxlength="60">
                                            <label for="cidade">Cidade</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.cidade.$error.maxlength">N�o � permitido mais que 60 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="uf" type="text" name="uf" class="validate" placeholder="UF" ng-model="controller.instituicao.uf" ng-class="{'error-form':instituicaoForm.uf.$error.maxlength}"  ng-maxlength="2">
                                            <label for="uf">UF</label>
                                            <span class="error" ng-class="error" ng-show="instituicaoForm.uf.$error.maxlength">N�o � permitido mais que 2 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="cep" type="text" name="cep" class="validate cep" placeholder="CEP" ng-model="controller.instituicao.cep">
                                            <label for="cep">CEP</label>
                                        </div> 
                                    </div>
                                </div>
                                <div class="botoes">
                                    <button type="button" ng-click="controller.editarInstituicao()" ng-disabled="instituicaoForm.$invalid" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Salvar</button>                                
                                    <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/instituicao/home"/>">Cancelar</a>
                                </div>
                            </form>
                        </div>                                             
                    </div>
                </div>
            </div>
        </div>      
        <jsp:include page="/resources/templates/footer.jsp"/>   
        <!--Incluindo a jsp que cont�m os arquivos JS comuns para as p�ginas-->
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
