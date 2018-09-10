docker run --rm --volumes-from teamarrowweb_splunkenterprise_1 -v $(pwd):/backup ubuntu bash -c "cd /backup && wget https://s3.eu-central-1.amazonaws.com/prohelion-splunk-config/etc.tar && cd /opt/splunk/etc && tar xvf /backup/etc.tar --strip 3
docker run --rm --volumes-from teamarrowweb_splunkenterprise_1 -v $(pwd):/backup ubuntu bash -c "cd /backup && wget https://s3.eu-central-1.amazonaws.com/prohelion-splunk-config/var.tar && cd /opt/splunk/var && tar xvf /backup/var.tar --strip 3