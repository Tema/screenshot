1. Setup web server.
E.g. http://sysoev.ru/nginx/ or http://httpd.apache.org/

2. Setup ftp server and setup access to the dir with web access.
E.g.
http://www.proftpd.org
https://security.appspot.com/vsftpd.html
http://www.pureftpd.org (http://www.linuxcenter.ru/lib/articles/networking/linux_pureftpd.phtml - russian instruction)

3. Extract archive screenshot-v.tar.gz to dir with web access

4. Edit screenshot-v.jnlp and replace
http://host:port/path_to_dir_with_jnlp_and_jar
ftp://host:port/path_to_dir_where_upload_screenshots
ftp user and password
http://host:port/path_to_dir_where_screenshots_are_saved

5. Give users direct link to JNLP file and advice them to allow create snapshot on the first access.
