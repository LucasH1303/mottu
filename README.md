Challenge API mottu.

Este é um projeto de API em dessevolvimento com Java utlizando o framework Spring Boot. A aplicação foca na utilização de QR codes nas motos para listagens, relatórios e futuramente rastreamento.


Equipe:

- Mariana Roberti Neri - RM: 556284

- Lucas Henrique de Souza Santos - RM: 558241

- Ryan Fernando Lucio da Silva - RM: 555924

Tecnologias utilizadas:

- Java 21
- Spring Boot
- Oracle SQL (Banco de Dados em memória)
- Spring Data JPA
- Spring Cache
- OpenAPI/Swagger

Criando a VM no terminal do portal da Azure:

az group create --name grupo-mottu--location brazilsouth

Acessando a VM (maquina virtual):

az vm create --resource-group  grupo-mottu --name vm-mottu --image Ubuntu2204 --admin-username admlnx --admin-password ********* --generate-ssh-keys

az vm open-port --port 8080 --resource-group grupo-mottu --name vm-mottu --priority 1001

ssh @admlnx@ip

Instalar o Docker: 

git clone [link do repositorio]

cd mottu

docker build -t app-mottu .

docker run -d --name mottu-p 8080:8080 app-mottu


