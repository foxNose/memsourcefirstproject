<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="page.setup.title" /></title>
		<style type="text/css" media="screen">
			
		</style>
	</head>
	<body>
		<div id="page-body" class="setup" role="main">
			<g:if test="${flash.message}"> 
                <div class='alert alert-info alert-dismissible'>
                    <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
                    ${flash.message}
                </div>
            </g:if>
            <g:if test="${flash.errors}"> 
                <div class='alert alert-danger alert-dismissible'>
                    <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
                    <ul>
                        <g:each in="${flash.errors}" var="msg" >
                            <li>${msg}</li>
                        </g:each>
                    </ul>
                </div>
            </g:if>

            <g:form action="save" method="post">
                <label for="username"><g:message code="form.input.label.username" /></label>
                <g:if test="${account.username}">
                    <g:field type="text" name="username_" required min="1" value="${account.username}" disabled="true" />
                    <g:field type="hidden" name="username" required min="1" value="${account.username}" />
                </g:if>
                <g:else>
                    <g:field type="text" name="username" required min="1" value="${account.username}" />
                </g:else>
                <label for="password"><g:message code="form.input.label.password" /></label>
                <g:if test="${account.password}">
                    <g:field type="password" name="password" placeholder="${g.message(code:'form.input.placeholder.notchanged')}" value="${account.password}" />
                </g:if>
                <g:else>
                    <g:field type="password" name="password" required min="1" value="" />
                </g:else>
                <g:if test="${account.username}">
                    <g:submitButton name="save-btn" value="${g.message(code:'page.setup.btn.change.password')}" />
                </g:if>
                <g:else>
                    <g:submitButton name="save-btn" value="${g.message(code:'page.setup.btn.login')}" />
                </g:else>
            </g:form>
		</div>
	</body>
</html>