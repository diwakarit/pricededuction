FROM kumorelease-docker-virtual.artylab.expedia.biz/stratus/primer-base-springboot:8-2.1

# Install application
COPY target/pricededuction.war /app/bin/
