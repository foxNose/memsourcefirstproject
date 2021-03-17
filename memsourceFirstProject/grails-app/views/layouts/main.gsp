<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Memsource viewer | <g:layoutTitle default=""/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="application.css"/>
		<asset:javascript src="application.js" />
		<g:layoutHead/>
	</head>
	<body>
		<div id="header">
			<div id="header-left">
				<a href="${g.createLink(controller: "setup", action: "index")}"><span class="helper"></span><asset:image src="logo.svg" alt="Memsource"/></a>
			</div>
			<div id="header-right">	
				<g:if test="${session.userId != null}">
					<g:link controller="projects" action="index"><g:message code="page.projects.title" /></g:link>
				</g:if>
				<g:link controller="setup" action="index"><g:message code="page.setup.title" /></g:link>
				<g:if test="${session.userId != null}">
					<g:link controller="setup" action="logout"><g:message code="page.logout.title" /></g:link>
				</g:if>
			</div>
		</div>
		<g:layoutBody/>
		<div class="footer">
			<span class="copy">&copy; 2021 Dominik Toušek</span>
			<span class="poweredby">Powered by <a href="https://grails.github.io/grails2-doc/2.5.0/">Grails 2.5.x</a></span>
		</div>
	</body>
</html>
