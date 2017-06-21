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
    <body class="grey lighten-2" ng-controller="InstituicaoController as controller" ng-init="controller.init()">
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
                            Cadastrar paciente                            
                        </h5>                        
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="/instituicao/pacientes"/>" class="breadcrumb link-anterior">Pacientes</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Novo</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12" id="pacienteForm" name="pacienteForm">                                    
                                    <div class="form1">  
                                        <h5>
                                            INFORMAÇÕES PESSOAIS
                                        </h5>
                                        <div class="form divider"></div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nome" type="text" name="nome" class="validate" ng-model="controller.paciente.nome" ng-class="{'error-form':pacienteForm.nome.$error.maxlength}"  ng-maxlength="60">
                                                <label for="nome">Nome do paciente (Obrigatório)</label>
                                                <span class="error" ng-class="error" ng-show="pacienteForm.nome.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nascimento" type="date" name="nascimento" class="datepicker" ng-model="controller.paciente.nascimento">
                                                <label for="nascimento">Data nascimento (Obrigatória)</label>
                                            </div> 
                                        </div> 
                                        <div class="row">
                                            <div class="input-field col s12 l12 m12">
                                                <textarea id="prontuario" name="prontuario" class="materialize-textarea" maxlength="200" data-length="200" ng-model="controller.paciente.prontuario" ng-class="{'error-form':pacienteForm.prontuario.$error.maxlength}"  ng-maxlength="200"></textarea>
                                                <label for="prontuario">Prontuário</label>
                                                <span ng-class="error" ng-show="pacienteForm.prontuario.$error.maxlength">Não é permitido mais que 200 caracteres!</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nomePai" type="text" name="nomePai" class="validate" ng-model="controller.paciente.nomePai"  ng-class="{'error-form':pacienteForm.nomePai.$error.maxlength}"  ng-maxlength="60">
                                                <label for="nomePai">Nome do pai</label>
                                                <span ng-class="error" ng-show="pacienteForm.nomePai.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l6 m6">                                            
                                                <input id="nomeMae" type="text" name="nomeMae" class="validate" ng-model="controller.paciente.nomeMae" ng-class="{'error-form':pacienteForm.nomeMae.$error.maxlength}"  ng-maxlength="60">
                                                <label for="nomeMae">Nome da mãe</label>
                                                <span ng-class="error" ng-show="pacienteForm.nomeMae.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div>                                     
                                        <div class="row">
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cpf" type="text" name="cpf" class="validate cpf" ng-model="controller.paciente.cpf">
                                                <label for="cpf">CPF</label>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="rg" type="text" name="rg" class="validate" ng-model="controller.paciente.rg">
                                                <label for="rg">RG</label>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="telefone" type="text" name="telefone" class="validate phone_with_ddd" ng-model="controller.paciente.telefone">
                                                <label for="telefone">Telefone</label>
                                            </div> 
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12 l12 m12">
                                                <textarea id="obs" name="obs" class="materialize-textarea" maxlength="200" data-length="200" ng-model="controller.paciente.obs" ng-class="{'error-form':pacienteForm.obs.$error.maxlength}"  ng-maxlength="200"></textarea>
                                                <label for="obs">Observações</label>
                                                <span ng-class="error" ng-show="pacienteForm.obs.$error.maxlength">Não é permitido mais que 200 caracteres!</span>
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
                                                <input id="endereco" type="text" name="endereco" class="validate" ng-model="controller.paciente.endereco" ng-class="{'error-form':pacienteForm.endereco.$error.maxlength}"  ng-maxlength="60">
                                                <label for="endereco">Rua</label>
                                                <span ng-class="error" ng-show="pacienteForm.endereco.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="numero" type="text" name="numero" class="validate" ng-model="controller.paciente.numero" ng-class="{'error-form':pacienteForm.numero.$error.maxlength}"  ng-maxlength="10">
                                                <label for="numero">Número</label>
                                                <span ng-class="error" ng-show="pacienteForm.numero.$error.maxlength">Não é permitido mais que 10 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="bairro" type="text" name="bairro" class="validate" ng-model="controller.paciente.bairro" ng-class="{'error-form':pacienteForm.bairro.$error.maxlength}"  ng-maxlength="60">
                                                <label for="bairro">Bairro</label>
                                                <span ng-class="error" ng-show="pacienteForm.bairro.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                        </div>
                                        <div class="row">
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cidade" type="text" name="cidade" class="validate" ng-model="controller.paciente.cidade" ng-class="{'error-form':pacienteForm.cidade.$error.maxlength}"  ng-maxlength="60">
                                                <label for="cidade">Cidade</label>
                                                <span ng-class="error" ng-show="pacienteForm.cidade.$error.maxlength">Não é permitido mais que 60 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="uf" type="text" name="uf" class="validate" ng-model="controller.paciente.uf" ng-class="{'error-form':pacienteForm.uf.$error.maxlength}"  ng-maxlength="2">
                                                <label for="uf">UF</label>
                                                <span ng-class="error" ng-show="pacienteForm.uf.$error.maxlength">Não é permitido mais que 2 caracteres!</span>
                                            </div> 
                                            <div class="input-field inline col s12 l4 m4">                                            
                                                <input id="cep" type="text" name="cep" class="validate cep" ng-model="controller.paciente.cep">
                                                <label for="cep">CEP</label>
                                            </div> 
                                        </div>
                                    </div>
                                    <div class="botoes">
                                        <button type="button" ng-click="controller.salvarPaciente()" ng-disabled="pacienteForm.$invalid" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Cadastrar</button>                                
                                        <a class="buttons btn waves-effect waves-light grey darken-3 right col s12 l3 m3" href="<c:url value="/instituicao/pacientes"/>">Cancelar</a>
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
