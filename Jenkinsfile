node {
    //su configuracion aqui
    def server = 'x.x.x.x';
    def ruta_base = '/filesapp/microservicios/'
    def puerto = "x";
    def user_aws = 'ec2-user';
    def branch_name = 'master';
    def command_base= 'ssh -o StrictHostKeyChecking=no -l ' + user_aws + ' ' + server;
    def namems = 'configuration-hazelcast';
    def url_base = 'http://repo.git';
    def base_path = ruta_base + namems;
    def sources_path = base_path + '/fuentes';
    def aplicacion = 'app';
    def environment = 'produccion'; //ambiente: desarrollo, produccion, qa.
    def log_group_name = '/ec2/' + aplicacion + '-' + namems + '-' + environment;
    
    git branch: branch_name, credentialsId: 'GIT', url: url_base;
    
    def command_copy = 'scp -r * ' + user_aws +'@'+server+':'+ sources_path +'/.';
    
    def command_clear = command_base +' rm -rf ' + sources_path +'/*';
    def command_idcontainer = command_base + ' docker ps -a -q --filter name='+namems;
    def command_stopcontainer = command_base + ' docker stop ';
    def command_rmcontainer = command_base + ' docker rm ';
    def command_idimage = command_base +' docker image ls -q --filter reference='+namems;
    def command_rmimage = command_base +' docker rmi -f ';
    def command_buildimage = command_base +' docker build -t '+namems+ ' '+sources_path + '/.';
    def command_newidimage = command_base +' docker image ls -q --filter reference=' + namems    ;
    def command_deployimage = command_base +' docker run --log-driver=awslogs --log-opt awslogs-region=us-east-1 --log-opt awslogs-group=' + log_group_name + ' --log-opt awslogs-create-group=true ' + '--restart unless-stopped -d --name '+ namems +' -v '+ base_path +':'+ base_path +':rw -p '+puerto+':5701 '+namems;
    def command_newidcontainer = command_base + ' docker ps -a -q --filter name='+namems;
    sshagent(['58896d59-6124-4d08-82fe-9be749c83817']) {
    echo 'Validando directorio base'
    def valida_dir_base = sh command_base + ' /home/ec2-user/script/valida.sh ' + base_path;
    if( valida_dir_base == 0){
        echo 'Creando directorio base'
        sh command_base + ' mkdir ' + base_path
    } else {
        echo 'Directorio base creado'
    }
    def validar_dir_fuentes = sh command_base + ' /home/ec2-user/script/valida.sh ' + sources_path;
    if(validar_dir_fuentes == 0){
        echo 'Creando directorio fuentes'
        sh command_base + ' mkdir ' + base_path + '/fuentes'
    } else {
        echo 'Directorio fuentes creado'
    }            
    echo 'Limpiando Fuentes Antiguas '
    sh command_clear
    echo 'Copiando Fuentes'
    sh command_copy
    def idcontainer = sh(script: command_idcontainer, returnStdout: true)
    def idimage = sh(script: command_idimage, returnStdout: true)
    if (idcontainer != '') {
        echo 'Stop Container'
        command_stopcontainer = command_stopcontainer + idcontainer;
        sh command_stopcontainer;
        echo 'Remove Container'
        command_rmcontainer = command_rmcontainer + idcontainer;
        sh command_rmcontainer;
    }else{
        echo 'No se encuentra contenedor'
    }
    if (idimage != '') {
        echo 'Remove Image Container'
        command_rmimage = command_rmimage + idimage
        sh command_rmimage
    }else{
        echo 'No se encuentra imagen'
    }
    if (isUnix()) {
        echo 'Compilando Docker Image'
        sh command_buildimage
    }else{
        echo 'El sistema operativo es incompatible'
        return
    }

    def idnewimage = sh(script: command_newidimage, returnStdout: true)
    if (idnewimage != '') {
        echo 'Desploy Container'
        sh command_deployimage
    }else{
        echo 'Ocurrio problemas al Compilar'
        return
    }
    idnewcontainer = sh(script: command_newidcontainer, returnStdout: true)    
    if (idnewcontainer != '') {
        echo 'Se desplego de Manera exitosa'
        return
    }else{
        echo 'Ocurrio un problema al desplegar contenedor'
        return
    }
  }
  
}
