pipeline {
  agent none
  options {
    copyArtifactPermission('*');
    skipDefaultCheckout true
  }
  environment { plugin = "PeeVeePeeArena" }
  triggers {
    upstream '/Runsafe/RunsafeMailbox/master'
    pollSCM '@monthly'
  }
  stages {
    stage('Ant Build') {
      agent { label 'ant' }
      tools {
        ant 'Default'
        jdk 'Default'
      }
      steps { buildPluginWithAnt env.plugin, 'RunsafeMailbox,WorldGuardBridge', 'build/jar/*.jar' }
    }
    stage('Deploy to test server') {
      agent { label 'server4' }
      steps {
        installPlugin "${env.plugin}.tar"
        buildReport env.plugin, 'Deployed to test server'
      }
    }
    stage('Ask for promotion') { steps { askForPromotion() } }
  }
  post { failure { buildReport env.plugin, 'Build failed' } }
}