# hadoop-setup
This folder contains resources for setting up and configuring Hadoop.


### A. Minimum System Specifications required for Hadoop framework:

| Component         | Specification                     |
|-------------------|-----------------------------------|
| **Processor**     | Dual-core processor              |
| **RAM**           | At least 4GB                    |
| **Storage**       | Minimum 25 GB of free disk space |
| **Operating System** | Linux-based OS                 |
| **Java**          | Java Development Kit (JDK 8 or later) |
| **Network**       | Basic networking support for localhost |


### B. Apache Hadoop Installation and Configuration:

#### 1. Install Java
To install OpenJDK 11, run the following command:
```bash
sudo apt install openjdk-11-jdk
```

After installation, confirm the Java version with:
```bash
java --version
```

#### 2. Create Hadoop User:
To create dedicated user for Hadoop.
```bash
sudo adduser hadoopuser
```
After creating hadoopuser, switch to it.
```bash 
sudo su -hadoopuser
```
After switching to hadoopuser, Setup SSH Keys.
```bash
ssh-keygen -t rsa
```
And add the public key to authorized keys.
```bash
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
```

#### 3. Download Apache Hadoop