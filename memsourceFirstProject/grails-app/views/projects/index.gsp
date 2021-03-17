<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="page.projects.title" /></title>
	</head>
	<body>
        <div id="info-msg">
        </div>
		<div id="page-body" class="projects" role="main">
            <div class="projects-container">
                <div class="project-header">
                    <span class="project-name-heading"><g:message code="page.projects.project.name" /></span>
                    <span class="project-status-heading"><g:message code="page.projects.project.status" /></span>
                    <span class="project-source-lang-heading"><g:message code="page.projects.project.source.language" /></span>
                    <span class="project-target-langs-heading"><g:message code="page.projects.project.target.languages" /></span>
                </div>
                <div class="projects">
                    <!--
                    <div class="project-item">
                        <span class="project-name">Project name</span>
                        <span class="project-status">NEW</span>
                        <span class="project-source-lang">CS</span>
                        <span class="project-target-langs">
                            <span class="project-target-lang">EN</span>
                        </span>
                    </div>
                    -->
                </div>
                <div class="projects-paginator">
                    <!--
                    <span class="page-first-btn"><<</span>
                    <span class="page-prev-btn"><</span>
                    <span class="page-btn">1</span>
                    <span class="page-btn">2</span>
                    <span class="page-btn">3</span>
                    <span class="page-next-btn">></span>
                    <span class="page-last-btn">>></span>
                    -->
                </div>
            </div>  
		</div>
        <g:javascript>
            function showMessage(message) {
                $("#info-msg")
                    .empty()
                    .append(
                        "<div class='alert alert-danger alert-dismissible'>"
                            +"<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
                            + message
                        +"</div>"
                    );
                $(".alert a.close").on("click",function(event){
                    $(event.target).parent().css("display","none");
                }); 
            }

            function getTargetLanguagesHtml(project) {
                let html = "";
                for (let i=0;i<project.targetLangs.length;i++) {
                    html += "<span class='lang-wrapper'>"+project.targetLangs[i]+"</span>";
                }
                return html;
            }

            function renderProjectItems(data) {
                let projects = data.content;

                $(".projects .projects-container .projects").empty();

                for (let i=0;i<data.numberOfElements;i++ ) {
                    let project = projects[i];
                    let targetLangs = getTargetLanguagesHtml(project);
                    $(".projects .projects-container .projects")
                        .append(
                            "<div class='project-item'>"
                                +"<span class='project-name'>"+project.name+"</span>"
                                +"<span class='project-status'>"+project.status+"</span>"
                                +"<span class='project-source-lang'>"
                                +"    <span class='lang-wrapper'>"+project.sourceLang+"</span>"
                                +"</span>"
                                +"<span class='project-target-langs'>"
                                + targetLangs
                                +"</span>"
                            +"</div>");
                }
            }

            function renderPaginator(data) {
                let totalPages = data.totalPages;
                let currentPage = data.pageNumber;
                
                let showPrev = (currentPage-1) >= 0;
                let showFirst = showPrev;
                let showNext = (currentPage+1) < totalPages;
                let showLast = showNext;

                $(".projects-paginator").empty();

                $(".projects-paginator")
                    .append("<span class='page-btn page-first "+((showFirst)?"":"disabled")+"' data-value='0' ><<</span>")
                    .append("<span class='page-btn page-prev "+((showPrev)?"":"disabled")+"' data-value='"+(currentPage-1)+"'><</span>");
                for (let i=0;i<totalPages;i++ ) {
                    $(".projects-paginator")
                        .append("<span class='page-btn page "+((i!=currentPage)?"":"disabled")+"' data-value='"+i+"'>"+(i+1)+"</span>");
                }
                $(".projects-paginator")
                    .append("<span class='page-btn page-next "+((showNext)?"":"disabled")+"' data-value='"+(currentPage+1)+"'>></span>")
                    .append("<span class='page-btn page-last "+((showLast)?"":"disabled")+"' data-value='"+(totalPages-1)+"'>>></span>");
                
                $(".projects-paginator .page-btn").on("click",function(event){
                    let btn = $(event.target);
                    if(btn.hasClass("disabled")) {
                        return;
                    }
                    let newPage = btn.data("value");
                    loadProjects(newPage);
                });
            }

            function loadProjects(page) {
                $.ajax({
                    url: "${g.createLink(controller: "projects", action: "projects")}.json",
                    type:"post",
                    dataType: 'json',
                    data:{ page: page },
                    success: function(data) {
                        renderProjectItems(data);
                        renderPaginator(data);   
                        $(".projects .projects-container.loading").removeClass('loading');
                    },
                    error: function(xhr) {
                        showMessage("${g.message(code:'pages.projects.error.ajaxloading')}");
                    }
                });
            }

            $(document).ajaxStart(function() {
                $('.projects .projects-container').addClass("loading");
            });
            $(document).ajaxStop(function() {
                $('.projects .projects-container').removeClass("loading");
            });

            $(function() {   
                loadProjects(0);
            });
        </g:javascript>
	</body>
</html>
