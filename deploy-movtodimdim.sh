#!/bin/bash

# Variáveis

# Altere todos os RM556128 e seu Repositório
#
# Utilizem Regiões diferentes, espalhem a criação do App:
#
# brazilsouth
# eastus
# eastus2
# westus
# westus2
export RESOURCE_GROUP_NAME="rg-testesprint4"
export WEBAPP_NAME="testesprint4-fiap"
export APP_SERVICE_PLAN="planTesteSprint4"
# Altere a sua região conforme orientação do Professor
export LOCATION="brazilsouth"
export RUNTIME="JAVA:17-java17"


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
