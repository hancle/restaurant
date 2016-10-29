import boto.ec2
import time

# Access Information
aws_access_key_id = '***' 
aws_secret_access_key = '**'
region = 'us-west-2'

# Instance Information
instance_type='t2.micro'
key_name = 'DinnerSeeker'
image_id = 'ami-d732f0b7'
security_group_ids = ['DinnerSeeker']

# Connecting to AWS API
conn = boto.ec2.connect_to_region(region_name=region, 
								  aws_access_key_id=aws_access_key_id, 
								  aws_secret_access_key=aws_secret_access_key)

# Launching New Instance
Reservation = conn.run_instances(image_id=image_id,
								key_name=key_name,
								instance_type=instance_type,
								security_group_ids=security_group_ids)

instance = Reservation.instances[0]

while instance.update() != "running":
	time.sleep(5)

time.sleep(20)

print instance.ip_address 




