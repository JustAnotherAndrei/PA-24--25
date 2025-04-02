<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Raport Repozitoriu Imagini</title>
</head>
<body>
<h1>Raport Repozitoriu Imagini</h1>
<ul>
    <#list images as image>
        <li>${image.name} - ${image.date?string("yyyy-MM-dd")} - Taguri: ${image.tags} - Locație: ${image.location}</li>
    </#list>
</ul>
</body>
</html>
