# Sling Command Line Utils
Sling Comand line Utils is a command line interface to a sling instance. 
## Status
`sling status` command provides status of instanlled bundles and components
To get summary of bundle status
```
> sling status bundles
```
To get summary of components status
```
> sling status components
```

## Exec
`sling exec` allows one to run arbitrary script on a sling instance. This can be used to quickly test a sample script. Or to run an arbitrary script to query or update system state using API which are not exposed otherwise.
Let us say file `testrr.java` has following code
```
import org.apache.sling.api.resource.ResourceResolver;
ResourceResolver rr = slingRequest.resourceResolver();
out.println(rr+"");
```
Then running following command will print resource resolver instance
```
> sling exec testrr.java
```
## Log
`sling log` allows access to sling log files. This command also supports tailing
Following will tail error.log
```
sling log --tail
```
Following will tail access.log
```
sling log --tail access
```
