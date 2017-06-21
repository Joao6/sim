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
                            Instituições cadastradas
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/master/home"/>" class="breadcrumb link-ativo">Home</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">
                        <div class="row">
                            <div class="row">
                                <form class="col s12">
                                    <div class="row">                                        
                                        <div class="input-field col inline s12 l6 m6">
                                            <i class="material-icons prefix">search</i>
                                            <input id="nomeInstituicao" type="text" name="nomeInstituicao" ng-change="controller.readInstituicao()" class="validate" ng-model="controller.nomeInstituicao">
                                            <label for="nomeInstituicao">Filtrar por nome</label>
                                        </div>
                                        <div class="input-field col s12 l6 m6">
                                            <select name="configuracao" id="configuracao" ng-change="controller.readInstituicao()" ng-model="controller.configuracaoVersao">
                                                <option class="color" value="" disabled selected>Selecione uma versão</option>
                                                <option class="color" value="QUALQUER">Qualquer</option>
                                                <option class="color" value="FREE">FREE</option>
                                                <option class="color" value="PRO">PRO</option>
                                            </select>
                                            <label>Filtrar por versão</label>
                                        </div> 
                                    </div>                                
                                </form>
                            </div>
                            <div ng-show="controller.instituicaoListEmpty()"><strong>Não foram encontrados resultados!</strong></div>
                            <div ng-hide="controller.instituicaoListEmpty()" class="row">
                                <table class="table bordered striped">
                                    <thead class="thead">
                                        <tr class="tr">
                                            <th class="th">Instituição</th>
                                            <th class="th">E-mail</th>
                                            <th class="th">Telefone</th>
                                            <th class="th">Qtd Pacientes</th>
                                            <th class="th">Versão</th>                                            
                                            <th class="th"></th>                                            
                                        </tr>
                                    </thead>
                                    <tbody class="tbody">
                                        <tr ng-repeat="instituicao in controller.instituicaoList">
                                            <td data-label="Instituição" class="td">{{instituicao.nome}}</td>
                                            <td data-label="E-mail" class="td">{{instituicao.email}}</td>
                                            <td data-label="Telefone" class="td">{{instituicao.telefone}}</td>
                                            <td data-label="Qtd Pacientes" class="td">{{instituicao.qtdPacientes}}</td>
                                            <td data-label="Versão" class="td">{{instituicao.confVersao}}</td>    
                                            <td class="td">
                                                <a class="btn-floating waves-effect waves-light" href="<c:url value="/master/instituicao/{{instituicao.id}}/editar"/>">
                                                    <i class="material-icons blue darken-4">mode_edit</i>
                                                </a>
                                            </td>
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
                            <strong ng-hide="controller.instituicaoListEmpty()">Monstrando {{controller.instituicaoListLenght()}} atendimentos de um total de {{controller.count}}.</strong>                            
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
        <script>
                                        $(function () {
                                            $(".button-collapse").sideNav();
                                        });
        </script>
    </body>
</html>
