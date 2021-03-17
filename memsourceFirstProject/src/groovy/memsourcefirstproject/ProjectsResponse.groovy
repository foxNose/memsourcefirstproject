package memsourcefirstproject

import groovy.transform.CompileStatic

@CompileStatic
class ProjectsResponse {
    int totalElements
    int totalPages
    int pageSize
    int pageNumber
    int numberOfElements
    List<ProjectResponse> content = []
}