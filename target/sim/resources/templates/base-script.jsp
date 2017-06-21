<%-- 
    Document   : base-script
    Created on : 15/03/2017, 09:07:43
    Author     : Joao Pedro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/base-script.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/md5.min.js"/>"></script>

<script>
    $(".dropdown-button").dropdown({
        inDuration: 300,
        outDuration: 225,
        constrainWidth: false, // Does not change width of dropdown to that of the activator
        hover: false, // Activate on hover
        gutter: 0, // Spacing from edge
        belowOrigin: true, // Displays dropdown below the button
        alignment: 'left', // Displays dropdown with edge aligned to the left of button
        stopPropagation: false // Stops event propagation
    });

    $('.datepicker').pickadate({
        format: 'dd/mm/yyyy',
        language: 'pt-BR',
        weekdaysFull: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
        weekdaysShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"],
        weekdaysLetter: ["D", "S", "T", "Q", "Q", "S", "S"],
        monthsFull: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        monthsShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        today: "Hoje",
        clear: "Limpar",
        close: "Fechar",
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });
</script>