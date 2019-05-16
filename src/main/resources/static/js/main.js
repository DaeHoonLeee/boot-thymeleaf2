$(document).ready(function () {

    $("#regform").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
    
    $("#delform").submit(function (event) {
        event.preventDefault();
        delete_ajax_submit();
    });

});

function fire_ajax_submit() {

    //var data = $("#regform").serializeArray();
    
	var data = {
			"name" : $('#name').val(),
			"company" : $('#company').val()
	};
    console.log(data);
    var json = JSON.stringify(data);
    console.log(json);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/users",
        data: json,
        dataType: 'text', // json -> text
        cache: false,
        timeout: 600000,
        success: function (data) {
        	console.log("SUCCESS : ", data);
        	window.location.href = "/users";
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
    
    
    function delete_ajax_submit() {

        //var data = $("#regform").serializeArray();
        
    	var data = {
    			"name" : $('#id').val(),
    			"company" : $('#company').val()
    	};
    	var url_id = "/users/" + $('#id').val();
        console.log(url_id);
        $.ajax({
            type: "DELETE",
            url: url_id,
            data: json,
            dataType: 'text', // json -> text
            success: function (result) {
            	console.log("SUCCESS : ", result);
            }, 
            error: function (e) {  
                console.log("ERROR : ", e);
            }
        });    
        
        function update_ajax_submit() {

            //var data = $("#regform").serializeArray();
            
        	var data = {
        			"name" : $('#id').val(),
        			"company" : $('#company').val()
        	};
        	var user_id = "/users/" + $('#id').val();
        	var json = JSON.stringify(data);
            console.log(json);
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "/users",
                data: json,
                dataType: 'text', // json -> text
                cache:false,
                timeout:600000,
                success: function (data) {
                	console.log("SUCCESS : ", data);
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });    
}
