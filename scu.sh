SERVER=http://localhost:4502
CRED=admin:admin
TMPFILE=/tmp/html.jsp
function upload_script()
{
  create_content -F"html.jsp=@/tmp/html.jsp" $SERVER/apps/a  
}
function create_jsp()
{ 
   echo > $TMPFILE  #clean up file
   echo "<%@ page import=\"java.lang.String " >> $TMPFILE

   echo " ,java.lang.Integer\" %> " >> $TMPFILE 

   echo "<%@ taglib prefix=\"sling\" uri=\"http://sling.apache.org/taglibs/sling/1.0\" %><sling:defineObjects /><% " >> $TMPFILE
   cat $1 >> $TMPFILE 
   echo "%> " >> $TMPFILE
}
function create_content()
{
  curl -u $CRED -s -o /dev/null -w "${http_code}" "$@"
}
#curl -u $CRED -F":operation=import" -F":contentType=json" -F":name=test" -F':content={ "jcr:primaryType":"nt:unstructured","sling:resourceType":"a" }' $SERVER/tmp

create_content -F"sling:resourceType=a" $SERVER/tmp/test
create_content -F"jcr:primaryType=nt:folder" $SERVER/apps/a
create_jsp $1
upload_script
## finally dump response
curl -u $CRED -s  $SERVER/tmp/test.html
