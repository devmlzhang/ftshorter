$(function () {
  footer();
});


function footer() {
  var $unstyledUl = $('<ul class="unstyled"></ul>');

  var $firstUlFooterLinks = $('<ul class="footer-links"></ul>');
  $firstUlFooterLinks
    .append($('<li>无意在<a target="_blank" href="https://github.com/nutzam/nutz">Nutz</a>看见的一个代码，用spring boot重写了文件文本短地址服务</li>'))
    .append($('<li>Thanks <a target="_blank" href="http://twitter.github.com/bootstrap/index.html">Bootstrap</a></li>'));

  $unstyledUl
    .append($('<li class="footer-links">Coded by <a target="_blank" href="#">Spirng boot</a> &copy; 2018</li>'))
    .append($('<li></li>').append($firstUlFooterLinks))
  $('<div class="container"></div>').append($unstyledUl).appendTo($('.footer'));

}

function qrcodeToggle(id, showStr, hideStr) {
  $("#" + id + "-qrcode-str").toggle(
    function () {
      $("#" + id + "-qrcode").show();
      $("#" + id + "-qrcode-str").html(hideStr);
    },
    function () {
      $("#" + id + "-qrcode").hide();
      $("#" + id + "-qrcode-str").html(showStr);
    }
  );
}




