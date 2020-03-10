@Library('releasenotes') _

@NonCPS
def call(Map config=[:]){
	node {
	    stage('SCM'){
		checkout([$class: 'GitSCM', branches: [[name: '*/${BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/FeynmanFan/JenkinsGroovy']]])
	    }
	    stage('Build') {  
		try{
			sh "dotnet build ConsoleApp1"
			echo 'Building....'
			echo 'Building New Feature'
			releasenotes(changes:"false");
		}catch(ex){
			echo 'Something went wrong'
			echo ex.toString();
			currentBuild.result = 'FAILURE'
		}
		finally{
			// cleanup
		}
	    }
	    stage('Test') {
		echo 'Testing....'
	    }
	    stage('Deploy') {
		echo 'Deploying....'
	    }
	}
}