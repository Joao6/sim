<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="SIM_APP">
    <head>
        <title>SIM</title>
        <link rel="shortcut icon" href="<c:url value="/resources/img/logo-icon.png"/>" type="image/x-icon">
        <!--Import Google Icon Font-->
        <link href="<c:url value="/resources/css/icon.css"/>" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/form-login.css"/>"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>

    <body class="blue-grey bg-img" ng-controller="RecuperarSenhaController as controller">

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
                <h5>Por favor aguarde...</h5>
            </div>            
        </div>

        <div class="section"></div>
        <main>
            <center>
                <div class="container">
                    <div class="z-depth-1 white row panel-login" style="">
                        <img class="responsive-img" width="250" src="<c:url value="/resources/img/logo-login.png"/>" />
                        <!--<div class="section"></div>-->

                        <h5 class="blue-text darken-4">Recuperar senha</h5>                       
                        <input id="idUsuario" value="${idUsuario}" type="hidden">
                        <input id="idRecuperacao" value="${idRecuperacao}" type="hidden">
                        <form id="formRecuperar" name="formRecuperar" class="col s12" ng-submit="controller.alterarSenha()">
                            <div class='row'>
                                <div class='col s12'>
                                </div>
                            </div>

                            <div class='row'>
                                <div class='input-field col s12'>
                                    <input class='validate' type='password' name='senha' id='senha' ng-model="controller.senha" required="" maxlength="20"/>
                                    <label for='senha'>Insira a nova senha</label>
                                </div>
                            </div>                            
                            <div class='row'>
                                <div class='input-field col s12'>
                                    <input class='validate' type='password' name='senhaC' id='senhaC' ng-model="controller.senhaC" required="" maxlength="20"/>
                                    <label for='senhaC'>Confirme a nova senha</label>
                                </div>
                            </div>  
                            <br />
                            <center>
                                <div class='row'>
                                    <button type='submit' name='btn_login' ng-disabled="formRecuperar.$invalid" class='col s12 btn btn-large waves-effect blue darken-3'>Enviar</button>
                                </div>
                            </center>
                        </form>
                    </div>
                </div>                
            </center>

            <div class="section"></div>
            <div class="section"></div>
        </main>

        <jsp:include page="/resources/templates/base-script.jsp"/>  
        <script type="text/javascript" src="<c:url value="/resources/js/angular.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/angular/usuario/recuperar-senha-controller.js"/>"></script>
    </body>
</html>
