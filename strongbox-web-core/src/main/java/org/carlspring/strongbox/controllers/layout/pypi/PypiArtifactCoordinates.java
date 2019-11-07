package org.carlspring.strongbox.controllers.layout.pypi;

import org.carlspring.strongbox.controllers.BaseArtifactController;
import org.carlspring.strongbox.web.LayoutRequestMapping;

import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@LayoutRequestMapping(PypiArtifactCoordinates)
public class PypiArtifactCoordinates
        extends BaseArtifactController
{
    @GetMapping(path = "{storageId}/{repositoryId}/pypi/{projectName}/json",
        produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getJSONMetadata(@PathVariable("projectName") String projectName)
    {
    }

    @GetMapping(path = "{storageId}/{repositoryId}/pypi/{projectName}/{version}/json",
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getJSONMetadata(@PathVariable("projectName") String projectName,
                                             @PathVariable("version") String version)
    {

    }

    private getJSONMetadata(String projectName, String version, String storageId, String repositoryId)
    {

    }
}
