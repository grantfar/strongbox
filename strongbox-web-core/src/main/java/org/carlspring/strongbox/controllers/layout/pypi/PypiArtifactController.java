package org.carlspring.strongbox.controllers.layout.pypi;

import org.carlspring.strongbox.controllers.BaseArtifactController;
import org.carlspring.strongbox.web.LayoutRequestMapping;
import org.carlspring.strongbox.artifact.coordinates.PypiArtifactCoordinates;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@LayoutRequestMapping(PypiArtifactCoordinates.LAYOUT_NAME)
public class PypiArtifactController
        extends BaseArtifactController
{
    @GetMapping(path = "{storageId}/{repositoryId}/pypi/{projectName}/json",
        produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getJSONMetadata(@PathVariable("projectName") String projectName)
    {
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @GetMapping(path = "{storageId}/{repositoryId}/pypi/{projectName}/{version}/json",
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getJSONMetadata(@PathVariable("projectName") String projectName,
                                             @PathVariable("version") String version)
    {
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

}
