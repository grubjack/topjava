<%@page contentType="text/html" pageEncoding="UTF-8" %>

<li>
    <a onclick="show('en')"><img src="resources/images/en.png" alt="English"></a>
</li>
<li>
    <a onclick="show('ru')"><img src="resources/images/ru.png" alt="Русский"></a>
</li>

<script type="text/javascript">
    function show(lang) {
        window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
    }
</script>