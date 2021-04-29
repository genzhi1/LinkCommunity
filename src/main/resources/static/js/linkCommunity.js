function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
            dataType: "json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentContent,
            "type": 0
        }),
        success: function (response) {
            if (response.code == 1024) {
                console.log(response);
                $("#comment_section").hide();
            } else {
                if (response.code == 1021) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        debugger;
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.233c470b3aa1382c&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
        }

        }
         );


}

