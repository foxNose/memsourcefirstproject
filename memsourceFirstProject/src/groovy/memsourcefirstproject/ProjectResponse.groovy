package memsourcefirstproject

import groovy.transform.CompileStatic

@CompileStatic
class ProjectResponse {
    String name
    String sourceLang
    List<String> targetLangs = []
    String status
}