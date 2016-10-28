#!bin/bash

echo "Launching AWS EC2 Instance....."
ip=$(python launch.py)
ip_address=ubuntu@$ip

echo "Copying Documents....."
sudo scp -i ~/Documents/DinnerSeeker.pem ~/Documents/install.sh $ip_address:~/
sudo scp -i ~/Documents/DinnerSeeker.pem ~/Documents/DinnerSeeker.war $ip_address:~/
sudo scp -i ~/Documents/DinnerSeeker.pem ~/Documents/import.sql $ip_address:~/
sudo scp -i ~/Documents/DinnerSeeker.pem ~/Documents/tomcat.sh $ip_address:~/

echo "Automated Deploying....."
sudo ssh -i ~/Documents/DinnerSeeker.pem $ip_address "sudo bash ~/install.sh"
