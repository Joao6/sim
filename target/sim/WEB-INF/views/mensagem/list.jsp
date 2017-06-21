<%-- 
    Document   : list
    Created on : 02/06/2017, 08:43:40
    Author     : Joao Pedro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <title>SIM</title>
        <!-- Incluindo a página que contém os arquivos css comuns para as páginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/profissional/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/table/responsive-table.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2" ng-controller="MensagemController as controller" ng-init="controller.init()">
        
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

        <!-- MODAL ENVIAR MENSAGEM -->
        <div class="modal" id="modal-nova-mensagem">
            <div class="modal-content">
                <h3 class="valign-wrapper" style="font-size: 25pt"><i class="material-icons valign" style=" font-size: 25pt !important">email</i> 
                    <span class="valign">Enviar mensagem</span>
                </h3>
                <form name="formMensagem" id="formMensagem">
                    <div class="row">
                        <div class="col l12 s12 m12">
                            <select name="profissionais" ng-model="controller.mensagem.destinatario" required="" class="browser-default">
                                <option value="" disabled="" selected="">Selecione o destinatário</option>
                                <option ng-repeat="profissional in controller.profissionalList" value="{{profissional.id}}">
                                    {{profissional.nome}}
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="row">                        
                        <div class="input-field col s12 l12 m12">
                            <textarea id="conteudo" name="conteudo" class="materialize-textarea" maxlength="200" data-length="200" 
                                      ng-model="controller.mensagem.conteudo" ng-class="{'error-form':formMensagem.conteudo.$error.maxlength}"
                                      ng-maxlength="200" placeholder="Conteúdo da mensagem" required=""></textarea>
                            <label for="conteudo">Conteúdo</label>
                            <span ng-class="error" ng-show="formMensagem.conteudo.$error.maxlength">Não é permitido mais que 200 caracteres!</span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer card-action">
                <button type="button" ng-click="controller.enviarMensagem()" ng-disabled="formMensagem.$invalid" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Enviar</button>                                
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat grey white-text darken-2" style="margin-right: 5px;">Cancelar</a>
            </div>
        </div>

        <!-- MODAL RESPONDER MENSAGEM -->
        <div class="modal" id="modal-responder">
            <div class="modal-content">
                <h3 class="valign-wrapper" style="font-size: 25pt"><i class="material-icons valign" style=" font-size: 25pt !important">email</i> 
                    <span class="valign">Responder mensagem de {{controller.mensagem.destinatario.nome}}</span>
                </h3>
                <form name="formReply" id="formReply">                  
                    <div class="row">                        
                        <div class="input-field col s12 l12 m12">
                            <textarea id="conteudo" name="conteudo" class="materialize-textarea" maxlength="200" data-length="200" 
                                      ng-model="controller.mensagem.conteudo" ng-class="{'error-form':formReply.conteudo.$error.maxlength}"
                                      ng-maxlength="200" placeholder="Conteúdo da mensagem" required=""></textarea>
                            <label for="conteudo">Conteúdo</label>
                            <span ng-class="error" ng-show="formReply.conteudo.$error.maxlength">Não é permitido mais que 200 caracteres!</span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer card-action">
                <button type="button" ng-click="controller.responderMensagem()" ng-disabled="formReply.$invalid" class="buttons btn waves-effect waves-light col s12 l3 m3 blue darken-4 right">Enviar</button>                                
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat grey white-text darken-2" style="margin-right: 5px;">Cancelar</a>
            </div>
        </div>

        <!-- Incluindo a JSP que contém a navbar do usuário Master -->
        <jsp:include page="/resources/templates/base-navbar-profissional.jsp"/> 

        <div class="container">
            <div class="card-panel"> 
                <div class="card-content">
                    <div class="header-page row">
                        <h5>
                            Lista de mensagens                            
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/profissional/home"/>" class="breadcrumb link-ativo">Home</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Mensagens</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>                    
                    <div class="main-content">                        
                        <div class="row">
                            <div class="row">
                                <form class="col s12">
                                    <!--<div class="row">-->                                        
                                    <div class="input-field col inline s12 l6 m6">
                                        <i class="material-icons prefix">search</i>
                                        <input id="nomeProfissional" type="text" name="nomeProfissional" ng-change="controller.readMensagens()" class="validate" ng-model="controller.nomeProfissional">
                                        <label for="nomeProfissional">Pesquisar</label>
                                    </div>  
                                    <a class="btn col s12 m4 l3 waves-effect waves-light right blue darken-4" ng-click="controller.openModal('nova-mensagem')">
                                        Nova Mensagem
                                    </a>
                                </form>

                            </div>
                            <div class="card">
                                <div class="card-tabs">
                                    <ul class="tabs tabs-fixed-width">
                                        <li class="tab"><a class="active" href="#" ng-click="controller.readMensagensRecebidas()">Recebidas</a></li>
                                        <li class="tab"><a href="#" ng-click="controller.readMensagensEnviadas()">Enviadas</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div ng-show="controller.isMensagemListEmpty()"><strong>Nenhuma mensagem aqui!</strong></div>
                            <div ng-hide="controller.isMensagemListEmpty()" class="row">
                                <div id="mensagemCards" ng-repeat="mensagem in controller.mensagemList">
                                    <ul class="collection">                                        
                                        <li class="collection-item avatar">
                                            <i class="material-icons circle green">email</i>
                                            <span class="title" ng-hide="controller.isDestinatarioEmpty(mensagem)"><b>{{mensagem.destinatario.nome}}</b></span>
                                            <span class="title" ng-hide="controller.isRemetenteEmpty(mensagem)"><b>{{mensagem.remetente.nome}}</b></span>
                                            <p>{{mensagem.conteudo}}<br>
                                                <b>{{mensagem.dataEnvio}}</b>
                                            </p>
                                            <a href="#!" class="secondary-content" ng-hide="controller.isRemetenteEmpty(mensagem)" ng-click="controller.openModalResponder('responder', mensagem)">
                                                <i class="fa fa-reply fa-2x color-green"></i>
                                            </a>
                                            <p class="right" ng-show="controller.isDestinatarioEmpty(mensagem)">                                     
                                                <input ng-hide="controller.isMensagemLida(mensagem)" ng-change="controller.mensagemLida(mensagem)" ng-model="mensagem.isLida" type="checkbox" id="check-{{mensagem.id}}" />
                                                <label for="check-{{mensagem.id}}">Lida</label>                                           
                                            </p>                                            
                                        </li>                                     
                                    </ul>
                                </div>
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
                            <strong ng-hide="controller.isMensagemListEmpty()">Monstrando {{controller.mensagemListLenght()}} mensagens de um total de {{controller.count}}.</strong>                            
                        </div>
                    </div>
                </div>
            </div>
        </div>      
        <jsp:include page="/resources/templates/footer.jsp"/>   
        <!--Incluindo a jsp que contém os arquivos JS comuns para as páginas-->
        <jsp:include page="/resources/templates/base-script.jsp"/>  
        <script type="text/javascript" src="<c:url value="/resources/js/angular.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/angular/mensagem/mensagem-controller.js"/>"></script>
        <script>
                                        $(function () {
                                            $(".button-collapse").sideNav();
                                        });
        </script>
    </body>
</html>
