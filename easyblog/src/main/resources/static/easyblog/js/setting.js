window.onload = function () {
    document.querySelector('#change_pic_button').addEventListener('click', function () {
        showdiv("change_pic", true);
    })

    document.querySelector('#close_button').addEventListener('click', function () {
        showdiv("change_pic", false);
    })


}

function submit() {
    prtid("first_name");
    prtid("last_name");
    prtid("username");
    prtid("user_about");
   // prtid("img_file");

}
