// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.2.0.min
//= require bootstrap
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $(document).ajaxStart(function() {
            $('#spinner').fadeIn();
        }).ajaxStop(function() {
            $('#spinner').fadeOut();
        });
    })(jQuery);
}

$(document).ready(function () {

    $.ajax({
        url: "/resource/rating",
        method: 'post',
        data: {id: $("#id").val()},
        success: function (result) {
            console.log(result)
        }
    });
})

$(document).ready(function(){
    $('#sendInvite').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var tid = parseInt(button.data('topicid')) // Extract info from data-* attributes

        var modal = $(this)
        if(tid){
            modal.find('.modal-body select#topic').val(tid)
        }
    });



})

$(document).ready(function() {
    $('#topicEditModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var tid = parseInt(button.data('topicid')) // Extract info from data-* attributes

        var modal = $(this)
        if (tid) {
            modal.find('.modal-body form#topicnameEditForm input[name="id"]').val(tid)
        }
    });
})




