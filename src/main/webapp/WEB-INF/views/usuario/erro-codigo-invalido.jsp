<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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

    <body class="blue-grey bg-img">
        <div class="section"></div>
        <main>
            <center>
                <div class="container">
                    <div class="z-depth-1 white row panel-login" style="">
                        <img class="responsive-img" width="250" src="<c:url value="/resources/img/logo-login.png"/>" />
                        <!--<div class="section"></div>-->

                        <h5 class="blue-text darken-4">Ops...</h5>
                        <!--<div class="section"></div>-->                        
                        <div class='row'>
                            <div class='col s12'>
                            </div>
                        </div>
                        <p><b>O código de recuperação de senha que você está tentando utilizar expirou ou é inválido.</b></p>
                        <br />
                        <center>
                            <div class='row'>
                                <a href="<c:url value="/login"/>" class="col s12 btn btn-large waves-effect blue darken-3">Voltar</a>
                            </div>
                        </center>
                    </div>
                </div>                
            </center>

            <div class="section"></div>
            <div class="section"></div>
        </main>

        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js"/>"></script>
    </body>
</html>
