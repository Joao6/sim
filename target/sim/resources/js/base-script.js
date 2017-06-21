$(function () {
    $(".button-collapse").sideNav();
});
$(document).ready(function () {
    $('select').material_select();
});
$(document).ready(function () {
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $('.modal').modal();
});
var configModal = {
    dismissible: false, // Modal can be dismissed by clicking outside of the modal
    opacity: .5, // Opacity of modal background
    in_duration: 1, // Transition in duration
    out_duration: 1, // Transition out duration
    ready: function () {
        return;
    }, // Callback for Modal open
    complete: function () {

    } // Ca
};

$('.dropdown-button').dropdown({
    inDuration: 300,
    outDuration: 225,
    constrainWidth: false, // Does not change width of dropdown to that of the activator
    hover: true, // Activate on hover
    gutter: 0, // Spacing from edge
    belowOrigin: true, // Displays dropdown below the button
    alignment: 'left', // Displays dropdown with edge aligned to the left of button
    stopPropagation: false // Stops event propagation
}
);