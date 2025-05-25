Challenge API mottu.

Este é um projeto de API em dessevolvimento com Java utlizando o framework Spring Boot. A aplicação foca na utilização de QR codes nas motos para listagens, relatórios e futuramente rastreamento.


Equipe:

- Mariana Roberti Neri - RM: 556284

- Lucas Henrique de Souza Santos - RM: 558241

- Ryan Fernando Lucio da Silva - RM: 555924

Tecnologias utilizadas:

- Java 21
- Spring Boot
- H2 (Banco de Dados em memória)
- Spring Data JPA
- Spring Cache
- OpenAPI/Swagger

Criando a VM no terminal do portal da Azure:

az group create --name grupo-mottu --location brazilsouth

az vm create --resource-group  grupo-mottu --name vm-mottu --image Ubuntu2204 --admin-username admlnx --admin-password Fiap@2tdsvms --generate-ssh-keys

az vm open-port --port 8080 --resource-group grupo-mottu --name vm-mottu --priority 1001

-------------------------------------------------------------------------------------------------------------------------------------------------------------

Acessando a nossa VM (Maquina Virtual): 

ssh @admlnx@ip

-------------------------------------------------------------------------------------------------------------------------------------------------------------

Instalar o Docker: 

sudo apt install -y ca-certificates curl gnupg lsb-release

sudo mkdir -m 0755 -p /etc/apt/keyrings

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt update

sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo docker --version

----------------------------------------------------------------------------------------------------------------------------------------------------------------

Clonando o repositorio do GitHub:

git clone [link do repositorio]

----------------------------------------------------------------------------------------------------------------------------------------------------------------

cd mottu

sudo usermod -aG docker $USER

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

Acessar novamente a VM (Maquina Virtual): 

ssh admlnx@ip

cd mottu 

docker build -t app-mottu .

docker run -d --name mottu-p 8080:8080 app-mottu


