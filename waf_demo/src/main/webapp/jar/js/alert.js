function alertMsg(text) {
    var bgHtml ="<div class='msg-bg'></div>";
    var msgAlertHtml = "<div class='msg-alertWrap'>" +
                            "<div class='msg-alert'>" +
                                "<div class='msg-header'><button class='msg-dismiss'><span>&times;</span></button><h4 class='msg-title'>温馨提示</h4></div>" +
                                "<div class='msg-body'>" +
                                    "<div class='msg-content'></div>" +
                                "</div>" +
                                "<div class='msg-footer'>" +
                                    "<button type='button' class='msg-btnOk'>确定</button>" +
                                "</div>" +
                            "</div>"+
                        "</div>";
    if ($(".msg-bg").length == 0) {
        $("body").append(bgHtml + msgAlertHtml);
    } else {
        $("body").append(msgAlertHtml);
    }

    $(".msg-alertWrap:last-child .msg-content").html(text);

    $(".msg-btnOk,.msg-dismiss").on("click", function () {
        $(this).parents(".msg-alertWrap").remove();
        if ($(".msg-alertWrap").length == 0) {
            $(".msg-bg").remove();
        }
    })
}