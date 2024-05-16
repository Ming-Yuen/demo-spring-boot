pipeline {
    agent any
    options{
        timestamps()
        timeout(time:1,unit:'HOURS')
    }
    stages {
        stage('Clone repository') {
            steps {
                script {
                    def currentPath = pwd()
                    echo "Current Path: ${currentPath}"
                }
                git branch: 'main', url: 'https://github.com/Ming-Yuen/demo-spring-boot.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t demo-spring-boot -f Dockerfile .'
            }
        }
        stage('Docker Run') {
            steps {
                // 运行Docker容器
                sh 'docker run -d -p 8080:8080 demo-spring-boot'
            }
        }
    }
}
