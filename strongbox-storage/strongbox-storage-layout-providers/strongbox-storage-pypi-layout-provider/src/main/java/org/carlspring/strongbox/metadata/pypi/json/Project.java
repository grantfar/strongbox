package org.carlspring.strongbox.metadata.pypi.json;

import org.carlspring.strongbox.metadata.pypi.Info;
import org.carlspring.strongbox.metadata.pypi.Release;

import java.util.Map;

public class Project
{
    public Info info;
    public Map<String, Release> releases;

}
