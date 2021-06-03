$(function(){
    $("#wait").click(function(event) {
        event.preventDefault();
            $.ajax({
                type: "POST",
                url: "/api/v1/start?wait=true",
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (jsonData) {
                    console.log(JSON.stringify(jsonData));
                    window.location.href = "/api/v1/message?processId=" + jsonData.id;
                },
                error: function() {
                    alert("Error while creating process");
            },
        });
    });
    $("#noWait").click(function(event) {
        event.preventDefault();
            $.ajax({
                type: "POST",
                url: "/api/v1/start?wait=false",
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (jsonData) {
                    console.log(JSON.stringify(jsonData));
                    window.location.href = "/api/v1/info?processId=" + jsonData.id;
                },
                error: function() {
                    alert("Error while creating process");
            },
        });
    });
    $("#submit").click(function(event) {
        event.preventDefault();
            var processId = $("#processId").val();
            var message = $("#message").val();
            $.ajax({
                type: "POST",
                url: "/api/v1/message",
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify({
                    processId: processId,
                    message: message
                }),
                success: function (jsonData) {
                    console.log(JSON.stringify(jsonData));
                    window.location.href = "/api/v1/info?processId=" + processId;
                },
                error: function(data) {
                    alert(data.responseJSON.message);
            },
        });
    });
    $("#restart").click(function(event) {
        event.preventDefault();
        window.location.href = "/api/v1/start";
    });
});