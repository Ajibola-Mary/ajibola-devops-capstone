terraform {
  backend "s3" {
    bucket = "capstone-terraform-state-ajibolamvry"
    key    = "capstone/terraform.tfstate"
    region = "eu-west-2"
  }

  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

provider "aws" {
  region = "eu-west-2"
}
resource "aws_vpc" "capstone_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = "capstone-vpc"
  }
}
resource "aws_internet_gateway" "capstone_igw" {
  vpc_id = aws_vpc.capstone_vpc.id

  tags = {
    Name = "capstone-igw"
  }
}
resource "aws_subnet" "capstone_public_subnet" {
  vpc_id                  = aws_vpc.capstone_vpc.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "eu-west-2a"
  map_public_ip_on_launch = true

  tags = {
    Name = "capstone-public-subnet"
  }
}
resource "aws_route_table" "capstone_public_rt" {
  vpc_id = aws_vpc.capstone_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.capstone_igw.id
  }

  tags = {
    Name = "capstone-public-rt"
  }
}
resource "aws_route_table_association" "capstone_public_rta" {
  subnet_id      = aws_subnet.capstone_public_subnet.id
  route_table_id = aws_route_table.capstone_public_rt.id
}
resource "aws_security_group" "capstone_sg" {
  name        = "capstone-sg"
  description = "Security group for capstone EC2 instance"
  vpc_id      = aws_vpc.capstone_vpc.id

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Portfolio"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Java application"
    from_port   = 8081
    to_port     = 8081
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "capstone-sg"
  }
}
resource "aws_instance" "capstone_server" {
  ami                         = "ami-07524133e69bdba59"
  instance_type               = "t3.micro"
  subnet_id                   = aws_subnet.capstone_public_subnet.id
  vpc_security_group_ids      = [aws_security_group.capstone_sg.id]
  key_name                    = "maryweb-key"
  associate_public_ip_address = true

  tags = {
    Name = "capstone-server"
  }
}
output "ec2_public_ip" {
  description = "Public IP address of the capstone EC2 instance"
  value       = aws_instance.capstone_server.public_ip
}
