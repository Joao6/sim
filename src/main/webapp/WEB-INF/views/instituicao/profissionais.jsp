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
        <!-- MODAL EXCLUIR PROFISSIONAL -->
        <div class="modal" id="modal-excluir">
            <div class="modal-content">
                <h3 class="valign-wrapper" style="font-size: 25pt"><i class="material-icons valign" style=" font-size: 25pt !important">warning</i> 
                    <span class="valign">Alerta de Exclus�o</span>
                </h3>
                <h5>Esta opera��o n�o poder� ser desfeita posteriormente</h5>
                <p>Obs.: os dados deste profissional ser�o exclu�dos de todos os locais. Deseja confirmar esta opera��o?</p>
            </div>
            <div class="modal-footer card-action">
                <a href="#!" ng-click="controller.deletarProfissional(controller.profissional)" class="waves-effect waves-orange white-text btn-flat red darken-3 modal-close">Excluir</a>
                <a href="#!" class="modal-action modal-close waves-effect waves-orange btn-flat grey white-text darken-2" style="margin-right: 5px;">Cancelar</a>
            </div>
        </div>
        <!-- Incluindo a JSP que cont�m a navbar do usu�rio Master -->
        <jsp:include page="/resources/templates/base-navbar-instituicao.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Profissionais                            
                        </h5>
                        <a class="btn-floating btn-add waves-effect waves-light" href="<c:url value="/instituicao/profissionais/novo"/>">
                            <i class="material-icons blue darken-4">add</i>                            
                        </a>
                        <span>Cadastrar profissional</span>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Profissionais</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12">
                                    <div class="row">
                                        <div class="input-field inline col s12 l6 m6">
                                            <i class="material-icons prefix">search</i>
                                            <input id="nomeProfissional" ng-change="controller.getProfissionalList()" type="text" name="nomeProfissional" class="validate" value="${nomeProfissional}" ng-model="controller.nomeProfissional">
                                            <label for="nomeProfissional">Filtrar por nome</label>
                                        </div>                                       
                                    </div>                                
                                </form>
                            </div>
                            <div ng-show="controller.profissionalListEmpty()"><strong>N�o foi encontrado nenhum profissional!</strong></div>
                            <div ng-hide="controller.profissionalListEmpty()" class="row">                                 
                                <table class="table bordered striped">
                                    <thead class="thead">
                                        <tr class="tr">
                                            <th class="th">Nome</th>
                                            <th class="th">Profiss�o</th>
                                            <th class="th">Telefone</th>
                                            <th class="th">E-mail</th>
                                            <th class="th"></th>                                            
                                            <th class="th"></th>                                            
                                        </tr>
                                    </thead>

                                    <tbody class="tbody">
                                        <%--<c:forEach items="${profissionalList}" var="profissional">--%>
                                        <tr ng-repeat="profissional in controller.profissionalList">
                                            <td data-label="Nome" class="td">{{profissional.nome}}</td>
                                            <td data-label="Profiss�o" class="td">{{profissional.profissao}}</td>
                                            <td data-label="Telefone" class="td">{{profissional.telefone}}</td>
                                            <td data-label="Email" class="td">{{profissional.email}}</td>
                                            <td class="td">
                                                <a class="btn-floating waves-effect waves-light" href="<c:url value="/instituicao/profissionais/{{profissional.id}}/editar"/>">
                                                    <i class="material-icons blue darken-4">mode_edit</i>
                                                </a>
                                            </td>
                                            <td class="td">
                                                <a ng-click="controller.openModalProfissional('excluir', profissional)" class="btn-floating waves-effect waves-light" href="<c:url value="#"/>">
                                                    <i class="material-icons red darken-2">delete</i>
                                                </a>
                                            </td> 
                                        </tr>
                                        <%--</c:forEach>--%>                                            
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
                            <strong ng-hide="controller.profissionalListEmpty()">Monstrando {{controller.profissionalListLenght()}} profissionais de um total de {{controller.count}}.</strong>
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
        <script>
                                        $(function () {
                                            $(".button-collapse").sideNav();
                                        });
        </script>
    </body>
</html>
