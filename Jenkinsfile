pipeline {
    agent any
    options{
        timestamps()
        timeout(time:1,unit:'HOURS')
    }
    stages {
        stage('Clone repository') {
            steps {
                sh 'docker stop demo-spring-boot || true'
                sh 'docker rm demo-spring-boot || true'
                sh 'docker rmi demo-spring-boot || true'
                sh 'rm -r ./demo/* || true'
                script {
                    def currentPath = pwd()
                    echo "Current Path: ${currentPath}"
                }
                git branch: 'main', url: 'https://github.com/Ming-Yuen/demo-spring-boot.git'
            }
        }
        stage('Build') {
            steps {
               sh 'mvn clean package' // 打包项目
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t demo-spring-boot -f Dockerfile .'
            }
        }
        stage('Docker Run') {
            steps {
                sh 'docker run -d -p 8180:8180 --name demo-spring-boot demo-spring-boot'
            }
        }
    }
}
