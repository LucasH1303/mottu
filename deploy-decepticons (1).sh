# Altere todos os RM556128 e seu Repositório
#
# Utilizem Regiões diferentes, espalhem a criação do App:
#
# brazilsouth
# eastus
# eastus2
# westus
# westus2
export RESOURCE_GROUP_NAME="rg-decepticons"
export WEBAPP_NAME="decepticonssprint4-mottu"
export APP_SERVICE_PLAN="plandecepticonssprint4"
# Altere a sua região conforme orientação do Professor
export LOCATION="brazilsouth"
export RUNTIME="JAVA:17-java17"
export SERVER_NAME="sqlserver-decepticonssprint4"
export USERNAME="admsql"
export PASSWORD="Fiap@2tdsvms"
export DBNAME="decepticonsdb"
az group create --name $RESOURCE_GROUP_NAME --location $LOCATION
az sql server create -l $LOCATION -g $RESOURCE_GROUP_NAME -n $SERVER_NAME -u $USERNAME -p $PASSWORD --enable-public-network true
az sql db create -g $RESOURCE_GROUP_NAME -s $SERVER_NAME -n $DBNAME --service-objective Basic --backup-storage-redundancy Local --zone-redundant false
az sql server firewall-rule create -g $RESOURCE_GROUP_NAME -s $SERVER_NAME -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255


# Criar o Plano de Serviço
az appservice plan create \
  --name "$APP_SERVICE_PLAN" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --location "$LOCATION" \
  --sku F1 \
  --is-linux

# Criar o Serviço de Aplicativo
az webapp create \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --plan "$APP_SERVICE_PLAN" \
  --runtime "$RUNTIME"
