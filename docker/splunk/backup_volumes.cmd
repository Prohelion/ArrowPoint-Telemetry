docker run --rm --volumes-from teamarrowweb_splunkenterprise_1 -v %cd%:/backup ubuntu tar cvf /backup/etc.tar /opt/splunk/etc
docker run --rm --volumes-from teamarrowweb_splunkenterprise_1 -v %cd%:/backup ubuntu tar cvf /backup/var.tar /opt/splunk/var