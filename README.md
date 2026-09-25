# Modern End-to-End DevOps Pipeline

## Project Overview

This is my hands-on DevOps capstone project. The goal was to deploy two applications on the same AWS EC2 server and automate the deployment process using the Cloud and DevOps tools I have been learning.

The two applications are:

1. A personal portfolio website built with HTML and CSS.
2. The Bloomy Technologies Class of 2026 Java Yearbook application provided in my tutor's source repository.

The final workflow is:

**GitHub → Jenkins → Maven → Terraform → AWS EC2 → Ansible → Docker Compose → Applications**

The applications run on the same EC2 server:

* Portfolio: `port 80`
* Java Yearbook: `port 8081`

---

## Project Objective

The project required me to:

* Create AWS infrastructure using Terraform.
* Configure an EC2 server using Ansible.
* Containerize both applications using Docker.
* Run both applications using Docker Compose.
* Build the Java application using Maven.
* Automate the deployment using Jenkins.
* Dynamically get the EC2 public IP from Terraform and use it for Ansible.

---

## How the Project Works

The deployment process works in the following order:

```text
GitHub
   ↓
Jenkins
   ↓
Maven builds Java application
   ↓
Terraform creates AWS infrastructure
   ↓
Jenkins gets EC2 public IP
   ↓
Ansible configures EC2
   ↓
Docker Compose builds and runs both applications
   ↓
Portfolio :80 + Java :8081
```

Each tool has a specific role:

| Tool           | Purpose in this project           |
| -------------- | --------------------------------- |
| GitHub         | Stores the project source code    |
| Jenkins        | Automates the deployment pipeline |
| Maven          | Builds the Java application       |
| Terraform      | Creates the AWS infrastructure    |
| AWS EC2        | Hosts the applications            |
| Ansible        | Configures and deploys to EC2     |
| Docker         | Containerizes the applications    |
| Docker Compose | Runs both containers together     |
| Nginx          | Serves the portfolio website      |

---

## Project Structure

```text
.
├── ansible/
│   └── playbook.yml
├── java/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── myportfolio/
│   ├── Dockerfile
│   └── index.html
├── terraform/
│   └── main.tf
├── docker-compose.yml
├── jenkinsfile
└── README.md
```

### Main files

* `myportfolio/index.html` contains the portfolio website.
* `myportfolio/Dockerfile` packages the portfolio using Nginx.
* `java/` contains the Java Yearbook application and Maven configuration.
* `java/Dockerfile` packages the Java application.
* `docker-compose.yml` defines both application containers.
* `terraform/main.tf` defines the AWS infrastructure.
* `ansible/playbook.yml` configures the EC2 server and deploys the applications.
* `jenkinsfile` defines the CI/CD pipeline.

---

## The Applications

### Portfolio

The portfolio is a personal website built with HTML and CSS.

It is served using Nginx inside a Docker container and is exposed on port `80`.

### Java Yearbook

The Java application came from my tutor's source repository.

When I inspected the original application, I found that it was designed around AWS Lambda. The capstone, however, required the application to run inside Docker on an EC2 server.

I asked my tutor for clarification before changing it. I was advised to adapt the application for the EC2 and Docker deployment and to use port `8081`.

I therefore adapted the application to run as a standalone Java HTTP application and built it into a JAR using Maven.

The Java container runs on port `8081`.

---

## AWS Infrastructure with Terraform

Terraform creates the AWS environment required by the project.

The infrastructure includes:

* Custom VPC: `10.0.0.0/16`
* Public subnet: `10.0.1.0/24`
* Internet Gateway
* Public route table
* Security group
* EC2 instance

The security group allows:

* `22` for SSH
* `80` for the portfolio
* `8081` for the Java application

The project uses the AWS London region: `eu-west-2`.

Terraform state was also configured to use a dedicated S3 backend so that Jenkins could work with the same remote state.

---

