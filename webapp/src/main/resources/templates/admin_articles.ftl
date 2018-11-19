<html>
<h2>Hello!</h2>

List of articles:

<table id="articles" class="articles">
    <tr>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <#list articles as article>
        <tr>
            <td class="name">${article.name?capitalize}</td>
            <td>${article.price}</td>
            <td><div location="button-${article.id}" /></td>
        </tr>
    </#list>
</table>
</html>