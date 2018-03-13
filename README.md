# simple-s3

# How to try

Fix configuration in `hadoop-conf/core-site.xml`.

```
> HADOOP_CONF_DIR=hadoop-conf sbt
sbt:simple-s3-parent> project simple-s3
sbt:simple-s3> runMain com.spotify.data.example.BrownieRecsJob
```