## Ansible and Docker Deployment

After Terraform creates the EC2 server, Ansible connects to it using SSH.

The Ansible playbook:

1. Installs Docker.
2. Starts the Docker service.
3. Installs Docker Compose and Buildx.
4. Creates the `/opt/capstone` directory.
5. Copies the application files and Docker Compose configuration.
6. Runs Docker Compose to build and start both applications.

The applications are then available as:

```text
Portfolio:     http://<EC2-PUBLIC-IP>
Java Yearbook: http://<EC2-PUBLIC-IP>:8081
```

---

## Jenkins CI/CD Pipeline

Jenkins automates the deployment process using the `jenkinsfile`.

The main pipeline stages are:

1. **Checkout** – gets the project from GitHub.
2. **Build Java** – builds the Java application with Maven.
3. **Terraform** – creates or updates the AWS infrastructure.
4. **Get EC2 IP** – gets the current EC2 public IP from Terraform.
5. **Create Ansible Inventory** – creates the inventory using the current IP.
6. **Deploy with Ansible** – connects to EC2 and deploys both applications.

AWS credentials and the EC2 SSH key are stored securely in Jenkins credentials rather than being written directly in the Jenkinsfile.

The Terraform state is stored remotely in S3.

---

## Challenges and Troubleshooting

This project involved several real troubleshooting situations.

| Problem                                          | What I did                                                               |
| ------------------------------------------------ | ------------------------------------------------------------------------ |
| Ubuntu system time was incorrect                 | Used Chrony to correct the time and restore AWS authentication           |
| Docker permission error                          | Added my Ubuntu user to the Docker group                                 |
| Port `80` was already in use                     | Found Apache using the port and stopped it                               |
| Java application was Lambda based                | Adapted it to run as a standalone Java application on port `8081`        |
| Maven found a duplicate Java class               | Moved the backup Java file outside the source directory                  |
| Java JAR had no main manifest                    | Updated the Maven Shade Plugin with the main class                       |
| `t2.micro` was unavailable for my AWS account    | Checked available instance types and changed to `t3.micro`               |
| Ansible could not use the SSH key                | Corrected the key ownership and permissions                              |
| Docker Compose/Buildx issues on EC2              | Installed compatible versions through Ansible                            |
| Ansible Compose command used the wrong directory | Ran it from `/opt/capstone`                                              |
| Jenkins returned `SignatureDoesNotMatch`         | Verified the Jenkins AWS credentials using `aws sts get-caller-identity` |
| Jenkins did not recognise `sshagent`             | Installed the Jenkins SSH Agent Plugin                                   |

These problems helped me understand that troubleshooting is an important part of working with Cloud and DevOps tools.

---

## Final Result

The complete Jenkins pipeline eventually ran successfully.

I verified that both applications were running on the EC2 server and accessed them from a browser:

```text
Portfolio:
http://<EC2-PUBLIC-IP>

Java Yearbook:
http://<EC2-PUBLIC-IP>:8081
```

I also verified both applications from the EC2 server using `curl`.

The final Jenkins pipeline completed successfully with:

```text
Finished: SUCCESS
```

Screenshots were captured showing:

* The successful Jenkins pipeline.
* The deployed portfolio website.
* The deployed Java Yearbook application.

---

## What I Learned

This project helped me understand how the different Cloud and DevOps tools connect.

I learned that:

* **Terraform** creates the infrastructure.
* **Ansible** prepares and configures the server.
* **Docker** packages the applications.
* **Docker Compose** runs the applications together.
* **Maven** builds the Java application.
* **Jenkins** automates the entire process.
* **GitHub** stores and manages the project code.

The biggest lesson for me was that DevOps is not just about memorising commands. It is about understanding how the different parts work together and being able to troubleshoot when something goes wrong.

---

## Repository

The completed project is available on GitHub:

`https://github.com/Ajibola-Mary/ajibola-devops-capstone`
