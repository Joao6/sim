<%-- 
    Document   : base-navbar-master
    Created on : 15/03/2017, 09:15:45
    Author     : Joao Pedro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav>
    <div class="nav-wrapper">
        <a href="<c:url value="/instituicao/home"/>" class="brand-logo">
            <img class="responsive-img logo" width="170" height="150" src="<c:url value="/resources/img/logo-sim.png"/>" />
            <!--SIM-->            
        </a>
        <a href="#" data-activates="menu-mobile" class="button-collapse">
            <i class="material-icons">menu</i>
        </a>
        <ul id="dropdown1" class="dropdown-content">
            <li><a href="<c:url value="/instituicao/meus-dados"/>">Meus dados</a></li>           
            <li><a href="<c:url value="/instituicao/alterar-senha"/>">Alterar senha</a></li>           
            <li><a href="<c:url value="/usuario/logout"/>">Sair</a></li>           
        </ul>
        <ul class="right hide-on-med-and-down">            
            <li><a href="<c:url value="/instituicao/home"/>">Home</a></li>            
            <li><a href="<c:url value="/instituicao/pacientes"/>">Pacientes</a></li>
            <li><a href="<c:url value="/instituicao/profissionais"/>">Profissionais</a></li>
            <li><a href="<c:url value="/instituicao/associar/profissional-paciente"/>">Associar</a></li>
            <li><a class="dropdown-button" href="#!" data-activates="dropdown1">${usuarioLogado.nome}<i class="material-icons right">arrow_drop_down</i></a></li>
        </ul>
        <ul class="side-nav" id="menu-mobile">
            <li><a href="#!"><i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>${usuarioLogado.nome}</a></li>
            <li><a href="<c:url value="/instituicao/home"/>"><i class="fa fa-home fa-2x" aria-hidden="true"></i>Home</a></li>
            <li><a href="<c:url value="/instituicao/pacientes"/>"><i class="fa fa-user fa-2x" aria-hidden="true"></i>Pacientes</a></li>
            <li><a href="<c:url value="/instituicao/profissionais"/>"><i class="fa fa-users fa-2x" aria-hidden="true"></i>Profissionais</a></li>
            <li><a href="<c:url value="/instituicao/associar/profissional-paciente"/>"><i class="fa fa-check-square fa-2x" aria-hidden="true"></i>Associar</a></li>
            <li><a href="<c:url value="/instituicao/meus-dados"/>"><i class="fa fa-database fa-2x" aria-hidden="true"></i>Meus dados</a></li>
            <li><a href="<c:url value="/instituicao/alterar-senha"/>"><i class="fa fa-key fa-2x" aria-hidden="true"></i>Alterar senha</a></li>
            <li><a href="<c:url value="/usuario/logout"/>"><i class="fa fa-sign-out fa-2x" aria-hidden="true"></i>Sair</a></li>
        </ul>
    </div>
</nav>