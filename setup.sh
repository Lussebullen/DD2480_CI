#!/bin/bash

# Install prerequisites
sudo apt update
sudo apt install nginx
sudo apt install maven
sudo apt install openjdk-17-jdk openjdk-17-jre

# Define variables
NGINX_CONF='/etc/nginx/sites-available/default'
BACKUP_CONF="${NGINX_CONF}.backup"

# Check if running as root
if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

# Backup the current Nginx default configuration
echo "Backing up the current Nginx default config to ${BACKUP_CONF}"
cp $NGINX_CONF $BACKUP_CONF

# Write the new server block configuration to the default configuration file
cat > $NGINX_CONF <<EOL
server {
    listen 80;
    server_name 16.170.201.102;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOL

# Test Nginx configuration for syntax errors
nginx -t

# Prompt to restart Nginx to apply changes
read -p "Do you want to restart Nginx to apply changes? [Y/n] " -n 1 -r
echo    # Move to a new line
if [[ $REPLY =~ ^[Yy]$ ]]
then
    systemctl restart nginx
    echo "Nginx has been restarted."
else
    echo "Nginx has not been restarted. Remember to restart Nginx manually to apply changes."
fi

