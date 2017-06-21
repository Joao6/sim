<%-- 
    Document   : base-navbar-master
    Created on : 15/03/2017, 09:15:45
    Author     : Joao Pedro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/resources/templates/base-script.jsp"/> 
<script type="text/javascript" src="<c:url value="/resources/js/verifica-mensagem.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/mensagem.js"/>"></script>
<nav>
    <div class="nav-wrapper">
        <a href="<c:url value="/profissional/home"/>" class="brand-logo">
            <img class="responsive-img logo" width="170" height="150" src="<c:url value="/resources/img/logo-sim.png"/>" />
            <!--SIM-->            
        </a>
        <a href="#" data-activates="menu-mobile" class="button-collapse">
            <i class="material-icons">menu</i>
        </a>
        <ul id="dropdown1" class="dropdown-content">
            <li><a href="<c:url value="/profissional/meus-dados"/>">Meus dados</a></li>           
            <li><a href="<c:url value="/profissional/alterar-senha"/>">Alterar senha</a></li>           
            <li><a href="<c:url value="/usuario/logout"/>">Sair</a></li>           
        </ul>
        <ul class="right hide-on-med-and-down">            
            <li><a href="<c:url value="/profissional/home"/>">Home</a></li>            
            <li><a href="<c:url value="/profissional/meus-atendimentos"/>">Meus atendimentos</a></li>
            <li><a href="<c:url value="/profissional/mensagens"/>">Mensagens <span id="menuConversa" class="alert menuConversa"></span></a></li>
            <li><a class="dropdown-button" href="#!" data-activates="dropdown1">${usuarioLogado.nome}<i class="material-icons right">arrow_drop_down</i></a></li>
        </ul>
        <ul class="side-nav" id="menu-mobile">
            <li><a href="#!"><i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>${usuarioLogado.nome}</a></li>
            <li><a href="<c:url value="/profissional/home"/>"><i class="fa fa-home fa-2x" aria-hidden="true"></i>Home</a></li>
            <li><a href="<c:url value="/profissional/meus-atendimentos"/>"><i class="fa fa-sticky-note fa-2x" aria-hidden="true"></i>Meus atendimentos</a></li>
            <li><a href="<c:url value="/profissional/mensagens"/>"><i class="fa fa-envelope fa-2x" aria-hidden="true"></i>Mensagens <span id="menuConversa" class="alert menuConversa"></span></a></li>
            <li><a href="<c:url value="/profissional/meus-dados"/>"><i class="fa fa-database fa-2x" aria-hidden="true"></i>Meus dados</a></li>
            <li><a href="<c:url value="/profissional/alterar-senha"/>"><i class="fa fa-key fa-2x" aria-hidden="true"></i>Alterar senha</a></li>
            <li><a href="<c:url value="/usuario/logout"/>"><i class="fa fa-sign-out fa-2x" aria-hidden="true"></i>Sair</a></li>
        </ul>
    </div>
</nav>