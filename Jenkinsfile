node("master") {
    def retstat = sh(script: 'docker service inspect microservice-spark-cassandra', returnStatus: true)

    def MANAGER_IP = env.MANAGER_IP
    def APP_NAME = env.APP_NAME

    stage('checkout') {
        git url: 'https://github.com/NirbyApp/mitosis-microservice-spark-cassandra.git'
    }

    stage('package') {
      sh 'sbt clean package'
    }

    stage('deploy to Docker') {
        sh 'docker build -t mitosis/microservice-spark-cassandra:1 .'
        if (retstat != 1) {
            // sh 'docker service update --replicas 2 --image mitosis/microservice-spark-cassandra microservice-spark-cassandra:1'
            sh 'docker service rm microservice-spark-cassandra'
        }
        sh "docker service create --name microservice-spark-cassandra --log-driver=gelf --log-opt gelf-address=udp://${MANAGER_IP}:12201 --publish 7077:7077 --network microservices-net --network ${APP_NAME}-net --replicas 2 mitosis/microservice-spark-cassandra:1"
    }
}