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

        <!-- MODAL RESPONDER MENSAGEM -->
        <div class="modal" id="modal-alterar-senha">
            <div class="modal-content">
                <h3 class="valign-wrapper" style="font-size: 25pt"><i class="material-icons valign" style=" font-size: 25pt !important">vpn_key</i> 
                    <span class="valign">Alterar a senha de {{controller.instituicao.nome}}</span>
                </h3>
                <form name="formAlterarSenha" id="formAlterarSenha">                  
                    <div class="row">                        
                        <div class="input-field inline col s12 l12 m12">                                            
                            <input required="" id="senhaN" type="password" name="senhaN" class="validate" ng-model="controller.senhaN"  ng-class="{'error-form':formAlterarSenha.senhaN.$error.maxlength}"  ng-maxlength="60">
                            <label for="senhaN">Nova senha</label>
                            <span ng-class="error" ng-show="formAlterarSenha.senhaN.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="input-field inline col s12 l12 m12">                                            
                            <input required="" id="senhaC" type="password" name="senhaC" class="validate" ng-model="controller.senhaC" ng-class="{'error-form':formAlterarSenha.senhaC.$error.maxlength}"  ng-maxlength="60">
                            <label for="senhaC">Confirmar senha</label>
                            <span ng-class="error" ng-show="formAlterarSenha.senhaC.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                        </div> 
                    </div>
                </form>
            </div>
            <div class="modal-footer card-action">
                <button type="button" ng-click="controller.alterarSenhaInstituicao()" ng-disabled="formAlterarSenha.$invalid" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Salvar</button>                                
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat grey white-text darken-2" style="margin-right: 5px;">Cancelar</a>
            </div>
        </div>

        <!-- Incluindo a JSP que contém a navbar do usuário Master -->
        <jsp:include page="/resources/templates/base-navbar-master.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Editar instituição                            
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/master/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#"/>" class="breadcrumb link-ativo">Editar instituição</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <input type="hidden" value="${idInstituicao}" id="idInstituicao">
                            <form class="col s12 m12 l12" id="instituicaoForm" name="instituicaoForm">
                                <div class="form1">
                                    <h5>
                                        INFORMAÇÕES
                                    </h5>
                                    <div class="form divider"></div>
                                    <div class="row">
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="nome" type="text" name="nome" class="validate" placeholder="Nome" ng-model="controller.instituicao.nome" ng-class="{'error-form':formAlterarSenha.nome.$error.maxlength}"  ng-maxlength="60">
                                            <label for="nome">Nome</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.nome.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="email" type="email" name="email" placeholder="Email" ng-change="controller.changeEmail()" ng-model="controller.instituicao.email" ng-class="{'error-form':formAlterarSenha.email.$error.maxlength}"  ng-maxlength="60">
                                            <label for="email">E-mail</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.email.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                        </div> 
                                    </div> 
                                    <div class="row">
                                        <div class="input-field inline col s12 l6 m6">                                            
                                            <input id="telefone" type="text" name="telefone" class="validate phone_with_ddd" placeholder="Telefone" ng-model="controller.instituicao.telefone" ng-class="{'error-form':formAlterarSenha.telefone.$error.maxlength}"  ng-maxlength="20">
                                            <label for="telefone">Telefone</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.telefone.$error.maxlength">Não é permitido mais que 20 caracteres!</span>
                                        </div> 
                                        <div class="input-field col s12 l6 m6">
                                            <select name="configuracao" id="configuracao" ng-model="controller.instituicao.confVersao">
                                                <option value="{{controller.instituicao.confVersao}}" disabled selected>{{controller.instituicao.confVersao}}</option>                                                    
                                                <option value="FREE">FREE</option>
                                                <option value="PRO">PRO</option>
                                            </select>
                                            <label>Configuração da Versão</label>
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
                                            <input id="endereco" type="text" name="endereco" class="validate" placeholder="Rua" ng-model="controller.instituicao.endereco" ng-class="{'error-form':formAlterarSenha.endereco.$error.maxlength}"  ng-maxlength="60">
                                            <label for="endereco">Rua</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.endereco.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="numero" type="text" name="numero" class="validate" placeholder="Número" ng-model="controller.instituicao.numero" ng-class="{'error-form':formAlterarSenha.numero.$error.maxlength}"  ng-maxlength="15">
                                            <label for="numero">Número</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.numero.$error.maxlength">Não é permitido mais que 15 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="bairro" type="text" name="bairro" class="validate" placeholder="Bairro" ng-model="controller.instituicao.bairro" ng-class="{'error-form':formAlterarSenha.bairro.$error.maxlength}"  ng-maxlength="60">
                                            <label for="bairro">Bairro</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.bairro.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                        </div> 
                                    </div>
                                    <div class="row">
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="cidade" type="text" name="cidade" class="validate" placeholder="Cidade" ng-model="controller.instituicao.cidade" ng-class="{'error-form':formAlterarSenha.cidade.$error.maxlength}"  ng-maxlength="60">
                                            <label for="cidade">Cidade</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.cidade.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="uf" type="text" name="uf" class="validate" placeholder="UF" ng-model="controller.instituicao.uf" ng-class="{'error-form':formAlterarSenha.uf.$error.maxlength}"  ng-maxlength="2">
                                            <label for="uf">UF</label>
                                            <span class="error" ng-class="error" ng-show="formAlterarSenha.uf.$error.maxlength">Não é permitido mais que 2 caracteres!</span>
                                        </div> 
                                        <div class="input-field inline col s12 l4 m4">                                            
                                            <input id="cep" type="text" name="cep" class="validate cep" placeholder="CEP" ng-model="controller.instituicao.cep">
                                            <label for="cep">CEP</label>
                                        </div> 
                                    </div>
                                </div>
                                <div class="botoes">
                                    <button type="button" ng-click="controller.editarInstituicao()" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Salvar</button>                                
                                    <a class="buttons btn waves-effect waves-light blue darken-4 right col s12 l3 m3" href="#" ng-click="controller.openModal('alterar-senha')">Alterar senha</a>
                                    <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/master/home"/>">Cancelar</a>
                                </div>
                            </form>
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
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mask.js"/>"></script>
        <script>
                                                $(function () {
                                                    $(".button-collapse").sideNav();
                                                });
        </script>
    </body>
</html>
