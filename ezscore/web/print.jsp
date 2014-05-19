<html>
<head>
<link rel="stylesheet" href="css/south-street/jquery-ui-1.10.4.custom.min.css" />
<link rel="stylesheet" href="css/multiselect.css"></link>
<link rel="stylesheet" href="css/ezscore.css"></link> 
<script src="js/spin.min.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.4.custom.min.js"></script>
<script src="js/jquery-multiselect.min.js"></script>
<script src="js/ezscore.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	new EZScoreApp(<%=request.getParameter("meetID")%>, '<%=request.getParameter("page")%>');
});
</script>
</head>
<body>
<div id="printRoot"></div>
<div id="loadingMessage"></div>
<script type="text/javascript">
var opts = {
  lines: 11, // The number of lines to draw
  length: 20, // The length of each line
  width: 10, // The line thickness
  radius: 30, // The radius of the inner circle
  corners: 1, // Corner roundness (0..1)
  rotate: 0, // The rotation offset
  direction: 1, // 1: clockwise, -1: counterclockwise
  color: '#000', // #rgb or #rrggbb or array of colors
  speed: 1, // Rounds per second
  trail: 60, // Afterglow percentage
  shadow: false, // Whether to render a shadow
  hwaccel: false, // Whether to use hardware acceleration
  className: 'spinner', // The CSS class to assign to the spinner
  zIndex: 2e9, // The z-index (defaults to 2000000000)
  top: 'auto', // Top position relative to parent in px
  left: 'auto' // Left position relative to parent in px
};
var spinner = new Spinner(opts).spin(document.getElementById('loadingMessage'));
</script>
</body>
</html>
