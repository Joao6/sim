<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <!-- Incluindo a página que contém os arquivos css comuns para as páginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/master/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/table/responsive-table.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2" ng-controller="MasterController as controller" ng-init="controller.init()">
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
        <jsp:include page="/resources/templates/base-navbar-master.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Cadastrar instituição                            
                        </h5>                        
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/master/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Cadastrar Instituição</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12" id="instituicaoForm" name="instituicaoForm">                                    
                                    <div class="form1">  
                                        <h5>
                                            INFORMAÇÕES
                                        </h5>
                                        <div class="form divider"></div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nome" type="text" name="nome" class="validate" ng-model="controller.instituicao.nome" ng-class="{'error-form':instituicaoForm.nome.$error.maxlength}"  ng-maxlength="60">
                                                <label for="nome">Nome (Obrigatório)</label>
                                                <span class="error" ng-class="error" ng-show="instituicaoForm.nome.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="email" type="email" name="email" class="validate" ng-model="controller.instituicao.email" ng-focus="controller.cleanEmailError()" ng-blur="controller.validaEmail(controller.instituicao.email)">
                                                <label for="email">E-mail (Obrigatório)</label>
                                            </div> 
                                        </div> 
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="senha" type="password" name="senha" class="validate" ng-model="controller.instituicao.senha"  ng-class="{'error-form':instituicaoForm.senha.$error.maxlength}"  ng-maxlength="60">
                                                <label for="senha">Senha</label>
                                                <span ng-class="error" ng-show="instituicaoForm.senha.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="csenha" type="password" name="csenha" class="validate" ng-model="controller.csenha" ng-class="{'error-form':instituicaoForm.csenha.$error.maxlength}"  ng-maxlength="60">
                                                <label for="csenha">Confirmar senha</label>
                                                <span ng-class="error" ng-show="instituicaoForm.csenha.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div>                                     
                                        <div class="row"> 
                                            <div class="input-field col s12 l6 m6">
                                                <select name="configuracao" id="configuracao" ng-model="controller.instituicao.confVersao">
                                                    <option value="" disabled selected>Selecione uma versão</option>                                                    
                                                    <option value="FREE">FREE</option>
                                                    <option value="PRO">PRO</option>
                                                </select>
                                                <label>Configuração da Versão</label>
                                            </div>                                           
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="telefone" type="text" name="telefone" class="validate phone_with_ddd" ng-model="controller.instituicao.telefone">
                                                <label for="telefone">Telefone</label>
                                            </div> 
                                        </div>                                       
                                    </div>

                                    <div class="form2">
                                        <h5>
                                            ENDEREÇO
                                        </h5>
                                        <div class="form divider"></div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="endereco" type="text" name="endereco" class="validate" ng-model="controller.instituicao.endereco" ng-class="{'error-form':instituicaoForm.endereco.$error.maxlength}"  ng-maxlength="60">
                                                <label for="endereco">Rua</label>
                                                <span ng-class="error" ng-show="instituicaoForm.endereco.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="numero" type="text" name="numero" class="validate" ng-model="controller.instituicao.numero" ng-class="{'error-form':instituicaoForm.numero.$error.maxlength}"  ng-maxlength="10">
                                                <label for="numero">Número</label>
                                                <span ng-class="error" ng-show="instituicaoForm.numero.$error.maxlength">Não é permitido mais que 10 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="bairro" type="text" name="bairro" class="validate" ng-model="controller.instituicao.bairro" ng-class="{'error-form':instituicaoForm.bairro.$error.maxlength}"  ng-maxlength="60">
                                                <label for="bairro">Bairro</label>
                                                <span ng-class="error" ng-show="instituicaoForm.bairro.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cidade" type="text" name="cidade" class="validate" ng-model="controller.instituicao.cidade" ng-class="{'error-form':instituicaoForm.cidade.$error.maxlength}"  ng-maxlength="60">
                                                <label for="cidade">Cidade</label>
                                                <span ng-class="error" ng-show="instituicaoForm.cidade.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="uf" type="text" name="uf" class="validate" ng-model="controller.instituicao.uf" ng-class="{'error-form':instituicaoForm.uf.$error.maxlength}"  ng-maxlength="2">
                                                <label for="uf">UF</label>
                                                <span ng-class="error" ng-show="instituicaoForm.uf.$error.maxlength">Não é permitido mais que 2 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cep" type="text" name="cep" class="validate cep" ng-model="controller.instituicao.cep">
                                                <label for="cep">CEP</label>
                                            </div> 
                                        </div>
                                    </div>
                                    <div class="botoes">
                                        <button type="button" ng-click="controller.salvarInstituicao()" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Cadastrar</button>                                
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
        <!--Incluindo a jsp que contém os arquivos JS comuns para as páginas-->
        <jsp:include page="/resources/templates/base-script.jsp"/>  
        <script type="text/javascript" src="<c:url value="/resources/js/angular.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/angular/master/master-controller.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.maskedinput.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mask.js"/>"></script>
        <script>
                                                    $(function () {
                                                        $(".button-collapse").sideNav();
                                                    });
        </script>
    </body>
</html>
