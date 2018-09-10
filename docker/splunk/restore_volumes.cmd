docker run --rm --volumes-from teamarrowweb_splunkenterprise_1 -v D:\backups:/backup ubuntu bash -c "cd /opt/splunk/etc && tar xvf /backup/etc.tar --strip 3"
docker run --rm --volumes-from teamarrowweb_splunkenterprise_1 -v D:\backups:/backup ubuntu bash -c "cd /opt/splunk/var && tar xvf /backup/var.tar --strip 3"
