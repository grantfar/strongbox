package org.carlspring.strongbox.metadata.pypi;

import java.util.List;
import java.util.Map;

public class Release
{
    public List<Map<String,String>> digests;
    public long downloads;
    public String filename;
    public boolean has_sig;
    public String packagetype;
    public String md5_digest;
    public String python_version;
    public int size;
    public String upload_time_iso_8601;
    public String url;

}
