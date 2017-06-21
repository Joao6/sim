<%-- 
    Document   : limit-pacientes
    Created on : 02/05/2017, 10:31:47
    Author     : Joao Pedro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SIM</title>
        <!-- Incluindo a página que contém os arquivos css comuns para as páginas-->
        <jsp:include page="/resources/templates/base-style.jsp"/>
        <link href="<c:url value="/resources/css/instituicao/app.css"/>" rel="stylesheet">
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body class="grey lighten-2">
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
                            LIMITE DESTA VERSÃO ATINGIDO                            
                        </h5>
                        <div class="nav-wrapper right">
                            <div class="col s12 ">                                
                                <a href="<c:url value="/instituicao/home"/>" class="breadcrumb link-anterior">Home</a>                                
                                <a href="<c:url value="#!"/>" class="breadcrumb link-ativo">Limite</a>                                
                            </div>
                        </div>
                        <div class="form divider"></div>
                    </div>
                    <div class="main-content">                       
                        <div class="row">                                 
                            <p>Verificamos em nossa sistema, que você atingiu o limite para cadastro de pacientes
                             da sua configuração de versão.</p>
                            <p>Caso tenha interesse em mudar a sua versão, para cadastros ilimitados, por favor, entre 
                            em contato conosco.</p>
                            <p>Maiores informações para contato encontram-se no rodapé desta página.</p>
                            <img class="img-responsive" src="<c:url value="/resources/img/LIMITE_CADASTRO.png"/>" alt="Erro">
                        </div>                                           
                    </div>
                </div>
            </div>
        </div>      
        <jsp:include page="/resources/templates/footer.jsp"/>   
        <!--Incluindo a jsp que contém os arquivos JS comuns para as páginas-->
         <jsp:include page="/resources/templates/base-script.jsp"/>
        <script>
            $(function () {
                $(".button-collapse").sideNav();
            });
        </script>
    </body>
</html>
