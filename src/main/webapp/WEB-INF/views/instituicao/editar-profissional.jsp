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
        <!-- Incluindo a JSP que contém a navbar do usuário Master -->
        <jsp:include page="/resources/templates/base-navbar-instituicao.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Editar profissional                            
                        </h5>                        
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="/instituicao/profissionais"/>" class="breadcrumb link-anterior">Profissionais</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Editar</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <input type="hidden" value="${idProfissional}" id="idProfissional">
                                <form class="col s12" id="profissionalForm" name="profissionalForm">                                  
                                    <div class="form1">  
                                        <h5>
                                            INFORMAÇÕES PESSOAIS
                                        </h5>
                                        <div class="form divider"></div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nome" type="text" name="nome" class="validate" required="true" placeholder="Nome" ng-model="controller.profissional.nome" ng-class="{'error-form':profissionalForm.nome.$error.maxlength}"  ng-maxlength="60">
                                                <label for="nome">Nome do profissional (Obrigatório)</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.nome.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="email" type="email" name="email" readonly="" class="" required="true" placeholder="Email" ng-model="controller.profissional.email" ng-class="{'error-form':profissionalForm.email.$error.maxlength}"  ng-maxlength="60">
                                                <label for="email">E-mail (Obrigatório)</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.email.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div> 
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="profissao" type="text" name="profissao" required="true" placeholder="Profissão" class="validate" ng-model="controller.profissional.profissao" ng-class="{'error-form':profissionalForm.profissao.$error.maxlength}"  ng-maxlength="60">
                                                <label for="profissao">Profissão (Obrigatório)</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.profissao.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="telefone" type="text" name="telefone" required="true" placeholder="Telefone" class="validate phone_with_ddd" ng-model="controller.profissional.telefone">
                                                <label for="telefone">Telefone (Obrigatório)</label>
                                            </div> 
                                        </div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cpf" type="text" name="cpf" class="validate cpf" placeholder="CPF" ng-model="controller.profissional.cpf">
                                                <label for="cpf">CPF</label>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="rg" type="text" name="rg" placeholder="RG" class="validate" ng-model="controller.profissional.rg" ng-class="{'error-form':profissionalForm.rg.$error.maxlength}"  ng-maxlength="15">
                                                <label for="rg">RG</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.rg.$error.maxlength">Não é permitido mais que 15 caracteres!</span>
                                            </div> 
                                            
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="registroDeConselho" type="text" placeholder="Registro de Conselho" name="registroDeConselho" class="validate" ng-model="controller.profissional.registroDeConselho" ng-class="{'error-form':profissionalForm.registroDeConselho.$error.maxlength}"  ng-maxlength="40">
                                                <label for="registroDeConselho">Registro de Conselho</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.registroDeConselho.$error.maxlength">Não é permitido mais que 40 caracteres!</span>
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
                                                <input id="endereco" type="text" name="endereco" placeholder="Rua" class="validate" ng-model="controller.profissional.endereco" ng-class="{'error-form':profissionalForm.endereco.$error.maxlength}"  ng-maxlength="60">
                                                <label for="endereco">Rua</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.endereco.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="numero" type="text" name="numero" placeholder="Número" class="validate" ng-model="controller.profissional.numero" ng-class="{'error-form':profissionalForm.numero.$error.maxlength}"  ng-maxlength="10">
                                                <label for="numero">Número</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.numero.$error.maxlength">Não é permitido mais que 10 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="bairro" type="text" name="bairro" class="validate" placeholder="Bairro" ng-model="controller.profissional.bairro" ng-class="{'error-form':profissionalForm.bairro.$error.maxlength}"  ng-maxlength="60">
                                                <label for="bairro">Bairro</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.bairro.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cidade" type="text" name="cidade" class="validate" placeholder="Cidade" ng-model="controller.profissional.cidade" ng-class="{'error-form':profissionalForm.cidade.$error.maxlength}"  ng-maxlength="60">
                                                <label for="cidade">Cidade</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.cidade.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="uf" type="text" name="uf" class="validate" placeholder="UF" ng-model="controller.profissional.uf" ng-class="{'error-form':profissionalForm.uf.$error.maxlength}"  ng-maxlength="2">
                                                <label for="uf">UF</label>
                                                <span class="error" ng-class="error" ng-show="profissionalForm.uf.$error.maxlength">Não é permitido mais que 2 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cep" type="text" name="cep" class="validate cep" placeholder="CEP" ng-model="controller.profissional.cep">
                                                <label for="cep">CEP</label>
                                            </div> 
                                        </div>
                                    </div>
                                    <div class="botoes">
                                        <button type="button" ng-click="controller.editarProfissional()" ng-disabled="profissionalForm.$invalid" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Salvar</button>                                
                                        <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/instituicao/profissionais"/>">Cancelar</a>
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
